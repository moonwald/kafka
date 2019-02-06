package com.aldogrand.sbpc.dataaccess.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.aldogrand.sbpc.model.Account;
import com.aldogrand.sbpc.model.AccountProperty;
import com.aldogrand.sbpc.model.Bet;
import com.aldogrand.sbpc.model.BettingPlatform;
import com.aldogrand.sbpc.model.Connector;
import com.aldogrand.sbpc.model.CurrentDangerballStatus;
import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.EventIncident;
import com.aldogrand.sbpc.model.ExchangeType;
import com.aldogrand.sbpc.model.GFeedMidPoint;
import com.aldogrand.sbpc.model.GFeedSetting;
import com.aldogrand.sbpc.model.Mapping;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.Offer;
import com.aldogrand.sbpc.model.Position;
import com.aldogrand.sbpc.model.Price;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.Score;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.sbpc.model.SourceMarket;
import com.aldogrand.sbpc.model.SourceRunner;
import com.aldogrand.sbpc.model.UnmappedEvent;
import com.aldogrand.sbpc.model.UnmappedMarket;
import com.aldogrand.sbpc.model.UnmappedRunner;

/**
 * <p>
 * <b>Title</b> ModelFactory
 * </p>
 * <p>
 * <b>Description</b> A factory used to create shared model objects from data access
 * DTOs. 
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
public class ModelFactory
{
    private static final Logger logger = LogManager.getLogger(ModelFactory.class);
    
    /**
     * Create a {@link Connector} from the {@link ConnectorDto}.
     * @param connectorDto
     * @return
     */
    public static Connector createConnector(ConnectorDto connectorDto)
    {
        assert connectorDto != null;
        
        logger.debug("Creating connector from dto " + connectorDto);        
        
        Connector connector = new Connector();
        connector.setId(connectorDto.getId());
        connector.setName(connectorDto.getName());
        connector.setEnabled(connectorDto.isEnabled());
        connector.setEventContributor(connectorDto.isEventContributor());
        connector.setOfferManagementEnabled(connectorDto.isOfferManagementEnabled());
        
        List<BettingPlatform> bettingPlatforms = new ArrayList<BettingPlatform>();        
        for (BettingPlatformDto bettingPlatformDto : connectorDto.getBettingPlatforms()) {
            bettingPlatforms.add(ModelFactory.createBettingPlatform(bettingPlatformDto));
        }
        
        if (!bettingPlatforms.isEmpty()) {
            connector.setBettingPlatforms(bettingPlatforms);
        } else {
        	connector.setBettingPlatforms(Collections.EMPTY_LIST);
        }
        
        List<Account> accounts = new ArrayList<Account>();
        for (AccountDto accountDto : connectorDto.getAccounts()) {
            accounts.add(ModelFactory.createAccount(accountDto));
        }
        
        if (!accounts.isEmpty()) {
            connector.setAccounts(accounts);
        } else {
        	connector.setAccounts(Collections.EMPTY_LIST);
        }
        
        return connector;
    }
    
    /**
     * Create a {@link BettingPlatform} from a {@link BettingPlatformDto}.
     * @param bettingPlatformDto
     * @return
     */
    public static BettingPlatform createBettingPlatform(BettingPlatformDto bettingPlatformDto) {
        assert bettingPlatformDto != null;

        logger.debug("Creating betting platform from dto " + bettingPlatformDto);

        BettingPlatform bettingPlatform = new BettingPlatform();
        bettingPlatform.setId(bettingPlatformDto.getId());
        bettingPlatform.setConnectorId(bettingPlatformDto.getConnector().getId());
        bettingPlatform.setConnectorName(bettingPlatformDto.getConnector().getName());
        bettingPlatform.setName(bettingPlatformDto.getName());
        
        return bettingPlatform;
    }
    
    /**
     * Create an {@link Account} from an {@link AccountDto}.
     * @param accountDto
     * @return
     */
    public static Account createAccount(AccountDto accountDto) {
        assert accountDto != null;
        
        logger.debug("Creating account from dto " + accountDto.getUsername());
        
        Account a = new Account();
        a.setId(accountDto.getId());
        a.setUsername(accountDto.getUsername());
        a.setPassword(accountDto.getPassword());
        a.setCurrency(accountDto.getCurrency());
        List<AccountProperty> accountProperties = new ArrayList<AccountProperty>();
        for (AccountPropertyDto prop : accountDto.getAccountProperties()) {
            AccountProperty p = new AccountProperty();
            p.setName(prop.getName());
            p.setValue(prop.getValue());
            accountProperties.add(p);
        }
        
        if (!accountProperties.isEmpty()) {
            a.setOtherProperties(accountProperties);
        } else {
        	a.setOtherProperties(Collections.EMPTY_LIST);
        }
        
        a.setConnectorName(accountDto.getConnector().getName());
        
        return a;
    }
    
    /**
     * Create a {@link MetaTag} from a {@link MetaTagDto}.
     * @param metaTagDto
     * @return
     */
    public static MetaTag createMetaTag(MetaTagDto metaTagDto) {
        assert metaTagDto != null;
        
        logger.debug("Creating meta-tag from dto " + metaTagDto.getId());
        
        MetaTag metaTag = new MetaTag();
        metaTag.setId(metaTagDto.getId());
        metaTag.setName(metaTagDto.getName());
        metaTag.setType(metaTagDto.getType());
        return metaTag;
    }
    
    /**
     * Create an {@link Event} from an {@link EventDto}.
     * @param eventDto
     * @param includeMappings
     * @return
     */
    public static Event createEvent(EventDto eventDto, boolean includeMappings) {
        assert eventDto != null;
        
        logger.debug("Creating event from dto " + eventDto.getId());
                
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setStartTime(eventDto.getStartTime());
        event.setEventStatus(eventDto.getEventStatus());
        
        List<MetaTag> metaTags = new ArrayList<MetaTag>();
        for (MetaTagDto metaTagDto : eventDto.getMetaTags()) {
            MetaTag metaTag = new MetaTag();
            metaTag.setId(metaTagDto.getId());
            metaTag.setName(metaTagDto.getName());
            metaTag.setType(metaTagDto.getType());
            metaTags.add(metaTag);
        }
        
		if (!metaTags.isEmpty()) {
            event.setMetaTags(metaTags);
        } else {
        	event.setMetaTags(Collections.EMPTY_LIST);
        }
        
        List<Market> markets = new ArrayList<Market>();
		for (MarketDto marketDto : eventDto.getMarkets()) {
            markets.add(createMarket(marketDto, includeMappings));
        }
        
		if (!markets.isEmpty()) {
            event.setMarkets(markets);
        } else {
        	event.setMarkets(Collections.EMPTY_LIST);
        }
        
		if (includeMappings) {
            List<Mapping> mappings = new ArrayList<Mapping>();
            for (SourceEventDto sourceEventDto : eventDto.getMappings()) {
                mappings.add(createMapping(sourceEventDto));
            }
            
            if (!mappings.isEmpty()) {
                event.setMappings(mappings);
            } else {
            	event.setMappings(Collections.EMPTY_LIST);
            }
        }
        
        event.setLastChanged(eventDto.getLastChangeTime());
        
        return event;
    }
    
    /**
     * Create a {@link Mapping} from the {@link SourceEventDto}.
     * @param sourceEventDto
     * @return
     */
	public static Mapping createMapping(SourceEventDto sourceEventDto) {
        assert sourceEventDto != null;
        
        logger.debug("Creating source event mapping from dto " + sourceEventDto.getId());
        
        Mapping mapping = new Mapping();
        mapping.setId(sourceEventDto.getId());
        mapping.setSourceId(sourceEventDto.getSourceId());
        mapping.setName(sourceEventDto.getSourceName());
        mapping.setConnectorId(sourceEventDto.getConnector().getId());
        mapping.setConnectorName(sourceEventDto.getConnector().getName());
        mapping.setLastFetched(sourceEventDto.getLastFetchTime());
        return mapping;
    }
    
    /**
     * Create an {@link UnmappedEvent} from the {@link SourceEventDto}.
     * @param sourceEventDto
     * @return
     */
    public static UnmappedEvent createUnmappedEvent(SourceEventDto sourceEventDto) {
        assert sourceEventDto != null;
        
        logger.debug("Creating unmapped event from dto " + sourceEventDto.getId());
        
        UnmappedEvent unmappedEvent = new UnmappedEvent();
        unmappedEvent.setId(sourceEventDto.getId());
        unmappedEvent.setConnectorId(sourceEventDto.getConnector().getId());
        unmappedEvent.setSourceId(sourceEventDto.getSourceId());
        unmappedEvent.setName(sourceEventDto.getSourceName());
        unmappedEvent.setStartTime(sourceEventDto.getStartTime());
        
        List<MetaTag> metaTags = new ArrayList<MetaTag>();
        for (MetaTagDto metaTagDto : sourceEventDto.getMetaTags()) {
            MetaTag metaTag = new MetaTag();
            metaTag.setId(metaTagDto.getId());
            metaTag.setName(metaTagDto.getName());
            metaTag.setType(metaTagDto.getType());
            metaTags.add(metaTag);
        }
        
        if (!metaTags.isEmpty()) {
            unmappedEvent.setMetaTags(metaTags);
        } else {
        	unmappedEvent.setMetaTags(Collections.EMPTY_LIST);
        }

        unmappedEvent.setLastFetched(sourceEventDto.getLastFetchTime());
        unmappedEvent.setLastChanged(sourceEventDto.getLastChangeTime());
        return unmappedEvent;
    }
    
    /**
     * Create a {@link Market} from a {@link MarketDto}.
     * @param marketDto
     * @param includeMappings
     * @return
     */
    public static Market createMarket(MarketDto marketDto, boolean includeMappings) {
        assert marketDto != null;
        
        logger.debug("Creating market from dto " + marketDto.getId());
        
        Market market = new Market();
        market.setId(marketDto.getId());
        market.setName(marketDto.getName());
        market.setEventId(marketDto.getEvent().getId());
        market.setEventName(marketDto.getEvent().getName());
        market.setType(marketDto.getType());
        market.setPeriod(marketDto.getPeriod());
        market.setHandicap(marketDto.getHandicap());
        market.setLastChanged(marketDto.getLastChangeTime());
        market.setMarketStatus(marketDto.getMarketStatus());
        
        List<Runner> runners = new ArrayList<Runner>();
        for (RunnerDto runnerDto : marketDto.getRunners()) {
            runners.add(createRunner(runnerDto, includeMappings));
        }
        
        if (!runners.isEmpty()) {
            market.setRunners(runners);
        } else {
        	market.setRunners(Collections.EMPTY_LIST);
        }
        
        if (includeMappings) {
            List<Mapping> mappings = new ArrayList<Mapping>();
            for (SourceMarketDto sourceMarketDto : marketDto.getMappings()) {
                mappings.add(createMapping(sourceMarketDto));
            }
            
            if (!mappings.isEmpty()) {
                market.setMappings(mappings);
            } else {
            	market.setMappings(Collections.EMPTY_LIST);
            }
        }
        
        return market;
    }

    /**
     * Create a {@link Mapping} from the {@link SourceMarketDto}.
     * @param sourceMarketDto
     * @return
     */
	public static Mapping createMapping(SourceMarketDto sourceMarketDto) {
        assert sourceMarketDto != null;
        
        logger.debug("Creating source market mapping from dto " + sourceMarketDto.getId());
        
        Mapping mapping = new Mapping();
        mapping.setId(sourceMarketDto.getId());
        mapping.setSourceId(sourceMarketDto.getSourceId());
        mapping.setName(sourceMarketDto.getSourceName());
        mapping.setConnectorId(sourceMarketDto.getConnector().getId());
        mapping.setConnectorName(sourceMarketDto.getConnector().getName());
        mapping.setLastFetched(sourceMarketDto.getLastFetchTime());
        return mapping;
    }
    
    /**
     * Create an {@link UnmappedMarket} from the {@link SourceMarketDto}.
     * @param sourceMarketDto
     * @return
     */
    public static UnmappedMarket createUnmappedMarket(SourceMarketDto sourceMarketDto) {
        assert sourceMarketDto != null;
        
        logger.debug("Creating unmapped market from dto " + sourceMarketDto.getId());
        
        UnmappedMarket unmappedMarket = new UnmappedMarket();
        unmappedMarket.setId(sourceMarketDto.getId());
        unmappedMarket.setEventId(sourceMarketDto.getSourceEvent().getEvent() != null 
                ? sourceMarketDto.getSourceEvent().getEvent().getId() : null);
        unmappedMarket.setConnectorId(sourceMarketDto.getConnector().getId());
        unmappedMarket.setSourceId(sourceMarketDto.getSourceId());
        unmappedMarket.setName(sourceMarketDto.getSourceName());
        unmappedMarket.setType(sourceMarketDto.getType());
        unmappedMarket.setPeriod(sourceMarketDto.getPeriod());
        unmappedMarket.setHandicap(sourceMarketDto.getHandicap());
        unmappedMarket.setLastFetched(sourceMarketDto.getLastFetchTime());
        unmappedMarket.setLastChanged(sourceMarketDto.getLastChangeTime());
        return unmappedMarket;
    }
    
    /**
     * Create a {@link Runner} from a {@link RunnerDto}.
     * @param runnerDto
     * @param includeMappings
     * @return
     */
    public static Runner createRunner(RunnerDto runnerDto, boolean includeMappings)
    {
        assert runnerDto != null;
        
        logger.debug("Creating runner from dto " + runnerDto.getId());
        
        Runner runner = new Runner();
        runner.setId(runnerDto.getId());
        runner.setName(runnerDto.getName());
        runner.setEventId(runnerDto.getMarket().getEvent().getId());
        runner.setEventName(runnerDto.getMarket().getEvent().getName());
        runner.setMarketId(runnerDto.getMarket().getId());
        runner.setMarketName(runnerDto.getMarket().getName());
        runner.setType(runnerDto.getType());
        runner.setSequence(runnerDto.getSequence());
        runner.setSide(runnerDto.getSide());
        runner.setHandicap(runnerDto.getHandicap());
        runner.setRotationNumber(runnerDto.getRotationNumber());
        runner.setRunnerStatus(runnerDto.getRunnerStatus());
        runner.setResultStatus(runnerDto.getResultStatus());
        
        List<Price> prices = new ArrayList<Price>();
        for (PriceDto priceDto : runnerDto.getPrices()) {
            prices.add(createPrice(priceDto));
        }
        
        if (!prices.isEmpty()) {
            runner.setPrices(prices);
        } else {
        	runner.setPrices(Collections.EMPTY_LIST);
        }
                
        if (includeMappings) {
            List<Mapping> mappings = new ArrayList<Mapping>();
            for (SourceRunnerDto sourceRunnerDto : runnerDto.getMappings()) {
                mappings.add(createMapping(sourceRunnerDto));
            }
            
            if (!mappings.isEmpty()) {
                runner.setMappings(mappings);
            } else {
            	runner.setMappings(Collections.EMPTY_LIST);
            }
        }
        
        runner.setLastChanged(runnerDto.getLastChangeTime());
        return runner;
    }
    
    /**
     * Create a {@link Mapping} from the {@link SourceRunnerDto}.
     * @param sourceRunnerDto
     * @return
     */
    public static Mapping createMapping(SourceRunnerDto sourceRunnerDto) {
        assert sourceRunnerDto != null;
        
        logger.debug("Creating source runner from dto " + sourceRunnerDto.getId());
        
        Mapping mapping = new Mapping();
        mapping.setId(sourceRunnerDto.getId());
        mapping.setSourceId(sourceRunnerDto.getSourceId());
        mapping.setName(sourceRunnerDto.getSourceName());
        mapping.setConnectorId(sourceRunnerDto.getConnector().getId());
        mapping.setConnectorName(sourceRunnerDto.getConnector().getName());
        mapping.setLastFetched(sourceRunnerDto.getLastFetchTime());
        return mapping;
    }
    
    /**
     * Create an {@link UnmappedRunner} from the {@link SourceRunnerDto}.
     * @param sourceRunnerDto
     * @return
     */
    public static UnmappedRunner createUnmappedRunner(SourceRunnerDto sourceRunnerDto) {
        assert sourceRunnerDto != null;
        
        logger.debug("Creating unmapped runner from dto " + sourceRunnerDto.getId());
        
        UnmappedRunner unmappedRunner = new UnmappedRunner();
        unmappedRunner.setId(sourceRunnerDto.getId());
        unmappedRunner.setEventId(sourceRunnerDto.getSourceMarket().getSourceEvent().getEvent() != null 
                ? sourceRunnerDto.getSourceMarket().getSourceEvent().getEvent().getId() : null);
        unmappedRunner.setMarketId(sourceRunnerDto.getSourceMarket().getMarket() != null 
                ? sourceRunnerDto.getSourceMarket().getMarket().getId() : null);
        unmappedRunner.setConnectorId(sourceRunnerDto.getConnector().getId());
        unmappedRunner.setSourceId(sourceRunnerDto.getSourceId());
        unmappedRunner.setName(sourceRunnerDto.getSourceName());
        unmappedRunner.setType(sourceRunnerDto.getType());
        unmappedRunner.setSide(sourceRunnerDto.getSide());
        unmappedRunner.setHandicap(sourceRunnerDto.getHandicap());
        unmappedRunner.setRotationNumber(sourceRunnerDto.getRotationNumber());
        unmappedRunner.setSequence(sourceRunnerDto.getSequence());
        unmappedRunner.setLastFetched(sourceRunnerDto.getLastFetchTime());
        unmappedRunner.setLastChanged(sourceRunnerDto.getLastChangeTime());
        return unmappedRunner;
    }
    
    /**
     * Create a {@link Price} from a {@link PriceDto}.
     * @param priceDto
     * @return
     */
    public static Price createPrice(PriceDto priceDto) {
        assert priceDto != null;
        
        logger.debug("Creating price from dto " + priceDto);
        
        Price price = new Price();
        price.setId(priceDto.getId());
        price.setConnectorId(priceDto.getBettingPlatform().getConnector().getId());
        price.setBettingPlatformId(priceDto.getBettingPlatform().getId());
        price.setEventId(priceDto.getRunner().getMarket().getEvent().getId());
        price.setMarketId(priceDto.getRunner().getMarket().getId());
        price.setRunnerId(priceDto.getRunner().getId());
        if ((priceDto.getSide().equals(PriceSide.BACK)) 
                || (priceDto.getSide().equals(PriceSide.LAY)))
        {
            price.setExchangeType(ExchangeType.BACKLAY);
		} else {
            price.setExchangeType(ExchangeType.BINARY);
        }
        price.setSide(priceDto.getSide());
        price.setSequence(priceDto.getSequence());
        price.setOdds(priceDto.getOdds());
        price.setOddsType(priceDto.getOddsType());
        price.setDecimalOdds(priceDto.getDecimalOdds());
        price.setAvailableAmount(priceDto.getAvailableAmount());
        price.setCurrency(priceDto.getCurrency());
        price.setLastFetched(priceDto.getLastFetchTime());
        price.setLastChanged(priceDto.getLastChangeTime());
        return price;
    }
    
    /**
     * Create a {@link Position} from a {@link PositionDto}.
     * @param positionDto
     * @return
     */
	public static Position createPosition(PositionDto positionDto) {
        assert positionDto != null;
        
        logger.debug("Creating position from dto " + positionDto);
                
        Position position = new Position();
        position.setId(positionDto.getId());
        position.setConnectorId(positionDto.getBettingPlatform().getConnector().getId());
        position.setBettingPlatformId(positionDto.getBettingPlatform().getId());
        position.setAccountId(positionDto.getAccount().getId());
        position.setEventId(positionDto.getRunner().getMarket().getEvent().getId());
        position.setMarketId(positionDto.getRunner().getMarket().getId());
        position.setRunnerId(positionDto.getRunner().getId());
        position.setCurrency(positionDto.getCurrency());
        position.setValue(positionDto.getValue());
        position.setLastChanged(positionDto.getLastChangeTime());
        position.setLastFetched(positionDto.getLastFetchTime());
        return position;
    }
    
    /**
     * Create an {@link Offer} from an {@link OfferDto}.
     * @param offerDto
     * @return
     */
    public static Offer createOffer(OfferDto offerDto)
    {
        assert offerDto != null;
        
        logger.debug("Creating offer from dto " + offerDto);
        
        Offer offer = new Offer();
        offer.setId(offerDto.getId());
        offer.setConnectorId(offerDto.getConnector().getId());
        offer.setEventId(offerDto.getRunner().getMarket().getEvent().getId());
        offer.setMarketId(offerDto.getRunner().getMarket().getId());
        offer.setRunnerId(offerDto.getRunner().getId());
        offer.setAccountId(offerDto.getAccount().getId());
        offer.setSourceId(offerDto.getSourceId());
        offer.setOdds(offerDto.getOdds());
        offer.setOddsType(offerDto.getOddsType());
        offer.setDecimalOdds(offerDto.getDecimalOdds());
        offer.setStake(offerDto.getStake());
        offer.setMatchedAmount(offerDto.getMatchedAmount());
        offer.setAvailableAmount(offerDto.getAvailableAmount());
        offer.setCurrency(offerDto.getCurrency());
        if ((offerDto.getSide().equals(PriceSide.BACK)) 
                || (offerDto.getSide().equals(PriceSide.LAY)))
        {
            offer.setExchangeType(ExchangeType.BACKLAY);
		} else {
            offer.setExchangeType(ExchangeType.BINARY);
        }
        offer.setSide(offerDto.getSide());
        offer.setOfferTime(offerDto.getOfferTime());
        offer.setStatus(offerDto.getStatus());
        offer.setLastFetched(offerDto.getLastFetchTime());
        offer.setLastChanged(offerDto.getLastChangeTime());
        List<Bet> bets = new ArrayList<Bet>();
        for (BetDto betDto : offerDto.getBets())
        {
            bets.add(createBet(betDto));
        }
        
        if (!bets.isEmpty()) {
            offer.setBets(bets);
        } else {
        	offer.setBets(Collections.EMPTY_LIST);
        }
        
        return offer;
    }
    
    /**
     * Create a {@link Bet} from a {@link BetDto}.
     * @param betDto
     * @return
     */
    public static Bet createBet(BetDto betDto)
    {
        assert betDto != null;
        
        logger.debug("Creating bet from dto " + betDto);
        
        Bet bet = new Bet();
        bet.setId(betDto.getId());
        if (betDto.getBettingPlatform() != null) {
        	bet.setBettingPlatformId(betDto.getBettingPlatform().getId());
        	bet.setBettingPlatformName(betDto.getBettingPlatform().getName());
        }
        bet.setSourceId(betDto.getSourceId());
        bet.setOdds(betDto.getOdds());
        bet.setOddsType(betDto.getOddsType());
        bet.setDecimalOdds(betDto.getDecimalOdds());
        bet.setStake(betDto.getStake());
        bet.setCurrency(betDto.getCurrency());
        bet.setCreationTime(betDto.getCreationTime());
        return bet;
    }
    
    /**
     * Create a {@link GFeedMidPoint} from a {@link GFeedMidPointDto}.
     * @param midPointDto
     * @return
     */
    public static GFeedMidPoint createGFeedMidPoint(GFeedMidPointDto midPointDto)
    {
        assert midPointDto != null;
        
        logger.debug("Creating GFeed Mid Point from dto " + midPointDto);
        
        GFeedMidPoint midPoint = new GFeedMidPoint();
        midPoint.setId(midPointDto.getId());
        midPoint.setRunnerId(midPointDto.getRunner().getId());
        midPoint.setBettingPlatformId(midPointDto.getBettingPlatform().getId());
        midPoint.setEnabled(midPointDto.isEnabled());
        midPoint.setOdds(midPointDto.getOdds());
        midPoint.setOddsType(midPointDto.getOddsType());
        return midPoint;
    }
    
    /**
     * Create a {@link GFeedSetting} from a {@link GFeedSettingDto}.
     * @param gFeedSettingDto
     * @return
     */
    public static GFeedSetting createGFeedSetting(GFeedSettingDto gFeedSettingDto)
    {
        assert gFeedSettingDto != null;
        
        logger.debug("Creating gFeedSetting from dto " + gFeedSettingDto.getId());
        
        GFeedSetting gFeedSetting = new GFeedSetting();
        gFeedSetting.setId(gFeedSettingDto.getId());
        gFeedSetting.setEventId(gFeedSettingDto.getEvent().getId());
        gFeedSetting.setOtherPriceThreshold(gFeedSettingDto.getOtherPriceThreshold());
        gFeedSetting.setOldPriceThreshold(gFeedSettingDto.getOldPriceThreshold());
        gFeedSetting.setAnchorPercentage(gFeedSettingDto.getAnchorPercentage());
        return gFeedSetting;
    }

    /**
     * Create a {@link EventIncident} from a given {@link CurrentDangerballStatus}
     * 
     * @param currentDangerballStatus
     * @param eventIncidentDto
     * @return
     */
    public static EventIncident createEventIncident(CurrentDangerballStatus currentDangerballStatus,
			SourceEventIncidentDto eventIncidentDto) {

    	ScoreDto currentScore = eventIncidentDto.getScore();
    	
    	Score score = new Score(
    		currentScore.getType(),
    		currentScore.getHome(),
    		currentScore.getAway()
    	);

    	String ocurredTime = null;
    	if (eventIncidentDto.getElapsedTime() != null){
    		ocurredTime = new SimpleDateFormat("HH:mm:ss").format(eventIncidentDto.getElapsedTime());
    	}
    	
		EventIncident incident =
			new EventIncident(
				eventIncidentDto.getSourceEvent().getEvent().getId(),
				eventIncidentDto.getSequenceNumber(), 
    			currentDangerballStatus,
    			eventIncidentDto.getParticipantId(),
    			eventIncidentDto.getIncidentType(), 
    			ocurredTime,
    			score,
    			eventIncidentDto.getEventPhase()
    		);

		return incident;
	}
    

    public static SourceEvent createSourceEvent(SourceEventDto sourceEventDto) {
    	assert sourceEventDto != null;
    	    	
    	SourceEvent sourceEvent = new SourceEvent();
    	sourceEvent.setId(sourceEventDto.getId());
    	sourceEvent.setVersion(sourceEventDto.getVersion());
    	sourceEvent.setConnectorId(sourceEventDto.getConnector().getId());
    	sourceEvent.setCreator(sourceEventDto.isCreator());
    	if (sourceEventDto.getEvent() != null) {
    		sourceEvent.setEventId(sourceEventDto.getEvent().getId());
    	}
    	sourceEvent.setMarkets(createSourceMarket(sourceEventDto.getMarkets()));
    	sourceEvent.setMetaTags(createMetaTags(sourceEventDto.getMetaTags()));
    	sourceEvent.setSourceId(sourceEventDto.getSourceId());
    	sourceEvent.setSourceName(sourceEventDto.getSourceName());
    	sourceEvent.setStartTime(sourceEventDto.getStartTime());
    	sourceEvent.setStatus(sourceEventDto.getStatus());
    	
    	return sourceEvent;
    }
    
    public static List<SourceMarket> createSourceMarket(List<SourceMarketDto> sourceMarketDtos) {
    	assert sourceMarketDtos != null;
    	
    	List<SourceMarket> sourceMarkets = new ArrayList<SourceMarket>();    	
    	for (SourceMarketDto sourceMarketDto : sourceMarketDtos) {
    		if (sourceMarketDto != null) {
    			sourceMarkets.add(createSourceMarket(sourceMarketDto));
    		}
    	}
    	
    	return sourceMarkets;
    }
    
    public static SourceMarket createSourceMarket(SourceMarketDto sourceMarketDto) {
    	assert sourceMarketDto != null;
    	SourceMarket sourceMarket = new SourceMarket();
    	sourceMarket.setId(sourceMarketDto.getId());
    	sourceMarket.setVersion(sourceMarketDto.getVersion());
    	sourceMarket.setConnectorId(sourceMarketDto.getConnector().getId());
    	sourceMarket.setCreator(sourceMarketDto.isCreator());
    	sourceMarket.setHandicap(sourceMarketDto.getHandicap());
    	if (sourceMarketDto.getMarket() != null) {
    		sourceMarket.setMarketId(sourceMarketDto.getMarket().getId());
    	}
    	sourceMarket.setMarketStatus(sourceMarketDto.getMarketStatus());
    	sourceMarket.setPeriod(sourceMarketDto.getPeriod());
    	sourceMarket.setRunners(createSourceRunners(sourceMarketDto.getRunners()));
    	sourceMarket.setSourceEventId(sourceMarketDto.getSourceEvent().getId());
    	sourceMarket.setSourceId(sourceMarketDto.getSourceId());
    	sourceMarket.setSourceName(sourceMarketDto.getSourceName());
    	sourceMarket.setType(sourceMarketDto.getType());
    	
    	return sourceMarket;
    }
    
    public static List<SourceRunner> createSourceRunners(List<SourceRunnerDto> sourceRunnerDtos) {
    	assert sourceRunnerDtos != null;
    	
    	List<SourceRunner> sourceRunners = new ArrayList<SourceRunner>();    	
    	for (SourceRunnerDto sourceRunnerDto : sourceRunnerDtos) {
    		sourceRunners.add(createSourceRunner(sourceRunnerDto));
    	}    	
    	return sourceRunners;
    }
    
    public static SourceRunner createSourceRunner(SourceRunnerDto sourceRunnerDto) {
    	assert sourceRunnerDto != null;
    	
    	SourceRunner sourceRunner = new SourceRunner();
    	sourceRunner.setId(sourceRunnerDto.getId());
    	sourceRunner.setVersion(sourceRunnerDto.getVersion());
    	sourceRunner.setConnectorId(sourceRunnerDto.getConnector().getId());
    	sourceRunner.setCreator(sourceRunnerDto.isCreator());
    	sourceRunner.setHandicap(sourceRunnerDto.getHandicap());
    	sourceRunner.setResultStatus(sourceRunnerDto.getResultStatus());
    	sourceRunner.setRotationNumber(sourceRunnerDto.getRotationNumber());
    	
    	if (sourceRunnerDto.getRunner() != null) {
    		sourceRunner.setRunnerId(sourceRunnerDto.getRunner().getId());
    	}
    	
    	sourceRunner.setRunnerStatus(sourceRunnerDto.getRunnerStatus());
    	sourceRunner.setSequence(sourceRunnerDto.getSequence());
    	sourceRunner.setSide(sourceRunnerDto.getSide());
    	sourceRunner.setSourceId(sourceRunnerDto.getSourceId());
    	sourceRunner.setSourceMarketId(sourceRunnerDto.getSourceMarket().getId());
    	sourceRunner.setSourceEventId(sourceRunnerDto.getSourceMarket().getSourceEvent().getId());
    	sourceRunner.setSourceName(sourceRunnerDto.getSourceName());
    	sourceRunner.setType(sourceRunnerDto.getType());
    	
    	return sourceRunner;
    }
    
    
    public static Set<MetaTag> createMetaTags(Set<MetaTagDto> metaTagDtos) {
        assert metaTagDtos != null;
        
        HashSet<MetaTag> metaTags = new HashSet<MetaTag>();    	
    	for (MetaTagDto metaTagDto : metaTagDtos) {
    		metaTags.add(createMetaTag(metaTagDto));
    	}
        return metaTags;
    }
    

    /**
     * Creates a {@link Score} from a given {@link ScoreDto}
     * 
     * @param scoreDto
     * @return
     */
    public static Score createScore(ScoreDto scoreDto) {
    	Score score = null;

    	if (scoreDto != null) {
    		score = new Score();

	    	score.setScoreType(scoreDto.getType());
	    	score.setHome(scoreDto.getHome());
	    	score.setAway(scoreDto.getAway());
    	}
    	
    	return score;
    }
    
    /**
     * Prevent instantiation.
     */
	private ModelFactory() {
	}

}
