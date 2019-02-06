package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>Title</b> CancelOfferResponse
 * </p>
 * <p>
 * <b>Description</b> A response to a cancel offer request from a Betting Exchange.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
@XmlRootElement(name = "cancel-offer-response")
public class CancelOfferResponse implements Serializable
{
    private static final long serialVersionUID = -18727835193376920L;

    private Long accountId;
    private String connectorName;
    private Long eventId;
    private Long marketId;
    private Long runnerId;
    private Long offerId;
    private String sourceId;
    private Currency currency;
    private BigDecimal amountOffered;
    private BigDecimal amountMatched;

    /**
     * @return the accountId
     */
    @XmlElement(name = "account-id")
    public Long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return the connectorName
     */
    @XmlElement(name = "connector-name")
    public String getConnectorName()
    {
        return connectorName;
    }

    /**
     * @param connectorName the connectorName to set
     */
    public void setConnectorName(String connectorName)
    {
        this.connectorName = connectorName;
    }
    
	/**
     * @return the eventId
     */
    @XmlElement(name = "event-id")
    public Long getEventId()
    {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    /**
     * @return the marketId
     */
    @XmlElement(name = "market-id")
    public Long getMarketId()
    {
        return marketId;
    }

    /**
     * @param marketId the marketId to set
     */
    public void setMarketId(Long marketId)
    {
        this.marketId = marketId;
    }

    /**
     * @return the runnerId
     */
    @XmlElement(name = "runner-id")
    public Long getRunnerId()
    {
        return runnerId;
    }

    /**
     * @param runnerId the runnerId to set
     */
    public void setRunnerId(Long runnerId)
    {
        this.runnerId = runnerId;
    }

    /**
     * @return the sourceId
     */
    @XmlElement(name = "source-id")
    public String getSourceId()
    {
        return sourceId;
    }

    /**
     * @param sourceId the sourceId to set
     */
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    
    /**
     * @return the offerId
     */
    @XmlElement(name = "offer-id")
    public Long getOfferId()
    {
        return offerId;
    }

    /**
     * @param offerId the offerId to set
     */
    public void setOfferId(Long offerId)
    {
        this.offerId = offerId;
    }
    
    
    /**
     * @return the currency
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }

    /**
	 * @return the amountOffered
	 */
    @XmlElement(name = "amount-offered")
	public BigDecimal getAmountOffered() 
    {
		return amountOffered;
	}

	/**
	 * @param amountOffered the amountOffered to set
	 */
	public void setAmountOffered(BigDecimal amountOffered) 
	{
		this.amountOffered = amountOffered;
	}

	/**
	 * @return the amountMatched
	 */
    @XmlElement(name = "amount-matched")
	public BigDecimal getAmountMatched() 
    {
		return amountMatched;
	}

	/**
	 * @param amountMatched the amountMatched to set
	 */
	public void setAmountMatched(BigDecimal amountMatched) 
	{
		this.amountMatched = amountMatched;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CancelOfferResponse [offerId=");
        builder.append(offerId);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", amountOffered=");
        builder.append(amountOffered);
        builder.append(", amountMatched=");
        builder.append(amountMatched);
        builder.append("]");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        CancelOfferResponse other = (CancelOfferResponse) obj;
        if (offerId == null)
        {
            if (other.offerId != null)
            {
                return false;
            }
        }
        else if (!offerId.equals(other.offerId))
        {
            return false;
        }
        return true;
    }
}
