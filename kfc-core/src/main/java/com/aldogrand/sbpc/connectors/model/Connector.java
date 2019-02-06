package com.aldogrand.sbpc.connectors.model;

public class Connector {

	private String name;
	private boolean enabled;
	private boolean eventContributor;
	private boolean offerManagementEnabled;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEventContributor() {
		return eventContributor;
	}
	public void setEventContributor(boolean eventContributor) {
		this.eventContributor = eventContributor;
	}
	public boolean isOfferManagementEnabled() {
		return offerManagementEnabled;
	}
	public void setOfferManagementEnabled(boolean offerManagementEnabled) {
		this.offerManagementEnabled = offerManagementEnabled;
	}
}
