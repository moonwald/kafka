package com.aldogrand.sbpc.dataaccess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * <b>Title</b> AbstractFetchableDto
 * </p>
 * <p>
 * <b>Description</b> Base DTO class for all database model entity classes that 
 * hold information that is fetched from connectors. It adds timing and fetch attempt fields. 
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@MappedSuperclass
public abstract class AbstractFetchableDto extends AbstractDto
{
    private static final long serialVersionUID = -5749293138476589574L;

    protected Date creationTime = new Date();
    protected Date lastFetchTime = new Date();
    protected Date lastChangeTime = new Date();
    
    /**
     * @return the creationTime
     */
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationTime()
    {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    /**
     * @return the lastFetchTime
     */
    @Column(name = "last_fetch_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastFetchTime()
    {
        return lastFetchTime;
    }

    /**
     * @param lastFetchTime the lastFetchTime to set
     */
    public void setLastFetchTime(Date lastFetchTime)
    {
        this.lastFetchTime = lastFetchTime;
    }

    /**
     * @return the lastChangeTime
     */
    @Column(name = "last_change_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastChangeTime()
    {
        return lastChangeTime;
    }

    /**
     * @param lastChangeTime the lastChangeTime to set
     */
    public void setLastChangeTime(Date lastChangeTime)
    {
        this.lastChangeTime = lastChangeTime;
    }
}
