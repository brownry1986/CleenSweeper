package com.sweeper.clean;

import com.sweeper.clean.control.nav.*;
import com.sweeper.clean.control.nav.ret.ReturnMoveBackward;
import com.sweeper.clean.control.nav.ret.ReturnMoveForward;
import com.sweeper.clean.control.nav.ret.ReturnMoveLeft;
import com.sweeper.clean.control.nav.ret.ReturnMoveRight;
import com.sweeper.clean.control.nav.scan.InitializationScan;
import com.sweeper.clean.control.nav.scan.ScanLocation;
import com.sweeper.clean.control.vacuum.EmptyMe;
import com.sweeper.clean.control.vacuum.Recharge;
import com.sweeper.clean.control.vacuum.SetCleaningHead;
import com.sweeper.clean.control.vacuum.Vacuum;

/**
 * Factory that creates all the actions
 * 
 * @author Dave
 *
 */
public class ActionFactory {
	
	/**
	 * @return action to change the cleaning head
	 */
	public SetCleaningHead getSetCleaningHead(){
		return new SetCleaningHead();
	}
	
	/**
	 * @return action to execute a vacuum
	 */
	public Vacuum getVacuum(){
		return new Vacuum();
	}
	
	/**
	 * @return action to move backwards
	 */
	public MoveBackward getBackward(){
		return new MoveBackward();
	}
	
	/**
	 * @return action to move forward
	 */
	public MoveForward getForward(){
		return new MoveForward();
	}
	
	/**
	 * @return action to move left
	 */
	public MoveLeft getLeft(){
		return new MoveLeft();
	}
	
	/**
	 * @return action to move right
	 */
	public MoveRight getRight(){
		return new MoveRight();
	}
	

	/**
	 * @return action to scan current location
	 */
	public ScanLocation getScanLocation(){
		return new ScanLocation();
	}
	
	/**
	 * @return action to scan the start location
	 */
	public InitializationScan getInitScan(){
		return new InitializationScan();
	}
	
	/**
	 * @return action to return move backward
	 */
	public ReturnMoveBackward getReturnMoveBackward(){
		return new ReturnMoveBackward();
	}
	
	/**
	 * @return action to return move backward
	 */
	public ReturnMoveForward getReturnMoveForward(){
		return new ReturnMoveForward();
	}
	
	/**
	 * @return action to return move backward
	 */
	public ReturnMoveLeft getReturnMoveLeft(){
		return new ReturnMoveLeft();
	}
	
	/**
	 * @return action to return move backward
	 */
	public ReturnMoveRight getReturnMoveRight(){
		return new ReturnMoveRight();
	}

    public IAction getRecharge() {
        return new Recharge();
    }

    public IAction getEmptyMe() {
        return new EmptyMe();
    }
}
