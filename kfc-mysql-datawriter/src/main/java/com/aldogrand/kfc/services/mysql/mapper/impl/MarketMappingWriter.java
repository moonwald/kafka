/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper.impl;

import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.ModelFactory;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.SourceMarket;
import com.aldogrand.kfc.services.mysql.mapper.MarketMappingWriterInterface;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

/**
 * <p>
 * <b>Title</b> MarketMappingWriter.java
 * </p>
 * com.aldogrand.kfc.services.mysql.impl.mapper
 * <p>
 * <b>Description</b> kfc-mysql-datawriter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
@Transactional
public class MarketMappingWriter implements MarketMappingWriterInterface
{

	private final Logger	logger	= LogManager.getLogger(getClass());

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	private MarketDao		marketDao;
	private SourceMarketDao	sourceMarketDao;

	/**
	 * 
	 */
	public MarketMappingWriter(MarketDao marketDao, SourceMarketDao sourceMarketDao)
	{
		this.marketDao = marketDao;
		this.sourceMarketDao = sourceMarketDao;
	}

	/**
	 * @param sourceMarket
	 * @param market
	 */	
	@Override
	public Market updateMarket(SourceMarket sourceMarket, Market market) throws RuntimeException
	{
		Market returnMarket = null;
		try
		{
			assert market != null;
			assert sourceMarket != null;
			MarketDto marketDto = marketDao.getMarket(market.getId());
			SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(sourceMarket.getId(), false);
			if (marketDto != null && sourceMarketDto != null)
			{
				logger.debug(String.format("Mapping source market %s to existing market %s.", 
						sourceMarketDto.getId(), marketDto.getId()));	

				marketDto.setLastChangeTime(new Date());
				marketDto = marketDao.saveMarket(marketDto);

				sourceMarketDto.setMarket(marketDto);
				sourceMarketDao.saveSourceMarket(sourceMarketDto);
				returnMarket = ModelFactory.createMarket(marketDto, false);
				logger.debug(String.format("Mapping source market %s - %s from connector %s - %s market %s - %s.", 
						sourceMarketDto.getId(), sourceMarketDto.getSourceName(), sourceMarketDto.getConnector().getId(),
						sourceMarketDto.getConnector().getName(), marketDto.getId(), marketDto.getName()));	

			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating the mapping for the existing market %s.", sourceMarket.getSourceName() ), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarket> builder = MessageBuilder.withPayload(sourceMarket).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing market.", te);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating the mapping for the existing market.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarket> builder = MessageBuilder.withPayload(sourceMarket).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing market.", ae);
			}
		}
		return returnMarket;
	}

	/**
	 * @param sourceMarket
	 * @param market
	 */	
	@Override
	public Market createMarket(SourceMarket sourceMarket, Market market) throws RuntimeException
	{
		Market returnMarket = null;
		try
		{
			assert sourceMarket != null;
			SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(sourceMarket.getId(), false);
			if (sourceMarketDto != null && sourceMarketDto.getMarket() == null && sourceMarketDto.getSourceEvent() != null
					&& sourceMarketDto.getSourceEvent().getEvent() != null)
			{
				EventDto eventDto = sourceMarketDto.getSourceEvent().getEvent();
				MarketDto marketDto = new MarketDto();
				marketDto.setEvent(sourceMarketDto.getSourceEvent().getEvent());
				marketDto.setName(sourceMarketDto.getSourceName());
				marketDto.setType(sourceMarketDto.getType());
				marketDto.setPeriod(sourceMarketDto.getPeriod());
				marketDto.setHandicap(sourceMarketDto.getHandicap());
				marketDto.setLastChangeTime(new Date());
				marketDto.setMarketStatus(sourceMarketDto.getMarketStatus());

				marketDto = marketDao.saveMarket(marketDto);
				sourceMarketDto.setMarket(marketDto);
				sourceMarketDto.setCreator(true);
				sourceMarketDao.saveSourceMarket(sourceMarketDto);
				eventDto.getMarkets().add(marketDto);
				returnMarket = ModelFactory.createMarket(marketDto, false);
				logger.debug(String.format("Mapping source market %s - %s from connector %s - %s market %s - %s.", 
						sourceMarketDto.getId(), sourceMarketDto.getSourceName(), sourceMarketDto.getConnector().getId(),
						sourceMarketDto.getConnector().getName(), marketDto.getId(), marketDto.getName()));				}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error creating the mapping for the existing market %s.", sourceMarket.getSourceName() ), 
					te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarket> builder = MessageBuilder.withPayload(sourceMarket).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for  market.", te);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error creating the mapping for  market.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarket> builder = MessageBuilder.withPayload(sourceMarket).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for  market.", ae);
			}
		}
		return returnMarket;
	}
}
