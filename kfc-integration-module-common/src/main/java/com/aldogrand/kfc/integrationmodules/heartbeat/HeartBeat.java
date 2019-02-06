package com.aldogrand.kfc.integrationmodules.heartbeat;

/**
 * 
 * @author aldogrand
 *
 */

public interface HeartBeat {

	public void start(long interval) throws Exception;
	public void stop() throws Exception;
	public void changeInterval(long interval) throws Exception;
}
