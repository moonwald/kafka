package com.aldogrand.sbpc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>Title</b> Error
 * </p>
 * <p>
 * <b>Description</b> A container that is used to provide error information to clients
 * of the api.
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
@XmlRootElement(name = "error")
public class Error implements Serializable
{
    private static final long serialVersionUID = -7787899901107547406L;

    private String message;
    private Integer code;

    /**
     * Create a new {@link Error}.
     */
    public Error()
    {
        super();
    }

    /**
     * Create a new {@link Error}.
     * @param message
     */
    public Error(String message)
    {
        super();
        this.message = message;
    }

    /**
     * Create a new {@link Error}.
     * @param message
     * @param code
     */
    public Error(String message, Integer code)
    {
        super();
        this.message = message;
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @return the code
     */
    @XmlAttribute
    public Integer getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code)
    {
        this.code = code;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Error [message=");
        builder.append(message);
        builder.append(", code=");
        builder.append(code);
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
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
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
        Error other = (Error) obj;
        if (code == null)
        {
            if (other.code != null)
            {
                return false;
            }
        }
        else if (!code.equals(other.code))
        {
            return false;
        }
        if (message == null)
        {
            if (other.message != null)
            {
                return false;
            }
        }
        else if (!message.equals(other.message))
        {
            return false;
        }
        return true;
    }
}
