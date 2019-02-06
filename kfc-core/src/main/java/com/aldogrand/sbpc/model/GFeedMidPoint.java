package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.aldogrand.kfc.utils.general.OddsType;


/**
 * <p>
 * <b>Title</b> GFeedMidPoint
 * </p>
 * <p>
 * <b>Description</b> A manually entered mid point used to calculate prices by the
 * {@link GFeed} implementation that corresponds to the {@link BettingPlatform}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@XmlRootElement(name = "gfeed-mid-point")
@XmlType(propOrder = {"id", "bettingPlatformId", "runnerId", "odds", "oddsType", "enabled"})
public class GFeedMidPoint implements Serializable
{
    private static final long serialVersionUID = 8624901326129795718L;

    private Long id;
    private Long bettingPlatformId;
    private Long runnerId;
    private BigDecimal odds;
    private OddsType oddsType;
    private Boolean enabled;

    /**
     * @return the id
     */
    @XmlElement(name = "id")
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the bettingPlatformId
     */
    @XmlElement(name = "betting-platform-id")
    public Long getBettingPlatformId()
    {
        return bettingPlatformId;
    }

    /**
     * @param bettingPlatformId the bettingPlatformId to set
     */
    public void setBettingPlatformId(Long bettingPlatformId)
    {
        this.bettingPlatformId = bettingPlatformId;
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
     * @return the odds
     */
    @XmlElement(name = "odds")
    public BigDecimal getOdds()
    {
        return odds;
    }

    /**
     * @param odds the odds to set
     */
    public void setOdds(BigDecimal odds)
    {
        this.odds = odds;
    }

    /**
     * @return the oddsType
     */
    @XmlElement(name = "odds-type")
    public OddsType getOddsType()
    {
        return oddsType;
    }

    /**
     * @param oddsType the oddsType to set
     */
    public void setOddsType(OddsType oddsType)
    {
        this.oddsType = oddsType;
    }

    /**
     * @return the enabled
     */
    @XmlElement(name = "enabled")
    public Boolean getEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GFeedMidPoint [id=");
        builder.append(id);
        builder.append(", bettingPlatformId=");
        builder.append(bettingPlatformId);
        builder.append(", runnerId=");
        builder.append(runnerId);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", enabled=");
        builder.append(enabled);
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        GFeedMidPoint other = (GFeedMidPoint) obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }
}
