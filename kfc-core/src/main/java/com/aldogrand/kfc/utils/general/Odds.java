package com.aldogrand.kfc.utils.general;

import java.math.BigDecimal;

/**
 * <p>
 * <b>Title</b> Odds
 * </p>
 * <p>
 * <b>Description</b> A representation of odds that stores a reference to the
 * numeric value and the {@link OddsType}.<br/>
 * This can be a simple way to encapsulate the odds information.
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
public class Odds extends Number implements Comparable<Odds>
{
    private static final long serialVersionUID = -1008664576330922089L;

    private final Number value;
    private final OddsType type;
    
    /**
     * Create new {@link Odd}s with the given value and {@link OddsType}.
     * @param value
     * @param type
     * @throws IllegalArgumentException if the value or type is null.
     */
    public Odds(Number value, OddsType type)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value cannot be null. Set an odds value");
        }
        if (type == null)
        {
            throw new IllegalArgumentException("type cannot be null. Set an odds type");
        }
        
        if ((type.equals(OddsType.FRACTIONAL)) && (!(value instanceof Fraction)))
        {
            throw new IllegalArgumentException("type is FRACTIONAL so value must be a Fraction");
        }
        if ((!type.equals(OddsType.FRACTIONAL)) && (!(value instanceof BigDecimal)))
        {
            this.value = BigDecimal.valueOf(value.doubleValue());
        }
        else
        {
            this.value = value;
        }
        this.type = type;
    }

    /**
     * @return the value
     */
    public Number getValue()
    {
        return value;
    }

    /**
     * @return the type
     */
    public OddsType getType()
    {
        return type;
    }

    /**
     * 
     * @param type
     * @return
     * @throws IllegalArgumentException if type is null.
     */
    public Odds oddsValue(OddsType type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type cannot be null. Set an OddsType to convert to");
        }
        
        final Number converted = OddsConverter.convert(getValue(), getType(), type);
        return new Odds(converted, type);
    }
    
    /**
     * Convert this {@link Odds} representation to a Decimal {@link OddsType} 
     * representation.
     * @return The Decimal {@link Odds} representation.
     */
    public Odds decimalOddsValue()
    {
        return oddsValue(OddsType.DECIMAL);
    }
    
    /**
     * Convert this {@link Odds} representation to a US {@link OddsType} 
     * representation.
     * @return The US {@link Odds} representation.
     */
    public Odds usOddsValue()
    {
        return oddsValue(OddsType.US);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Odds odds)
    {
        if (odds == null)
        {
            return 0;
        }
        
        return decimalOddsValue().compareTo(odds.decimalOddsValue());
    }

    /* (non-Javadoc)
     * @see java.lang.Number#intValue()
     */
    @Override
    public int intValue()
    {
        return value.intValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#longValue()
     */
    @Override
    public long longValue()
    {
        return value.longValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#floatValue()
     */
    @Override
    public float floatValue()
    {
        return value.floatValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#doubleValue()
     */
    @Override
    public double doubleValue()
    {
        return value.doubleValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#byteValue()
     */
    @Override
    public byte byteValue()
    {
        return value.byteValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#shortValue()
     */
    @Override
    public short shortValue()
    {
        return value.shortValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return value.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Odds other = (Odds) obj;
        if (type != other.type)
        {
            return false;
        }
        if (value == null)
        {
            if (other.value != null)
            {
                return false;
            }
        }
        else if (!value.equals(other.value))
        {
            return false;
        }
        return true;
    }
}
