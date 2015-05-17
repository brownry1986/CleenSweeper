package com.sweeper.clean;

import com.sweeper.clean.control.IsEndOfCleanCycle;
import com.sweeper.clean.control.vacuum.IsBinFull;
import com.sweeper.clean.control.vacuum.IsDirty;
import com.sweeper.clean.control.vacuum.IsReturnToCharger;
import com.sweeper.clean.control.vacuum.IsReturnedToCharger;

/**
 * Factory that creates all the decisions
 * 
 * @author Dave
 * 
 */
public class DecisionFactory {

	/**
	 * @return decision if the cleaning cycle should be terminated
	 */
	public IsEndOfCleanCycle getIsEndOfCleanCycle() {
		return new IsEndOfCleanCycle();
	}

	/**
	 * @return decision if the current location has dirt
	 */
	public IsDirty getIsDirty() {
		return new IsDirty();
	}

	/**
	 * @return decision if the waste bin is full
	 */
	public IsBinFull getIsBinFull() {
		return new IsBinFull();
	}

	public IDecision getIsReturnToCharger() {
		return new IsReturnToCharger();
	}

	public IDecision getIsReturnedToCharger() {
		return new IsReturnedToCharger();
	}

}
