package com.sweeper.clean.control;

import com.sweeper.clean.*;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.State;
import com.sweeper.clean.control.nav.NavigationSubController;
import com.sweeper.clean.control.nav.ret.ReturnToChargerController;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This acts as the main execution thread for the control system of the robot.
 * 
 * The process performed is the decision structure for how the robot executes
 * 
 * @author Dave
 * 
 */
public class Controller implements Runnable {

	private State state = new State();

	private HardwareInterfacePack hardware = new HardwareInterfacePack();

	private DecisionFactory decisionFactory = new DecisionFactory();

	private ActionFactory actionFactory = new ActionFactory();

	private volatile boolean stop = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		try {
			validateHardwarePack(hardware);

			// Scan the location of the charging station
			IAction initLocScan = getActionFactory().getInitScan();
			initLocScan.execute(hardware, state);

			IDecision isReturnedToCharger = getDecisionFactory().getIsReturnedToCharger();
			IDecision isEndOfCleanCycle = getDecisionFactory().getIsEndOfCleanCycle();
			IDecision isDirty = getDecisionFactory().getIsDirty();
			IDecision isBinFull = getDecisionFactory().getIsBinFull();
			IDecision isReturnToCharger = getDecisionFactory().getIsReturnToCharger();

			IAction scan = getActionFactory().getScanLocation();

			// Start clean cycle
			while (!stop) {

				if (isEndOfCleanCycle.decide(hardware, state)) {
					if (isReturnedToCharger.decide(hardware, state)) {
						stop = true;
					} else {
						ReturnToChargerController returnCharger = getReturnChargerController();
						returnCharger.returnToStation(hardware, state);
					}
				} else if (isBinFull.decide(hardware, state)) {
					if (isReturnedToCharger.decide(hardware, state)) {
                        IAction recharge = getActionFactory().getRecharge();
                        recharge.execute(hardware, state);
						IAction emptyMe = getActionFactory().getEmptyMe();
						emptyMe.execute(hardware, state);
					} else {
						ReturnToChargerController returnCharger = getReturnChargerController();
						returnCharger.returnToStation(hardware, state);
					}
				} else if (isReturnToCharger.decide(hardware, state)) {
					if (isReturnedToCharger.decide(hardware, state)) {
						IAction recharge = getActionFactory().getRecharge();
						recharge.execute(hardware, state);
					} else {
						ReturnToChargerController returnCharger = getReturnChargerController();
						returnCharger.returnToStation(hardware, state);
					}
					
				} else if (isDirty.decide(hardware, state)) {

					IAction setCleaningHead = getActionFactory().getSetCleaningHead();
					setCleaningHead.execute(hardware, state);

					IAction vacuum = getActionFactory().getVacuum();
					vacuum.execute(hardware, state);

				} else {
					NavigationSubController nav = getNavigationController();
					nav.move(hardware, state);
				}

				if (!stop) {
					scan.execute(hardware, state);
				}
			}

		} catch (Exception e) {
			state.log(e.getMessage());
		}

		state.log("Cleaning Cycle Ended.");
	}

	/**
	 * Returns all the logged messages
	 */
	public List<String> getLog() {

		List<String> retVal = new ArrayList<String>();

		Iterator<String> logIt = state.getLogIterator();
		while (logIt.hasNext()) {
			retVal.add(logIt.next());
		}

		return retVal;
	}

	/**
	 * @return the floor plan from the controllers memory
	 */
	public Map<Point, FloorCell> getFloorPlan() throws ControllerException {
		Map<Point, FloorCell> retVal = new HashMap<Point, FloorCell>();

		Iterator<Point> it = state.getFloorPlanKeyIterator();
		while (it.hasNext()) {
			Point p = it.next();
			FloorCell cell = state.getFloorCell(p);
			retVal.put(p, cell);
		}

		return retVal;
	}

	/**
	 * @return Sub controller for navigating
	 */
	protected NavigationSubController getNavigationController() {
		return new NavigationSubController();
	}

	/**
	 * @return Sub controller for returning to the charging station
	 */
	protected ReturnToChargerController getReturnChargerController() {
		return new ReturnToChargerController();
	}

	/**
	 * @return the factory that creates actions
	 */
	protected ActionFactory getActionFactory() {
		return actionFactory;
	}

	/**
	 * @return the factory that creates decisions
	 */
	protected DecisionFactory getDecisionFactory() {
		return decisionFactory;
	}

	/**
	 * Stops the execution of the thread
	 */
	public void stop() {
		stop = true;
	}

	/**
	 * Sets the Hardware interface for the controller
	 * 
	 * @param hardware Hardware interface
	 */
	public void setHardwarePack(HardwareInterfacePack hardware) {
		this.hardware = hardware;
	}

	/*
	 * Validates that the hardware interfaces have been provided.
	 */
	protected void validateHardwarePack(HardwareInterfacePack hardware) throws ControllerException {

		if (hardware == null) {
			throw new ControllerException("Hardware Interfaces not provided. Unable to process.");
		}

		Method[] methods = hardware.getClass().getMethods();
		for (Method method : methods) {

			if (method.getName().startsWith("getClass")) {
				continue;
			}

			if (method.getParameterTypes().length > 0) {
				continue;
			}

			if (method.getName().startsWith("get")) {

				Object retVal;
                try {
                    retVal = method.invoke(hardware);
                } catch (IllegalAccessException e) {
                    throw new ControllerException("Method: " + method.getName() + " on hardware interface pack is not accessible.");
                } catch (InvocationTargetException e) {
                    throw new ControllerException("Method: " + method.getName() + " on hardware interface pack cannot be invoked.");
                }

				if (retVal == null) {
					throw new ControllerException("Method: " + method.getName() + " on hardware interface pack did not return an interface.");
				}
			}

		}
	}

}
