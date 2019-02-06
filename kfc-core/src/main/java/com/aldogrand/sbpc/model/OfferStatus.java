package com.aldogrand.sbpc.model;

/**
 * <p>
 * <b>Title</b> OfferStatus
 * </p>
 * <p>
 * <b>Description</b> The status of an {@link Offer}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
public enum OfferStatus 
{
	UNMATCHED,  // Totally or partially unmatched (There is still some available to bet).
	MATCHED,    // Totally matched (Not available to bet)
	CANCELLED,  // Cancelled (Not available to bet)
	EXPIRED,    // Offer has expired. It is no longer available to bet.
	FAILED,     // Offer has failed.
	UNKNOWN
}
