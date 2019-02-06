package com.aldogrand.sbpc.dataaccess.jpa;

import com.aldogrand.sbpc.dataaccess.BetDao;
import com.aldogrand.sbpc.dataaccess.model.BetDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

import java.util.List;

/**
 * <p>
 * <b>Title</b> BetDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link BetDao}.
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
public class BetDaoJpaImpl extends GenericJpaDao<BetDto> implements BetDao
{
    /**
     * Create a new instance.
     */
    public BetDaoJpaImpl()
    {
        super(BetDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BetDao#getBet(java.lang.Long, java.lang.String)
     */
    @Override
    public BetDto getBet(Long offerId, String sourceId)
    {
        assert offerId != null;
        assert sourceId != null;
        
        return executeQuery("from Bet b where b.offer.id = ?1 and b.sourceId = ?2", offerId, sourceId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BetDao#getBet(java.lang.Long, java.lang.String, boolean)
     */
    @Override
    public BetDto getBet(Long offerId, String sourceId, boolean lock)
    {
        assert offerId != null;
        assert sourceId != null;
        
        return executeQuery("from Bet b where b.offer.id = ?1 and b.sourceId = ?2", lock, 
                new Object[] {offerId, sourceId});
    }
    
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.BetDao#deleteBets(java.util.List)
	 */
	@Override
	public void deleteBets(List<Long> ids) 
	{
	    assert ids != null;
	    
		String query = "delete from Bet b where b.id IN ?1";
		executeUpdateQuery(query, ids);
	}

}
