package com.aldogrand.sbpc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> SystemInfo
 * </p>
 * <p>
 * <b>Description</b> Information about the system the application is running on.
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
@XmlType(propOrder = {"hostname", "ipAddress", "systemArchitecture", "numberOfProcessors", 
        "osName", "osVersion", "jvmVendor", "jvmName", "jvmVersion", "upTime", "systemLoad", 
        "usedMemory", "maxMemory"})
public class SystemInfo implements Serializable
{
    private static final long serialVersionUID = -1692536657701414879L;

    private String hostname;
    private String ipAddress;
    private String systemArchitecture;
    private Integer numberOfProcessors;
    private String osName;
    private String osVersion;
    private String jvmVendor;
    private String jvmName;
    private String jvmVersion;
    private Long upTime;
    private Double systemLoad;
    private Long usedMemory;
    private Long maxMemory;

    /**
     * @return the hostname
     */
    @XmlElement(name = "hostname")
    public String getHostname()
    {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    /**
     * @return the ipAddress
     */
    @XmlElement(name = "ip-address")
    public String getIpAddress()
    {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the systemArchitecture
     */
    @XmlElement(name = "architecture")
    public String getSystemArchitecture()
    {
        return systemArchitecture;
    }

    /**
     * @param systemArchitecture the systemArchitecture to set
     */
    public void setSystemArchitecture(String systemArchitecture)
    {
        this.systemArchitecture = systemArchitecture;
    }

    /**
     * @return the numberOfProcessors
     */
    @XmlElement(name = "processors")
    public Integer getNumberOfProcessors()
    {
        return numberOfProcessors;
    }

    /**
     * @param numberOfProcessors the numberOfProcessors to set
     */
    public void setNumberOfProcessors(Integer numberOfProcessors)
    {
        this.numberOfProcessors = numberOfProcessors;
    }

    /**
     * @return the osName
     */
    @XmlElement(name = "os-name")
    public String getOsName()
    {
        return osName;
    }

    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    /**
     * @return the osVersion
     */
    @XmlElement(name = "os-version")
    public String getOsVersion()
    {
        return osVersion;
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion)
    {
        this.osVersion = osVersion;
    }

    /**
     * @return the jvmVendor
     */
    @XmlElement(name = "jvm-vendor")
    public String getJvmVendor()
    {
        return jvmVendor;
    }

    /**
     * @param jvmVendor the jvmVendor to set
     */
    public void setJvmVendor(String jvmVendor)
    {
        this.jvmVendor = jvmVendor;
    }

    /**
     * @return the jvmName
     */
    @XmlElement(name = "jvm-name")
    public String getJvmName()
    {
        return jvmName;
    }

    /**
     * @param jvmName the jvmName to set
     */
    public void setJvmName(String jvmName)
    {
        this.jvmName = jvmName;
    }

    /**
     * @return the jvmVersion
     */
    @XmlElement(name = "jvm-version")
    public String getJvmVersion()
    {
        return jvmVersion;
    }

    /**
     * @param jvmVersion the jvmVersion to set
     */
    public void setJvmVersion(String jvmVersion)
    {
        this.jvmVersion = jvmVersion;
    }

    /**
     * @return the upTime
     */
    @XmlElement(name = "up-time")
    public Long getUpTime()
    {
        return upTime;
    }

    /**
     * @param upTime the upTime to set
     */
    public void setUpTime(Long upTime)
    {
        this.upTime = upTime;
    }

    /**
     * @return the systemLoad
     */
    @XmlElement(name = "system-load")
    public Double getSystemLoad()
    {
        return systemLoad;
    }

    /**
     * @param systemLoad the systemLoad to set
     */
    public void setSystemLoad(Double systemLoad)
    {
        this.systemLoad = systemLoad;
    }

    /**
     * @return the usedMemory
     */
    @XmlElement(name = "used-memory")
    public Long getUsedMemory()
    {
        return usedMemory;
    }

    /**
     * @param usedMemory the usedMemory to set
     */
    public void setUsedMemory(Long usedMemory)
    {
        this.usedMemory = usedMemory;
    }

    /**
     * @return the maxMemory
     */
    @XmlElement(name = "max-memory")
    public Long getMaxMemory()
    {
        return maxMemory;
    }

    /**
     * @param maxMemory the maxMemory to set
     */
    public void setMaxMemory(Long maxMemory)
    {
        this.maxMemory = maxMemory;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SystemInfo [hostname=");
        builder.append(hostname);
        builder.append(", ipAddress=");
        builder.append(ipAddress);
        builder.append(", systemArchitecture=");
        builder.append(systemArchitecture);
        builder.append(", numberOfProcessors=");
        builder.append(numberOfProcessors);
        builder.append(", osName=");
        builder.append(osName);
        builder.append(", osVersion=");
        builder.append(osVersion);
        builder.append(", jvmVendor=");
        builder.append(jvmVendor);
        builder.append(", jvmName=");
        builder.append(jvmName);
        builder.append(", jvmVersion=");
        builder.append(jvmVersion);
        builder.append(", upTime=");
        builder.append(upTime);
        builder.append(", systemLoad=");
        builder.append(systemLoad);
        builder.append(", usedMemory=");
        builder.append(usedMemory);
        builder.append(", maxMemory=");
        builder.append(maxMemory);
        builder.append("]");
        return builder.toString();
    }
}
