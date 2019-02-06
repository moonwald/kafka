package com.aldogrand.kfc.utils.general;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * <p>
 * <b>Title</b> OddsConverter
 * </p>
 * <p>
 * <b>Description</b> A class containing a number of utility methods for converting
 * between various odds types.
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
public final class OddsConverter
{
    // Precision of IEEE754 Decimal32 format but with RoundingMode.HALF_UP
    private static final MathContext CONTEXT = new MathContext(7, RoundingMode.HALF_UP); 
    
    /**
     * Convert the odds which are in the specified {@link OddsType} to US odds.
     * @param odds The odds to convert.
     * @param oddsType The type of the odds.
     * @return The US odds value.
     * @throws IllegalArgumentException if odds or oddsType are null.
     */
    public static BigDecimal convertToUsOdds(Number odds, OddsType oddsType)
    {
        if (odds == null)
        {
            throw new IllegalArgumentException("odds cannot be null. Specify odds to convert");
        }
        if (oddsType == null)
        {
            throw new IllegalArgumentException("oddsType cannot be null. Specify oddsType to convert from");
        }
        if ((oddsType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof Fraction)))
        {
            throw new IllegalArgumentException("oddsType is FRACTIONAL. odds must be a Fraction");
        }
        if ((!oddsType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof BigDecimal)))
        {
            throw new IllegalArgumentException("odds must be a BigDecimal");
        }
        
        BigDecimal converted = null;
        if (odds instanceof BigDecimal)
        {
            converted = (BigDecimal) odds;
        }
        
        switch (oddsType)
        {
            case US :
            {
                break;
            }
            case DECIMAL :
            {
                // ret = (ret >= 2)? 100 * (ret-1) : -100 /(ret-1);
                int compare = converted.compareTo(BigDecimal.valueOf(2));
                if (compare >= 0) 
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = converted.multiply(BigDecimal.valueOf(100));
                }
                else
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                }
                break;
            }
            case PROBABILITY :
            {
                // ret = (ret <= 50)? 100 * ((100-ret) / ret) : (ret*100) / (ret-100);
                final int compare = converted.compareTo(BigDecimal.valueOf(50));
                if (compare <= 0) 
                {
                    final BigDecimal a = BigDecimal.valueOf(100).subtract(converted);
                    converted = a.divide(converted, CONTEXT);
                    converted = BigDecimal.valueOf(100).multiply(converted);
                } 
                else 
                {
                    final BigDecimal converted1 = converted.multiply(BigDecimal.valueOf(100));
                    final BigDecimal converted2 = converted.subtract(BigDecimal.valueOf(100));
                    converted = converted1.divide(converted2, CONTEXT);
                }
                break;
            }
            case HONG_KONG :
            {
                // ret = (ret >= 1)? 100 * (ret) : -100 /(ret);
                final int compare = converted.compareTo(BigDecimal.ONE);
                if (compare >= 0) 
                {
                    converted = converted.multiply(BigDecimal.valueOf(100));
                }
                else
                {
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                }
                break;
            }
            case MALAY :
            {
                // ret = -100/ret;
                converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                break;
            }
            case INDONESIAN :
            {
                // ret = ret*100;
                converted = BigDecimal.valueOf(100).multiply(converted);
                break;
            }
            case FRACTIONAL :
            {
                // convert to decimal
                final Fraction f = (Fraction) odds;
                converted = f.bigDecimalValue().add(BigDecimal.ONE);
                
                // ret = (ret >= 2)? 100 * (ret-1) : -100 /(ret-1);
                final int compare = converted.compareTo(BigDecimal.valueOf(2));
                if (compare >= 0) 
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = converted.multiply(BigDecimal.valueOf(100));
                }
                else
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                }
                break;
            }
        }
        
        return converted;
    }
    
    /**
     * Convert the given US Odds to the specified {@link OddsType}.
     * @param usOdds The US odds value to convert.
     * @param oddsType The type to convert to.
     * @return The converted odds value.
     * @throws IllegalArgumentException if usOdds or oddsType are null.
     */
    public static Number convertFromUsOdds(BigDecimal usOdds, OddsType oddsType)
    {
        if (usOdds == null)
        {
            throw new IllegalArgumentException("odds cannot be null. Specify odds to convert");
        }
        if (oddsType == null)
        {
            throw new IllegalArgumentException("oddsType cannot be null. Specify oddsType to convert to");
        }

        BigDecimal converted;
        if ((usOdds.compareTo(BigDecimal.valueOf(100)) < 0) && (usOdds.compareTo(BigDecimal.valueOf(-100)) > 0))
        {
            converted = BigDecimal.valueOf(100);
        }
        else
        {
            converted = usOdds;
        }
        
        switch (oddsType)
        {
            case US :
            {
                break;
            }
            case DECIMAL :
            {
                // ret = (value >= 100)? ((value/100) + 1) : ((-100/value) + 1);
                final int compare = converted.compareTo(BigDecimal.valueOf(100));
                if (compare >= 0) 
                {
                    converted = converted.divide(BigDecimal.valueOf(100), CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                else
                {
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                break;
            }
            case PROBABILITY :
            {
                // ret = (value >= 100)? (10000 / (value + 100)) : (100 * value) / (value-100);
                final int compare = converted.compareTo(BigDecimal.valueOf(100));
                if (compare >= 0) 
                {
                    converted = converted.add(BigDecimal.valueOf(100));
                    converted = BigDecimal.valueOf(10000).divide(converted, CONTEXT);
                } 
                else 
                {
                    final BigDecimal converted1 = converted.multiply(BigDecimal.valueOf(100));
                    final BigDecimal converted2 = converted.subtract(BigDecimal.valueOf(100));
                    converted = converted1.divide(converted2, CONTEXT);
                }
                break;
            }
            case HONG_KONG :
            {
                // ret = (value >= 100)? (value/100) : (-100/value);
                final int compare = converted.compareTo(BigDecimal.valueOf(100));
                if (compare >= 0) 
                {
                    converted = converted.divide(BigDecimal.valueOf(100), CONTEXT);
                }
                else
                {
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                }
                break;
            }
            case MALAY :
            {
                // ret = (-100/value) ;
                converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                break;
            }
            case INDONESIAN :
            {
                // ret = (value/100) ;
                converted = converted.divide(BigDecimal.valueOf(100), CONTEXT);
                break;
            }
            case FRACTIONAL :
            {
                // Convert to decimal
             
                // ret = (value >= 100)? ((value/100) + 1) : ((-100/value) + 1);
                final int compare = converted.compareTo(BigDecimal.valueOf(100));
                if (compare >= 0) 
                {
                    converted = converted.divide(BigDecimal.valueOf(100), CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                else
                {
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                
                // Convert decimal to fraction
                return Fraction.valueOf(converted.subtract(BigDecimal.ONE));
            }
        }
        
        converted = converted.round(CONTEXT);
        return converted;
    }
    
    /**
     * Convert the odds which are in the specified {@link OddsType} to Decimal odds.
     * @param odds The odds to convert.
     * @param oddsType The type of the odds.
     * @return The Decimal odds value.
     * @throws IllegalArgumentException if odds or oddsType are null.
     */
    public static BigDecimal convertToDecimalOdds(Number odds, OddsType oddsType)
    {
        if (odds == null)
        {
            throw new IllegalArgumentException("odds cannot be null. Specify odds to convert");
        }
        if (oddsType == null)
        {
            throw new IllegalArgumentException("oddsType cannot be null. Specify oddsType to convert from");
        }
        if ((oddsType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof Fraction)))
        {
            throw new IllegalArgumentException("oddsType is FRACTIONAL. odds must be a Fraction");
        }
        if ((!oddsType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof BigDecimal)))
        {
            throw new IllegalArgumentException("odds must be a BigDecimal");
        }
        
        BigDecimal converted = null;
        if (odds instanceof BigDecimal)
        {
            converted = (BigDecimal) odds;
        }
        
        switch (oddsType)
        {
            case US :
            {
                if ((converted.compareTo(BigDecimal.valueOf(100)) < 0) 
                        && (converted.compareTo(BigDecimal.valueOf(-100)) > 0))
                {
                    converted = BigDecimal.valueOf(100);
                }
                
                // ret = (value >= 100)? ((value/100) + 1) : ((-100/value) + 1);
                final int compare = converted.compareTo(BigDecimal.valueOf(100));
                if (compare >= 0) 
                {
                    converted = converted.divide(BigDecimal.valueOf(100), CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                else
                {
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                break;
            }
            case DECIMAL :
            {
                break;
            }
            case PROBABILITY :
            {
                // ret = 100 / ret;
                converted = BigDecimal.valueOf(100).divide(converted, CONTEXT);
                break;
            }
            case HONG_KONG :
            {
                // ret = ret + 1;
                converted = converted.add(BigDecimal.ONE);
                break;
            }
            case MALAY :
            {
                // ret = ret >= 0 ? ret + 1 : (-1 / ret) + 1;
                final int compare = converted.compareTo(BigDecimal.ZERO);
                if (compare >= 0)
                {
                    converted = converted.add(BigDecimal.ONE);
                }
                else
                {
                    converted = BigDecimal.valueOf(-1).divide(converted, CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                break;
            }
            case INDONESIAN :
            {
                // ret = ret >= 1 ? ret + 1 : (-1 / ret) + 1;
                final int compare = converted.compareTo(BigDecimal.ZERO);
                if (compare >= 0)
                {
                    converted = converted.add(BigDecimal.ONE);
                }
                else
                {
                    converted = BigDecimal.valueOf(-1).divide(converted, CONTEXT);
                    converted = converted.add(BigDecimal.ONE);
                }
                break;
            }
            case FRACTIONAL :
            {
                final Fraction f = (Fraction) odds;
                converted = f.bigDecimalValue().add(BigDecimal.ONE);
                break;
            }
        }
        
        return converted.round(CONTEXT);
    }
    
    /**
     * Convert the given Decimal Odds to the specified {@link OddsType}.
     * @param decimalOdds The Decimal odds value to convert.
     * @param oddsType The type to convert to.
     * @return The converted odds value.
     * @throws IllegalArgumentException if decimalOdds or oddsType are null.
     */
    public static Number convertFromDecimalOdds(BigDecimal decimalOdds, OddsType oddsType)
    {
        if (decimalOdds == null)
        {
            throw new IllegalArgumentException("odds cannot be null. Specify odds to convert");
        }
        if (oddsType == null)
        {
            throw new IllegalArgumentException("oddsType cannot be null. Specify oddsType to convert to");
        }

        BigDecimal converted = decimalOdds;
        switch (oddsType)
        {
            case US :
            {
                // ret = (ret >= 2)? 100 * (ret-1) : -100 /(ret-1);
                final int compare = converted.compareTo(BigDecimal.valueOf(2));
                if (compare >= 0) 
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = converted.multiply(BigDecimal.valueOf(100));
                }
                else
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = BigDecimal.valueOf(-100).divide(converted, CONTEXT);
                }
                break;
            }
            case DECIMAL :
            {
                break;
            }
            case PROBABILITY :
            {
                // ret = 100 / ret;
                converted = BigDecimal.valueOf(100).divide(converted, CONTEXT);
                break;
            }
            case HONG_KONG :
            {
                // ret = ret - 1;
                converted = converted.subtract(BigDecimal.ONE);
                break;
            }
            case MALAY :
            {
                // ret = ret >= 2 ? -1 / (ret - 1) : ret - 1;
                int compare = converted.compareTo(BigDecimal.valueOf(2));
                if (compare >= 0) 
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = BigDecimal.valueOf(-1).divide(converted, CONTEXT);
                }
                else
                {
                    converted = converted.subtract(BigDecimal.ONE);
                }
                break;
            }
            case INDONESIAN :
            {
                // ret = ret >= 2 ? ret - 1 : -1 / (ret - 1);
                int compare = converted.compareTo(BigDecimal.valueOf(2));
                if (compare >= 0) 
                {
                    converted = converted.subtract(BigDecimal.ONE);
                }
                else
                {
                    converted = converted.subtract(BigDecimal.ONE);
                    converted = BigDecimal.valueOf(-1).divide(converted, CONTEXT);
                }
                break;
            }
            case FRACTIONAL :
            {
                return Fraction.valueOf(converted.subtract(BigDecimal.ONE));
            }
        }
        
        return converted.round(CONTEXT);
    }
    
    /**
     * Convert the given odds from the old {@link OddsType} to the new {@link OddsType}.
     * @param odds The value of the odds in the old {@link OddsType}. 
     * This is a {@link BigDecimal} for all {@link OddsType}s except {@link FRACTIONAL} 
     * when it is a {@link Fraction}.
     * @param oldType The {@link OddsType} of the odds to be converted.
     * @param newType The {@link OddsType} of the conversion result.
     * @return The converted odds in the new {@link OddsType}. This is a 
     * {@link BigDecimal} for all {@link OddsType}s except {@link FRACTIONAL} 
     * when it is a {@link Fraction}.
     */
    public static Number convert(Number odds, OddsType oldType, OddsType newType)
    {
        if (odds == null)
        {
            throw new IllegalArgumentException("odds cannot be null. Specify odds to convert");
        }
        if (oldType == null)
        {
            throw new IllegalArgumentException("oldType cannot be null. Specify oldType to convert from");
        }
        if (newType == null)
        {
            throw new IllegalArgumentException("newType cannot be null. Specify newType to convert from");
        }
        if ((oldType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof Fraction)))
        {
            throw new IllegalArgumentException("oldType is FRACTIONAL. odds must be a Fraction");
        }
        if ((!oldType.equals(OddsType.FRACTIONAL)) && (!(odds instanceof BigDecimal)))
        {
            throw new IllegalArgumentException("odds must be a BigDecimal");
        }
        
        if (oldType.equals(newType))
        {
            return odds;
        }

        Number converted = null;
        switch (newType)
        {
            case US :
            {
                converted = convertToUsOdds(odds, oldType);
                break;
            }
            case DECIMAL :
            {
                converted = convertToDecimalOdds(odds, oldType);
                break;
            }
            default :
            {
                converted = convertToDecimalOdds(odds, oldType);
                converted = convertFromDecimalOdds((BigDecimal) converted, newType);
                break;
            }
        }
        
        return converted;
    }
    
    /**
     * Round the value of the {@link BigDecimal} to the number of decimal places.
     * If numOfDecimalPlaces is 0 then round to an integer value.
     * @param value The number to round.
     * @param numOfDecimalPlaces The number of decimal places to include in the result.
     * @return The rounded value.
     */
    public static BigDecimal round(BigDecimal value, int numOfDecimalPlaces)
    {
        assert value != null;
        assert numOfDecimalPlaces >= 0;
        
        return value.divide(BigDecimal.ONE, numOfDecimalPlaces, RoundingMode.HALF_UP);
    }
    
    private OddsConverter()
    {
    }
}
