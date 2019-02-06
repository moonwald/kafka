package com.aldogrand.sbpc.dataaccess.jpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.aldogrand.sbpc.dataaccess.PriceDao;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.PriceDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> PriceDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link PriceDao}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * 
 * @author Aldo Grand
 * @version 1.0
 */
public class PriceDaoJpaImpl extends GenericJpaDao<PriceDto> implements
		PriceDao 
{
	/**
	 * Create a new {@link PriceDaoJpaImpl}.
	 */
	public PriceDaoJpaImpl() 
	{
		super(PriceDto.class);
	}

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPrice(long)
	 */
	@Override
	public PriceDto getPrice(long id) 
	{
		return getEntity(id);
	}

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.PriceDto)
     */
    @Override
    public void lock(PriceDto price)
    {
        writeLock(price);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPrice(java.lang.Long, 
     * java.lang.Long, com.aldogrand.sbpc.model.PriceSide, java.lang.Integer)
     */
    @Override
    public PriceDto getPrice(Long bettingPlatformId,
            Long runnerId, PriceSide side, Integer sequence)
    {
        assert bettingPlatformId != null;
        assert runnerId != null;
        assert side != null;
        assert sequence != null;
        
        return executeQuery("from Price p where p.bettingPlatform.id = ?1 and p.runner.id = ?2 and p.side = ?3 and p.sequence = ?4", 
                bettingPlatformId, runnerId, side, sequence);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPrices(java.util.List)
     */
    @Override
    public List<PriceDto> getPrices(List<Long> ids)
    {
        assert ids != null;
        
        return executeQuery("from Price p where p.id in (?1)", 0, null, ids);
    }

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#getNumberOfPrices(
	 * java.lang.Long)
	 */
    @Override
    public int getNumberOfPrices(Long bettingPlatformId)
    {
        assert bettingPlatformId != null;
        
        Number count = executeQuery("select count(p.id) from Price p where p.bettingPlatform.id = ?1", 
                Number.class, bettingPlatformId);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPrices(java.lang.Long, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PriceDto> getPrices(Long bettingPlatformId, Integer offset, 
            Integer maxResults)
    {
        assert bettingPlatformId != null;
        
        return executeQuery("from Price p where p.bettingPlatform.id = ?1 order by p.runner.id, p.side, p.sequence", 
                offset, maxResults, bettingPlatformId);
    }

	/* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao
     * #getNumberOfRunnerPrices(java.lang.Long, java.lang.Long)
     */
    @Override
    public int getNumberOfRunnerPrices(Long connectorId, Long runnerId)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("select count(p.id) from Price p where p.bettingPlatform.connector.id = ?1 and p.runner.id = ?2", 
                Number.class, connectorId, runnerId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao
     * #getRunnerPrices(java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PriceDto> getRunnerPrices(Long connectorId, Long runnerId,
            Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("from Price p where p.bettingPlatform.connector.id = ?1 and p.runner.id = ?2 order by p.id", 
                offset, maxResults, connectorId, runnerId);
    }

    /* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPrices(java.util.List, 
	 * java.util.List, java.util.Date, java.util.List, java.util.List, java.util.List, 
	 * java.lang.Integer, java.util.List, java.lang.Integer, java.lang.Integer)
	 */
    @Override
    public List<PriceDto> getPrices(List<Long> connectorIds,
            List<Long> bettingPlatformIds, Date modifiedSince,
            List<Long> eventIds, List<Long> marketIds, List<Long> runnerIds,
            Integer depth, List<PriceSide> sides, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Price p");
        if ((bettingPlatformIds != null) && (!bettingPlatformIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(bettingPlatformIds);
        }
        else if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(connectorIds);
        }
        if (modifiedSince != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.lastChangeTime >= ?");
            query.append(index);
            index ++;
            params.add(modifiedSince);
        }
        if ((runnerIds != null) && (!runnerIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(runnerIds);
        }
        else if ((marketIds != null) && (!marketIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(marketIds);
        } 
        else if ((eventIds != null) && (!eventIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.event.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(eventIds);
        } 
        if (depth != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.sequence <= ?");
            query.append(index);
            index ++;
            params.add(depth);
        }
        if ((sides != null) && (!sides.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.side in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(sides);
        }
        query.append(" order by p.runner.id, p.bettingPlatform.id, p.side, p.sequence");
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getNumberOfPricesLastFetchedBefore(
     * java.lang.Long, java.lang.Long, java.util.Date)
     */
    @Override
    public int getNumberOfPricesLastFetchedBefore(Long connectorId,
            Long marketId, Date fetchTime)
    {    	
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(p.id) from Price p");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        
        query.append((where ? " and" : " where"));
        query.append(" p.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPricesLastFetchedBefore(
     * java.lang.Long, java.lang.Long, java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PriceDto> getPricesLastFetchedBefore(Long connectorId,
            Long marketId, Date fetchTime, Integer offset, Integer maxResults)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Price p");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        
        query.append((where ? " and" : " where"));
        query.append(" p.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        query.append(" order by p.id");
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getNumberOfPricesWithFailedFetchAttempts(
     * java.lang.Long, java.lang.Long, int)
     */
    @Override
    public int getNumberOfPricesWithFailedFetchAttempts(Long connectorId,
            Long marketId, int attempts)
    {
        assert connectorId != null;
        assert marketId != null;
        
        Number count = executeQuery("select count(p.id) from Price p where p.bettingPlatform.connector.id = ?1 and p.runner.market.id = ?2 and p.failedFetchAttempts >= ?3", 
                Number.class, connectorId, marketId, attempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PriceDao#getPricesWithFailedFetchAttempts(
     * java.lang.Long, java.lang.Long, int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PriceDto> getPricesWithFailedFetchAttempts(
            Long connectorId, Long marketId, int attempts,
            Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert marketId != null;
        
        return executeQuery("from Price p where p.bettingPlatform.connector.id = ?1 and p.runner.market.id = ?2 and p.failedFetchAttempts >= ?3 order by p.id", 
                offset, maxResults, connectorId, marketId, attempts);
    }

	@Override
	public List<PriceDto> getGFeedPrices(RunnerDto runner, Long bettingPlatformId) {

		assert runner != null;

		return executeQuery(
				"from Price p where p.runner = ?1 and p.bettingPlatform.id = ?2 order by p.id",
				0, null, runner, bettingPlatformId);

	}
	
	@Override
	public List<PriceDto> getGFeedPrices(MarketDto market, Long bettingPlatformId) {

		assert market != null;

		return executeQuery(
				"from Price p where p.runner.market = ?1 and p.bettingPlatform.id = ?2 order by p.id",
				0, null, market, bettingPlatformId);

	}

	@Override
	public PriceDto getLatestPriceForRunner(RunnerDto runner, Long bettingPlatformId) {
		// Get the latest price on the runner for the specified bettingPlatform
		assert runner != null;
		PriceDto price = null;
		
		List<PriceDto> prices =  executeQuery(
				"from Price p where p.runner = ?1 and p.bettingPlatform.id = ?2 ORDER BY p.sequence ASC",
				0, null,runner, bettingPlatformId);
		if(prices != null){
			if(prices.size() > 0){
				price = prices.get(0);
			}
		}
		
		return price;

	}

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#savePrice(
	 * com.aldogrand.sbpc.dataaccess.model.PriceDto)
	 */
	@Override
	public PriceDto savePrice(PriceDto price) 
	{
	    assert price != null;
	    
	    entityManager.persist(price);
		return price;
	}

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#deletePrice(
	 * com.aldogrand.sbpc.dataaccess.model.PriceDto)
	 */
	@Override
	public void deletePrice(PriceDto price) {
	    assert price != null;
	    		
	    StringBuilder query = new StringBuilder();
	    query.append("delete from prices where id=");
	    query.append(price.getId().toString());
	    Query nativeQuery = getEntityManager().createNativeQuery(query.toString());
	    nativeQuery.executeUpdate();
	}
	
	@Override
	public void deletePrices(List<PriceDto> prices) {
	    assert prices != null;	    
	    for (PriceDto price: prices) {
	    	deletePrice(price);
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PriceDao#deletePrices(java.util.Date, String ignoreConnectors)
	 */
	@Override
	public void deletePrices(Date lastFetchedBefore, String ignoreConnectors)
	{
	    assert lastFetchedBefore != null;
        
        StringBuilder query = new StringBuilder();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = dateFormat.format(lastFetchedBefore);
        
        query.append("delete p from prices p left join betting_platforms b on b.id=p.betting_platform left join connectors c on c.id=b.connector where c.id not in (" +
        ignoreConnectors + ") and p.last_fetch_time < '" + date + "'");

        Query nativeQuery = getEntityManager().createNativeQuery(query.toString());

		nativeQuery.executeUpdate();
		
	}
}
