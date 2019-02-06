package com.aldogrand.kfc.integrationmodules;

import java.util.Properties;

/**
 * 
 * @author aldogrand
 *
 */
public class IntegrationModuleInfoServiceImpl implements IntegrationModuleServiceInfo{
	
	private static final String MODULE_NAME = "module-name";
	private static final String MODULE_ID = "module-id";
	private static final String MODULE_INSTANCE_NAME = "module-instance";
	Properties properties;
	
	IntegrationModuleInfoServiceImpl(Properties properties){
		this.properties = properties;
	}

	public String getIntegrationModuleName() {
		return properties.getProperty(MODULE_NAME);
	}

	public String getIntegrationModuleId() {
		return properties.getProperty(MODULE_ID);
	}

	public String getInstanceName() {
		return properties.getProperty(MODULE_INSTANCE_NAME);
	}

}
