package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Container for Markets
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class UpdateMarketCommands implements Serializable {

	private static final long serialVersionUID = 4717446719460309251L;

	private List<UpdateMarket> updateMarkets;

	@XmlElement(name = "UpdateMarket")
	public List<UpdateMarket> getUpdateMarkets() {
		return updateMarkets;
	}

	public void setUpdateMarkets(List<UpdateMarket> updateMarkets) {
		this.updateMarkets = updateMarkets;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
