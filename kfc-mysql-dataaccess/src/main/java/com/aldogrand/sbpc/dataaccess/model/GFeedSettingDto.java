package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 * <b>Title</b> GFeedSettingDto
 * </p>
 * <p>
 * <b>Description</b> A G Feed Setting for an {@link EventDto}
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Cillian Kelly
 * @version 1.0
 */
@Entity(name = "GFeedSetting")
@Table(name = "gfeed_settings") 
@Cacheable(false)
public class GFeedSettingDto extends AbstractDto
{

	private static final long serialVersionUID = -825984364709538774L;
	private EventDto event;
    private Double otherPriceThreshold;
    private Double oldPriceThreshold;
    private Double anchorPercentage;
    
    /**
	 * @return the anchorPercentage
	 */
    @Column(name = "anchor_percentage", precision = 4, scale = 2)
	public Double getAnchorPercentage() {
		return anchorPercentage;
	}

	/**
	 * @param anchorPercentage the anchorPercentage to set
	 */
	public void setAnchorPercentage(Double anchorPercentage) {
		this.anchorPercentage = anchorPercentage;
	}

	/**
     * @return the event
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event", nullable = false)
    public EventDto getEvent()
    {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(EventDto event)
    {
        this.event = event;
    }

 
    /**
     * @return the other_price_threshold
     */
    @Column(name = "other_price_threshold", precision = 4, scale = 2)
    public Double getOtherPriceThreshold()
    {
        return otherPriceThreshold;
    }

    /**
     * @param other_price_threshold the other_price_threshold to set
     */
    public void setOtherPriceThreshold(Double otherPriceThreshold)
    {
        this.otherPriceThreshold = otherPriceThreshold;
    }
    
    /**
     * @return the old_price_threshold
     */
    @Column(name = "old_price_threshold", precision = 4, scale = 2)
    public Double getOldPriceThreshold()
    {
        return oldPriceThreshold;
    }

    /**
     * @param old_price_threshold the old_price_threshold to set
     */
    public void setOldPriceThreshold(Double oldPriceThreshold)
    {
        this.oldPriceThreshold = oldPriceThreshold;
    }


}
