package com.aldogrand.sbpc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>Title</b> InfoResponse
 * </p>
 * <p>
 * <b>Description</b> Container for application and system information.
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
@XmlRootElement(name = "info")
public class InfoResponse implements Serializable
{
    private static final long serialVersionUID = 101587679730451338L;
    
    private ApplicationInfo applicationInfo;
    private SystemInfo systemInfo;
    private ReplacementConfigInfo replacementConfigInfo;
    
    
    @XmlElement(name = "tag-info")
	/**
	 * @return the replacementConfigInfo
	 */
	public ReplacementConfigInfo getReplacementConfigInfo() {
		return replacementConfigInfo;
	}

	/**
	 * @param replacementConfigInfo the replacementConfigInfo to set
	 */
	public void setReplacementConfigInfo(ReplacementConfigInfo replacementConfigInfo) {
		this.replacementConfigInfo = replacementConfigInfo;
	}

	/**
     * @return the applicationInfo
     */
    @XmlElement(name = "application-info")
    public ApplicationInfo getApplicationInfo()
    {
        return applicationInfo;
    }

    /**
     * @param applicationInfo the applicationInfo to set
     */
    public void setApplicationInfo(ApplicationInfo applicationInfo)
    {
        this.applicationInfo = applicationInfo;
    }

    /**
     * @return the systemInfo
     */
    @XmlElement(name = "system-info")
    public SystemInfo getSystemInfo()
    {
        return systemInfo;
    }

    /**
     * @param systemInfo the systemInfo to set
     */
    public void setSystemInfo(SystemInfo systemInfo)
    {
        this.systemInfo = systemInfo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("InfoResponse [applicationInfo=");
        builder.append(applicationInfo);
        builder.append(", systemInfo=");
        builder.append(systemInfo);
        builder.append("]");
        return builder.toString();
    }
}
