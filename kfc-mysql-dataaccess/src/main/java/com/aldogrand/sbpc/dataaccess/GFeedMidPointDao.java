package com.aldogrand.sbpc.dataaccess;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.GFeedMidPointDto;

/**
 * <p>
 * <b>Title</b> GFeedMidPointDao
 * </p>
 * <p>
 * <b>Description</b> Data Access Object (DAO) that loads and saves {@link GFeedMidPointDto}s.
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
public interface GFeedMidPointDao
{
    /**
     * Get the {@link GFeedMidPointDto} with the id.
     * @param id
     * @return
     */
    GFeedMidPointDto getMidPoint(Long id);
    
    /**
     * Get the {@link GFeedMidPointDto} with the betting platform and runner ids.
     * @param bettingPlatformId
     * @param runnerId
     * @return
     */
    GFeedMidPointDto getMidPoint(Long bettingPlatformId, Long runnerId);
    
    /**
     * Get the number of {@link GFeedMidPointDto}s for the betting platform.
     * @param bettingPlatformId
     * @return
     */
    Integer getNumberOfMidPoints(Long bettingPlatformId);
    
    /**
     * Get the {@link GFeedMidPointDto}s for the betting platform.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param bettingPlatformId
     * @param offset
     * @param maxResults
     * @return
     */
    List<GFeedMidPointDto> getMidPoints(Long bettingPlatformId, Integer offset, 
            Integer maxResults);

    /**
     * Get the {@link GFeedMidPointDto}s for the betting platform and market.
     * @param bettingPlatformId
     * @param marketId
     * @return
     */
    List<GFeedMidPointDto> getMidPoints(Long bettingPlatformId, Long marketId);

    /**
     * Save the {@link GFeedMidPointDto}.
     * @param midPoint
     */
    void saveMidPoint(GFeedMidPointDto midPoint);
    
    /**
     * Remove the {@link GFeedMidPointDto}.
     * @param midPoint
     */
    void removeMidPoint(GFeedMidPointDto midPoint);
}
