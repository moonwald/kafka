package com.aldogrand.sbpc.dataaccess;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.BetDto;

/**
 * <p>
 * <b>Title</b> BetDao
 * </p>
 * <p>
 * <b>Description</b> Data Access Object that loads and saves {@link BetDto}s.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public interface BetDao
{
    /**
     * Get the {@link BetDto} for the offer with the given source id.
     * @param offerId
     * @param sourceId
     * @return
     */
    BetDto getBet(Long offerId, String sourceId);
    
    /**
     * Get the {@link BetDto} for the offer with the given source id.<br/>
     * Optionally lock the {@link BetDto}.
     * @param offerId
     * @param sourceId
     * @param lock
     * @return
     */
    BetDto getBet(Long offerId, String sourceId, boolean lock);
    
    /**
     * Delete the {@link BetDto}s with the given ids
     * @param ids
     */
    void deleteBets(List<Long> ids);
    
}
