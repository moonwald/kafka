package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.aldogrand.sbpc.model.MetaTagType;

/**
 * <p>
 * <b>Title</b> MetaTagDto
 * </p>
 * <p>
 * <b>Description</b> Additional meta information that can be associated with
 * other entities such as {@link EventDto}s
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
@Entity(name = "MetaTag")
@Table(name = "meta_tags", 
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "type"}))
@Cacheable(false)
public class MetaTagDto extends AbstractDto
{
    private static final long serialVersionUID = -105935212403480208L;

    private String name;
    private MetaTagType type;

    /**
     * @return the name
     */
    @Column(name = "name", length = 255, nullable = false)
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the type
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public MetaTagType getType()
    {
        return type == null? MetaTagType.UNKNOWN : type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MetaTagType type)
    {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MetaTagDto [name=");
        builder.append(name);
        builder.append(", type=");
        builder.append(type);
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
        int result = prime + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        MetaTagDto other = (MetaTagDto) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        if (type != other.type)
        {
            return false;
        }
        return true;
    }
}
