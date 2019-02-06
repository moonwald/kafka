package com.aldogrand.kfc.integrationmodules.betting.heartbeat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.integrationmodules.heartbeat.AbstractHeartBeatService;
import com.aldogrand.kfc.interfaces.KFCProducer;

/**
 * 
 * <p>
 * <b>Title</b> BetgeniusHeartbeatService
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 *
 */
public class BettingHeartbeatService extends AbstractHeartBeatService {
	private static Long DEFAULT_INTERVAL_PERIOD = 25000L;
	private Long intervalPeriod;	
	private Date lastBeatDate;
	private AtomicBoolean beatStatus = new AtomicBoolean();
	
	/**
	 * Constructor
	 * @param producer Kafka producer
	 * @param serviceInfo Integration module info service
	 * @param serviceName Name of this integration module
	 * @param interval Time to check the beat
	 */
	public BettingHeartbeatService(KFCProducer producer,
			IntegrationModuleServiceInfo serviceInfo, String serviceName, Long interval) {		
		super(producer, serviceInfo, serviceName, interval);		
	}

	@Override
	public boolean checkService() {		
		beatStatus.set(false);
		if (lastBeatDate == null) {
			return beatStatus.get();
		}		
		
		Long currentTimeInMilliseconds = new Date().getTime();
		if (currentTimeInMilliseconds - lastBeatDate.getTime() < intervalPeriod) {
			beatStatus.set(true);						
		}		
		return beatStatus.get();
	}
	
	public void receiveBeat() {
		lastBeatDate = new Date();
	}

	/**
	 * Return period in milliseconds allowed without receive
	 * betgenius heartbeat.
	 *  
	 * @return Period in milliseconds.
	 */
	public Long getIntervalPeriod() {
		if (intervalPeriod == null) {
			return DEFAULT_INTERVAL_PERIOD;
		}
		return intervalPeriod;
	}

	public void setIntervalPeriod(Long intervalPeriod) {
		this.intervalPeriod = intervalPeriod;
	}
}
