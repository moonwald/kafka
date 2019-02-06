package com.aldogrand.sbpc.model;

import java.io.Serializable;

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
 * @author Cillian Kelly
 * @version 1.0
 */
@XmlType(propOrder = {"bpapi-api-read-url", "bpapi-api-write-url"})
public class ReplacementConfigInfo implements Serializable
{

	private static final long serialVersionUID = 1183901673386929528L;
    
    private String matchbookReadUrl;
    private String matchbookWriteUrl;

	/**
	 * @return the matchbookReadUrl
	 */
    @XmlElement(name = "bpapi-api-read-url")
	public String getMatchbookReadUrl() {
		return matchbookReadUrl;
	}

    /**
	 * @param matchbookReadUrl the matchbookReadUrl to set
	 */
	public void setMatchbookReadUrl(String matchbookReadUrl) {
		this.matchbookReadUrl = matchbookReadUrl;
	}
	
	/**
	 * @return the matchbookWriteUrl
	 */
	@XmlElement(name = "bpapi-api-write-url")
	public String getMatchbookWriteUrl() {
		return matchbookWriteUrl;
	}
	
	/**
	 * @param matchbookWriteUrl the matchbookWriteUrl to set
	 */
	public void setMatchbookWriteUrl(String matchbookWriteUrl) {
		this.matchbookWriteUrl = matchbookWriteUrl;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ReplacementConfigInfo [matchbookReadUrl=");
        builder.append(matchbookReadUrl);
        builder.append(", matchbookWriteUrl=");
        builder.append(matchbookWriteUrl);
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
        result = prime
                * result
                + ((matchbookReadUrl == null) ? 0 : matchbookReadUrl.hashCode());
        result = prime
                * result
                + ((matchbookWriteUrl == null) ? 0 : matchbookWriteUrl
                        .hashCode());
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
        ReplacementConfigInfo other = (ReplacementConfigInfo) obj;
        if (matchbookReadUrl == null)
        {
            if (other.matchbookReadUrl != null)
            {
                return false;
            }
        }
        else if (!matchbookReadUrl.equals(other.matchbookReadUrl))
        {
            return false;
        }
        if (matchbookWriteUrl == null)
        {
            if (other.matchbookWriteUrl != null)
            {
                return false;
            }
        }
        else if (!matchbookWriteUrl.equals(other.matchbookWriteUrl))
        {
            return false;
        }
        return true;
    }
}
