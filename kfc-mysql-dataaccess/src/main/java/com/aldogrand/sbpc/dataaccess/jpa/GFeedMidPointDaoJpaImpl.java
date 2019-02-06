package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.GFeedMidPointDao;
import com.aldogrand.sbpc.dataaccess.model.GFeedMidPointDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> GFeedMidPointDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link GFeedMidPointDao}.
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
public class GFeedMidPointDaoJpaImpl extends GenericJpaDao<GFeedMidPointDto>
        implements GFeedMidPointDao
{
    public GFeedMidPointDaoJpaImpl()
    {
        super(GFeedMidPointDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao#getMidPoint(java.lang.Long)
     */
    @Override
    public GFeedMidPointDto getMidPoint(Long id)
    {
        assert id != null;
        
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao
     * #getMidPoint(java.lang.Long, java.lang.Long)
     */
    @Override
    public GFeedMidPointDto getMidPoint(Long bettingPlatformId, Long runnerId)
    {
        assert bettingPlatformId != null;
        assert runnerId != null;

        return executeQuery("from GFeedMidPoint mp where mp.bettingPlatform.id = ?1 and mp.runner.id = ?2", 
                bettingPlatformId, runnerId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao
     * #getNumberOfMidPoints(java.lang.Long)
     */
    @Override
    public Integer getNumberOfMidPoints(Long bettingPlatformId)
    {
        assert bettingPlatformId != null;
        
        return executeQuery("select count(mp) from GFeedMidPoint mp where mp.bettingPlatform.id = ?1", 
                Number.class, bettingPlatformId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao#getMidPoints(
     * java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<GFeedMidPointDto> getMidPoints(Long bettingPlatformId,
            Integer offset, Integer maxResults)
    {
        assert bettingPlatformId != null;

        return executeQuery("from GFeedMidPoint mp where mp.bettingPlatform.id = ?1 order by mp.runner.id", 
                offset, maxResults, bettingPlatformId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao
     * #getMidPoints(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<GFeedMidPointDto> getMidPoints(Long bettingPlatformId,
            Long marketId)
    {
        assert bettingPlatformId != null;
        assert marketId != null;
        
        return executeQuery("from GFeedMidPoint mp where mp.bettingPlatform.id = ?1 and mp.runner.market.id = ?2", 
                0, null, bettingPlatformId, marketId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao
     * #saveMidPoint(com.aldogrand.sbpc.dataaccess.model.GFeedMidPointDto)
     */
    @Override
    public void saveMidPoint(GFeedMidPointDto midPoint)
    {
        assert midPoint != null;
        
        entityManager.persist(midPoint);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.GFeedMidPointDao
     * #removeMidPoint(com.aldogrand.sbpc.dataaccess.model.GFeedMidPointDto)
     */
    @Override
    public void removeMidPoint(GFeedMidPointDto midPoint)
    {
        assert midPoint != null;
        
        removeEntity(midPoint);
    }    
}
