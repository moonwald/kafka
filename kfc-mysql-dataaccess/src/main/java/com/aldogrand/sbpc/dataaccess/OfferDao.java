package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.OfferDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.model.OfferStatus;

/**
 * <p>
 * <b>Title</b> OfferDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link OfferDto}s.
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
public interface OfferDao 
{
    /**
     * Get the {@link OfferDto} with the given id.
     * @param id
     * @return
     */
    OfferDto getOffer(long id);

    /**
     * Get the {@link OfferDto} with the given id.<br/>
     * Optionally lock the {@link OfferDto}.
     * @param id
     * @param lock
     * @return
     */
    OfferDto getOffer(long id, boolean lock);

    /**
     * Get the {@link OfferDto} from the {@link ConnectorDto} with the given
     * source id.
     * @param connectorId
     * @param sourceId
     * @return
     */
    OfferDto getOffer(Long connectorId, String sourceId);

    /**
     * Lock the {@link OfferDto} and refresh its state.
     * @param offer
     */
    void lock(OfferDto offer);
    
    /**
     * Get the {@link OfferDto}s with the given ids.
     * @param ids
     * @return
     */
    List<OfferDto> getOffers(List<Long> ids);
    
    /**
     * Get the number of {@link OfferDto}s from the {@link RunnerDto}.
     * @param runnerId
     * @return
     */
    int getNumberOfRunnerOffers(Long runnerId);
    
    /**
     * Get the {@link OfferDto}s from the {@link RunnerDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param runnerId
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getRunnerOffers(Long runnerId, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link OfferDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @return
     */
    int getNumberOfRunnerOffers(Long connectorId, Long runnerId);

    /**
     * Get the {@link OfferDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getRunnerOffers(Long connectorId, Long runnerId, Integer offset, 
            Integer maxResults);

    /**
     * Get {@link OfferDto}s optionally filtering the results.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds Include only {@link OfferDto}s from the connectors.
     * @param bettingPlatformIds Include only {@link OfferDto}s from the betting platforms. 
     *      Ignore connectorIds.
     * @param accountIds Include only {@link OfferDto}s for the accounts.
     * @param modifiedSince Include only {@link OfferDto}s modified after this date.
     * @param eventIds Include only {@link OfferDto}s from the events.
     * @param marketIds Include only {@link OfferDto}s from the markets. Ignore eventIds.
     * @param runnerIds Include only {@link OfferDto}s from the runners. 
     *      Ignore eventIds and marketIds.
     * @param status Include only {@link OfferDto}s with the status.
     * @param hasBets Include only {@link OfferDto}s which have matched bets associated with them.
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getOffers(List<Long> connectorIds, List<Long> accountIds, 
            Date modifiedSince, List<Long> eventIds, List<Long> marketIds, 
            List<Long> runnerIds, OfferStatus status, Boolean hasBets, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link OfferDto}s optionally filtered by the given parameters.
     * @param accountId Include only {@link OfferDto}s from the account.
     * @param eventIds Include only {@link OfferDto}s from the events.
     * @param marketIds Include only {@link OfferDto}s from the markets.
     * @param runnerIds Include only {@link OfferDto}s from the runners.
     * @return
     */
    int getNumberOfOffers(Long accountId, List<Long> eventIds, List<Long> marketIds, 
            List<Long> runnerIds);
    
    /**
     * Get the {@link OfferDto}s optionally filtered by the given parameters.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param accountId Include only {@link OfferDto}s from the account.
     * @param eventIds Include only {@link OfferDto}s from the events.
     * @param marketIds Include only {@link OfferDto}s from the markets.
     * @param runnerIds Include only {@link OfferDto}s from the runners.
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getOffers(Long accountId, List<Long> eventIds, List<Long> marketIds, 
            List<Long> runnerIds, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link OfferDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} that were last fetched before the date. 
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchTime
     * @return
     */
    int getNumberOfOffersLastFetchedBefore(Long connectorId, Long marketId, 
            Long accountId, Date fetchTime);
    
    /**
     * Get the {@link OfferDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} that were last fetched before the date.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getOffersLastFetchedBefore(Long connectorId, Long marketId, 
            Long accountId, Date fetchTime, Integer offset, Integer maxResults);

    /**
     * Get the number of {@link OfferDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} with fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param attempts
     * @return
     */
    int getNumberOfOffersWithFailedFetchAttempts(Long connectorId, Long marketId, 
            Long accountId, int attempts);
    
    /**
     * Get the {@link OfferDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param attempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<OfferDto> getOffersWithFailedFetchAttempts(Long connectorId, Long marketId, 
            Long accountId, int attempts, Integer offset, Integer maxResults);
    
    /**
     * Save the {@link OfferDto} returning the updated version.
     * @param offer
     * @return
     */
    OfferDto saveOffer(OfferDto offer);
    
    /**
     * Delete ehe {@link OfferDto}.
     * @param offer
     */
    void deleteOffer(OfferDto offer);
    
    /**
     * Delete the {@link OfferDto}s.
     * @param lastFetchedBefore
     */
    void deleteOffers(Date lastFetchedBefore);


    /**
     * Get the {@link OfferDto} with the given params.
     * @param connectorId
     * @param sourceId
     * @param sourceRunnerId
     * @param sourceMarketId
     * @param sourceEventId
     * @return
     */
	OfferDto getOffer(Long connectorId, String sourceId, String sourceRunnerId,
			String sourceMarketId, String sourceEventId); 
}
