package com.aldogrand.sbpc.model;

/**
 * <p>
 * <b>Title</b> PriceSide
 * </p>
 * <p>
 * <b>Description</b> The allowed values for {@link Price} sides.
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
public enum PriceSide
{
    BACK, LAY, // Backlay sides
    WIN, LOSE,  // Binary sides
    UNKNOWN
}
