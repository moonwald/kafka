package com.aldogrand.kfc.integrationmodules.betting;


/**
 * An Interface for all Betting Message Listeners
 * 
 * @author aldogrand
 *
 */
public interface BettingListener {

	/**
	 * Process and Event Management message from Betting
	 * @param message
	 */
	void onEventManagementMessage(String message);
	
	/**
	 * Process an in-play Football message from Betting
	 * @param message
	 */
	public void onInPlayFootballMessage(String message);
	
	/**
	 * Process an in-play Tennis message from Betting
	 * @param message
	 */
	public void onInPlayTennisMessage(String message);
}
