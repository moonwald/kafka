package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Representation of root element of Betgenius Event Management feed
 * 
 * @author aldogrand
 */
@XmlRootElement(name = "Updategram", namespace = "http://schemas.betgenius.com/2009/07/integration")
public class Updategram implements Serializable {

	private static final long serialVersionUID = 1478529015735267819L;

	private int channelId;
	private int customerId;
	private Product product;
	private CreateEventCommand createEventCommand;
	private List<CreateMarket> createMarkets;
	private UpdateEventCommand updateEventCommand;
	private List<UpdateMarket> updateMarkets;
	private CreateResultCommand createResultCommand;

	@XmlElement(name = "ChannelId")
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@XmlElement(name = "CustomerId")
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@XmlElement(name = "Product", nillable = true)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@XmlElement(name = "CreateEventCommand", nillable = true)
	public CreateEventCommand getCreateEventCommand() {
		return createEventCommand;
	}

	public void setCreateEventCommand(CreateEventCommand createEventCommand) {
		this.createEventCommand = createEventCommand;
	}

	@XmlElementWrapper(name = "CreateMarketCommands")
	@XmlElement(name = "CreateMarket", namespace = "http://schemas.betgenius.com/2009/07/integration")
	public List<CreateMarket> getCreateMarkets() {
		return createMarkets;
	}

	public void setCreateMarkets(List<CreateMarket> createMarkets) {
		this.createMarkets = createMarkets;
	}

	@XmlElement(name = "UpdateEventCommand", nillable = true)
	public UpdateEventCommand getUpdateEventCommand() {
		return updateEventCommand;
	}

	public void setUpdateEventCommand(
			UpdateEventCommand updateMarketEventCommand) {
		this.updateEventCommand = updateMarketEventCommand;
	}

	@XmlElementWrapper(name = "UpdateMarketCommands", nillable = true)
	@XmlElement(name = "UpdateMarket", namespace = "http://schemas.betgenius.com/2009/07/integration")
	public List<UpdateMarket> getUpdateMarkets() {
		return updateMarkets;
	}

	public void setUpdateMarkets(List<UpdateMarket> updateMarkets) {
		this.updateMarkets = updateMarkets;
	}

	@XmlElement(name = "CreateResultCommand", nillable = true)
	public CreateResultCommand getCreateResultCommand() {
		return createResultCommand;
	}

	public void setCreateResultCommand(CreateResultCommand createResultCommand) {
		this.createResultCommand = createResultCommand;
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
