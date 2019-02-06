package com.aldogrand.kfc.msg.events;

/**
 * Created by aldogrand on 29/10/14.
 */
public abstract class KFCEvent {

	private String integrationModuleName;
	private String integrationModuleId;

	/**
	 * @return the connector
	 */
	public String getIntegrationModuleId() {
		return integrationModuleId;
	}

	/**
	 * @param connector the connector to set
	 */
	public void setIntegrationModuleId(String integrationModuleId) {
		this.integrationModuleId = integrationModuleId;
	}

	/**
	 * @return the integrationModuleName
	 */
	public String getIntegrationModuleName()
	{
		return integrationModuleName;
	}

	/**
	 * @param integrationModuleName the integrationModuleName to set
	 */
	public void setIntegrationModuleName(String integrationModuleName)
	{
		this.integrationModuleName = integrationModuleName;
	}
}
