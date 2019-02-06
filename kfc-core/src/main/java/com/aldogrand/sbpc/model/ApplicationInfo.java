package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> ApplicationInfo
 * </p>
 * <p>
 * <b>Description</b> Information about the current application build.
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
@XmlType(propOrder = {"vendor", "name", "major", "minor", "patch", 
        "gitRevision", "branchName", "buildDate", "label"})
public class ApplicationInfo implements Serializable
{
    private static final long serialVersionUID = 6870564833986162307L;

    private String vendor;
    private String name;
    private Integer major;
    private Integer minor;
    private Integer patch;
    private String gitRevision;
    private String branchName;
    private Date buildDate;
    private String label;

    /**
     * @return the vendor
     */
    @XmlElement(name = "vendor")
    public String getVendor()
    {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    /**
     * @return the name
     */
    @XmlElement(name = "name")
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
     * @return the major
     */
    @XmlElement(name = "major-version")
    public Integer getMajor()
    {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(Integer major)
    {
        this.major = major;
    }

    /**
     * @return the minor
     */
    @XmlElement(name = "minor-version")
    public Integer getMinor()
    {
        return minor;
    }

    /**
     * @param minor the minor to set
     */
    public void setMinor(Integer minor)
    {
        this.minor = minor;
    }

    /**
     * @return the patch
     */
    @XmlElement(name = "patch-version")
    public Integer getPatch()
    {
        return patch;
    }

    /**
     * @param patch the patch to set
     */
    public void setPatch(Integer patch)
    {
        this.patch = patch;
    }

    /**
     * @return the gitRevision
     */
    @XmlElement(name = "git-revision")
    public String getGitRevision()
    {
        return gitRevision;
    }

    /**
     * @param gitRevision the gitRevision to set
     */
    public void setGitRevision(String gitRevision)
    {
        this.gitRevision = gitRevision;
    }

    /**
     * @return the branchName
     */
    @XmlElement(name = "branch-name")
    public String getBranchName()
    {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    /**
     * @return the buildDate
     */
    @XmlElement(name = "build-date")
    public Date getBuildDate()
    {
        return buildDate;
    }

    /**
     * @param buildDate the buildDate to set
     */
    public void setBuildDate(Date buildDate)
    {
        this.buildDate = buildDate;
    }

    /**
     * @return the label
     */
    @XmlElement(name = "build-label")
    public String getLabel()
    {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ApplicationInfo [vendor=");
        builder.append(vendor);
        builder.append(", name=");
        builder.append(name);
        builder.append(", major=");
        builder.append(major);
        builder.append(", minor=");
        builder.append(minor);
        builder.append(", patch=");
        builder.append(patch);
        builder.append(", gitRevision=");
        builder.append(gitRevision);
        builder.append(", buildDate=");
        builder.append(buildDate);
        builder.append("]");
        return builder.toString();
    }
}
