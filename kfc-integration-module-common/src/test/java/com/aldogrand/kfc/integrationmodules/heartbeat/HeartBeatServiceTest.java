package com.aldogrand.kfc.integrationmodules.heartbeat;

import org.springframework.beans.factory.annotation.Autowired;

import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.interfaces.KFCProducer;

public class HeartBeatServiceTest extends AbstractHeartBeatService {

	@Autowired
	public HeartBeatServiceTest(KFCProducer producer,
			IntegrationModuleServiceInfo serviceInfo, String name, Long interval) {
		super(producer, serviceInfo, name, interval);
	}

	@Override
	protected boolean checkService() {
		return true;
	}
}
