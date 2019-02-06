package com.aldogrand.kfc.integrationmodules;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * <b>Title</b> Message
 * </p>
 * <p>
 * <b>Description</b> A parameterised message that provides a key and optionally
 * parameters. The key and parameters can be combined with a localised message
 * bundle to create localised message Strings.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public class Message implements Serializable
{
    private static final long serialVersionUID = 5295989851926771591L;

    private final Key key;
    private final List<String> parameters;

    /**
     * Dummy constructor to help with deserialization via Jackson.
     */
    @SuppressWarnings("unused")
    private Message()
    {
        super();
        key = null;
        parameters = null;
    }

    /**
     * Create a new instance
     * @param key
     * @param parameters
     */
    public Message(Key key, String ... parameters)
    {
        this.key = key;
        if (parameters != null)
        {
            this.parameters = Arrays.asList(parameters);
        }
        else
        {
            this.parameters = null;
        }
    }

    /**
     * @return the key
     */
    public Key getKey()
    {
        return key;
    }

    /**
     * @return the parameters
     */
    public List<String> getParameters()
    {
        return parameters;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return (parameters != null ? MessageFormat.format(key.getDefaultMessageFormat(), parameters.toArray()) 
                : key.getDefaultMessageFormat());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
        Message other = (Message) obj;
        if (key != other.key)
        {
            return false;
        }
        if (parameters == null)
        {
            if (other.parameters != null)
            {
                return false;
            }
        }
        else if (!parameters.equals(other.parameters))
        {
            return false;
        }
        return true;
    }
    
    /**
     * <p>
     * <b>Title</b> Key
     * </p>
     * <p>
     * <b>Description</b> An enumeration of message keys. 
     * The key can be used to create a localised message String.
     * </p>
     * <p>
     * <b>Company</b> AldoGrand Consultancy Ltd
     * </p>
     * <p>
     * <b>Copyright</b> Copyright (c) 2015
     * </p>
     * @author Aldo Grand
     * @version 1.0
     */
    public enum Key
    {
        // Server Request messages
        REQUEST_CONTENT_TYPE_INVALID("Invalid Content-Type {0}. Requests should be of Content-Type {1}"),
        REQUEST_BODY_NO_CONTENT("Invalid request. The request contains no content. "),
        REQUEST_BODY_INVALID_CONTENT("The request object is not valid."),
        REQUEST_PROCESSING_ERROR("Error processing request {0}."),
                
        
        // Client messages
        CLIENT_RESPONSE_BODY_TYPE_NONE("Response does not contain a header. Unable to deserialize content."),
        CLIENT_RESPONSE_BODY_TYPE_NOT_FOUND("Cannot read response content. No class of type {0} found."),
        CLIENT_RESPONSE_BODY_NO_CONTENT("Response does not contain any content. Unable to deserialize content."),
        CLIENT_RESPONSE_BODY_CONTENT_INVALID_TYPE("Response content type is {0}. Unable to deserialize content.");


        private final String defaultMessageFormat;

        /**
         * Create a {@link Key} with a default message format that can be used
         * as an example for external localisation.
         * @param defaultMessageFormat
         */
        private Key(String defaultMessageFormat)
    	{
    	    this.defaultMessageFormat = defaultMessageFormat;
    	}
        
        /**
         * @return the defaultMessageFormat
         */
        public String getDefaultMessageFormat()
        {
            return defaultMessageFormat;
        }
    }
}
