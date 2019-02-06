package com.aldogrand.kfc.utils.general;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Represents a Number in the form of a fraction e.g. 1/2
 * 
 * @author kbarry
 */
public class Fraction extends Number implements Comparable<Fraction>
{
    private static final long serialVersionUID = -7240022942830760541L;

    // Precision of IEEE754 Decimal32 format but with RoundingMode.HALF_UP
    private static final MathContext CONTEXT = new MathContext(7, RoundingMode.HALF_UP); 
    
    private final int numerator;
    private final int denominator;
    
    /**
     * Get the fractional representation of the {@link BigDecimal} value.
     * @param val
     * @return
     */
    public static Fraction valueOf(BigDecimal val)
    {
        if (val == null)
        {
            throw new IllegalArgumentException("value cannot be null");
        }
        
        BigDecimal dec = val.stripTrailingZeros();
        dec = dec.round(CONTEXT);
        if (dec.scale() < 0)
        {
            dec = dec.setScale(0, RoundingMode.HALF_UP);
        }
        final Fraction fraction = new Fraction(dec.unscaledValue().intValue(), 
                BigDecimal.ONE.scaleByPowerOfTen(dec.scale()).intValue());
        
        return fraction.reduce();
    }

    /**
     * Get the fractional representation of the double value.
     * @param val
     * @return
     */
    public static Fraction valueOf(double val)
    {
        return valueOf(BigDecimal.valueOf(val));
    }

	/**
	 * Create new fraction with the provided numerator and denominator
	 * @param numerator
	 * @param denominator
	 */
	public Fraction(int numerator, int denominator) 
	{
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	/**
	 * Reduce the fraction so that it uses the lowest possible denominator
	 */
	public Fraction reduce() 
	{
		int n = numerator;
		int d = denominator;

		while (d != 0) 
		{
			final int t = d;
			d = n % d;
			n = t;
		}
		final int gcd = n;

		n = numerator / gcd;
		d = denominator / gcd;
		return new Fraction(n, d);
	}
	
	/**
	 * Get this fraction's numerator.
	 * @return
	 */
	public int getNumerator() 
	{
		return numerator;
	}

	/**
	 * Get this fraction's denominator.
	 * @return
	 */
	public int getDenominator() 
	{
		return denominator;
	}

	/**
	 * Return the value of this number as a {@link BigDecimal}.
	 * @return
	 */
	public BigDecimal bigDecimalValue()
	{
	    return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), 
	            CONTEXT).stripTrailingZeros();
	}

	/* (non-Javadoc)
     * @see java.lang.Number#intValue()
     */
    @Override
    public int intValue()
    {
        return bigDecimalValue().intValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#longValue()
     */
    @Override
    public long longValue()
    {
        return bigDecimalValue().longValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#floatValue()
     */
    @Override
    public float floatValue()
    {
        return bigDecimalValue().floatValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#doubleValue()
     */
    @Override
    public double doubleValue()
    {
        return bigDecimalValue().doubleValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#byteValue()
     */
    @Override
    public byte byteValue()
    {
        return bigDecimalValue().byteValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Number#shortValue()
     */
    @Override
    public short shortValue()
    {
        return bigDecimalValue().shortValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Fraction fraction)
    {
        if (fraction == null)
        {
            return 0;
        }
        return bigDecimalValue().compareTo(fraction.bigDecimalValue());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() 
	{
	    final StringBuilder builder = new StringBuilder();
	    builder.append(numerator);
	    builder.append("/");
	    builder.append(denominator);
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
        result = prime * result + denominator;
        result = prime * result + numerator;
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
        final Fraction other = (Fraction) obj;
        if (denominator != other.denominator)
        {
            return false;
        }
        if (numerator != other.numerator)
        {
            return false;
        }
        return true;
    }
}