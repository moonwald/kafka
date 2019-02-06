package com.aldogrand.kfc.integrationmodules.heartbeat;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.heartbeat.IntegrationModuleHeartBeatEvent;

/**
 * 
 * @author aldogrand
 *
 */

public abstract class AbstractHeartBeatService implements HeartBeat, Runnable{

	KFCProducer producer;
	IntegrationModuleServiceInfo serviceInfo;
	Long interval;
	String serviceName;
	ExecutorService executor;
	Future future;
	private final Logger	logger	= Logger.getLogger(getClass());
	
	public AbstractHeartBeatService(KFCProducer producer, IntegrationModuleServiceInfo serviceInfo, String serviceName, Long interval){
		this.producer = producer;
		this.serviceInfo = serviceInfo;
		this.interval = interval;
		this.serviceName = serviceName;
		executor = Executors.newFixedThreadPool(1);
	}
	
	protected abstract boolean checkService();
	
	
	protected void beat() throws ProducerException, IOException{
		if(checkService()){
			IntegrationModuleHeartBeatEvent event = new IntegrationModuleHeartBeatEvent(serviceInfo.getIntegrationModuleName(), (new Date()).getTime(), serviceName+"-"+serviceInfo.getInstanceName());
			event.setIntegrationModuleId(serviceInfo.getIntegrationModuleId());
			producer.send(event);
			logger.debug("HeartBeat Sent "+ Thread.currentThread().getName() + ":"+Thread.currentThread().getId());
		}
	}
	
	public void start() throws Exception{
		start(this.interval);
	}
	
	public void start(long interval) throws Exception{
		this.interval = interval;
		if(future != null){
			future.cancel(true);
		}
		future = executor.submit(this);
		
	}
	
	public void stop() throws Exception{
		future.cancel(true);
		future = null;
	}
	
	public void changeInterval(long interval) throws Exception{
		start(interval);
	}
	
	@Override
	public void run() {
		try {
			while(true){
				beat();
				Thread.sleep(interval);
			}
		} catch (ProducerException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.debug("HeartBeat Interrupted");
		}
	}

}
