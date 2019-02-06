package com.aldogrand.sbpc.services.update.impl;

import java.io.Serializable;

/**
 * <p>
 * <b>Title</b> Update
 * </p>
 * <p>
 * <b>Description</b> An encapsulation of an update performed by the.
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
public class Update<T> implements Serializable
{
    /**
     * <p>
     * <b>Title</b> Type
     * </p>
     * <p>
     * <b>Description</b> The type of update that was performed.
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
    public enum Type
    {
        ADDED,
        CHANGED,
        REMOVED
    }
    
    private static final long serialVersionUID = 99654879833343106L;

    private final T subject;
    private final Type type;

    /**
     * Create a new {@link Update}.
     * @param subject
     * @param type
     */
    public Update(T subject, Type type)
    {
        super();
        this.subject = subject;
        this.type = type;
    }

    /**
     * The object that was updated.
     * @return the subject
     */
    public T getSubject()
    {
        return subject;
    }

    /**
     * The type of update that was performed.
     * @return the type
     */
    public Type getType()
    {
        return type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Update [subject=");
        builder.append(subject);
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
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result
                + ((type == null) ? 0 : type.hashCode());
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
        Update<?> other = (Update<?>) obj;
        if (subject == null)
        {
            if (other.subject != null)
            {
                return false;
            }
        }
        else if (!subject.equals(other.subject))
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
