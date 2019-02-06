package com.aldogrand.kfc.utils.general;

/**
 * <p>
 * <b>Title</b> ObjectUtils
 * </p>
 * <p>
 * <b>Description</b> 
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
public class ObjectUtils
{
    /**
     * A null safe equality check that returns true if both parameters are null
     * or if o1.equals(o2) returns true. It returns false if one of the parameters is
     * null or if o1.equals(o2) returns false.
     * @param o1
     * @param o2
     * @return
     */
    public static boolean areEqual(Object o1, Object o2)
    {
        if ((o1 == null) && (o2 == null))
        {
            return true;
        }
        else if ((o1 == null) && (o2 != null))
        {
            return false;
        }
        else if ((o1 != null) && (o2 == null))
        {
            return false;
        }
        else // ((o1 != null) && (o2 != null))
        {
            return o1.equals(o2);
        }
    }
    
    /**
     * A null safe comparison check that returns 0 if both parameters are null.
     * It returns -1 if c1 is null and +1 if c2 is null and returns c1.compareTo(c2)
     * if both are not null.
     * @param c1
     * @param c2
     * @return
     */
    public static <T extends Comparable<T>> int compare(T c1, T c2)
    {
        if ((c1 == null) && (c2 == null))
        {
            return 0;
        }
        else if ((c1 == null) && (c2 != null))
        {
            return -1;
        }
        else if ((c1 != null) && (c2 == null))
        {
            return 1;
        }
        else // ((c1 != null) && (c2 != null))
        {
            return c1.compareTo(c2);
        }
    }
    
    private ObjectUtils()
    {
    }
}
