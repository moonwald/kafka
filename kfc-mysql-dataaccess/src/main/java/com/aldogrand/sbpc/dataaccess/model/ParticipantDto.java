package com.aldogrand.sbpc.dataaccess.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * <b>Title</b> Participant
 * </p>
 * <p>
 * <b>Description</b> A unique participant that takes part in events
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
@Entity(name = "Participant")
@Table(name = "participants")
@Cacheable(false)
public class ParticipantDto extends AbstractDto 
{
    private static final long serialVersionUID = -1371226516874530699L;

    private String name;
	private ParticipantType type;
	private Set<MetaTagDto> metaTags = new HashSet<MetaTagDto>();
	private Set<NameVariantDto> nameVariants = new HashSet<NameVariantDto>();

    /**
     * @return the name
     */
	@Column(name = "name", nullable = false)
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
    @Column(name = "type", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    public ParticipantType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ParticipantType type)
    {
        this.type = type;
    }

    /**
     * @return the metaTags
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "participants_meta_tags", 
            joinColumns = @JoinColumn(name = "participant", referencedColumnName = "id", nullable = false), 
            inverseJoinColumns = @JoinColumn(name = "meta_tag", referencedColumnName = "id", nullable = false), 
            uniqueConstraints = @UniqueConstraint(columnNames = {"participant", "meta_tag"}))
    public Set<MetaTagDto> getMetaTags()
    {
        return metaTags;
    }

    /**
     * @param metaTags the metaTags to set
     */
    public void setMetaTags(Set<MetaTagDto> metaTags)
    {
        this.metaTags = metaTags;
    }

    /**
     * @return the nameVariants
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "participants_name_variants", 
            joinColumns = @JoinColumn(name = "participant", referencedColumnName = "id", nullable = false), 
            inverseJoinColumns = @JoinColumn(name = "name_variant", referencedColumnName = "id", nullable = false), 
            uniqueConstraints = @UniqueConstraint(columnNames = {"participant", "name_variant"}))
    public Set<NameVariantDto> getNameVariants()
    {
        return nameVariants;
    }

    /**
     * @param nameVariants the nameVariants to set
     */
    public void setNameVariants(Set<NameVariantDto> nameVariants)
    {
        this.nameVariants = nameVariants;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ParticipantDto [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
