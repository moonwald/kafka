package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * <b>Title</b> NameVariantDto
 * </p>
 * <p>
 * <b>Description</b> A container for accepted variations of names; event names, 
 * runner names, etc.
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
@Entity(name = "NameVariant")
@Table(name = "name_variants")
@Cacheable(false)
public class NameVariantDto extends AbstractDto
{
    private static final long serialVersionUID = -1175471692738525177L;

    private String variant;

    /**
     * @return the variant
     */
    @Column(name = "variant", nullable = false)
    public String getVariant()
    {
        return variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(String variant)
    {
        this.variant = variant;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("NameVariantDto [variant=");
        builder.append(variant);
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
        int result = prime + ((variant == null) ? 0 : variant.hashCode());
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
        NameVariantDto other = (NameVariantDto) obj;
        if (variant == null)
        {
            if (other.variant != null)
            {
                return false;
            }
        }
        else if (!variant.equals(other.variant))
        {
            return false;
        }
        return true;
    }
}
