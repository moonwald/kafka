package com.aldogrand.kfc.services.mysql.impl;


import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.connectors.model.Account;
import com.aldogrand.sbpc.connectors.model.Connector;
import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.sbpc.connectors.model.Offer;
import com.aldogrand.sbpc.connectors.model.OfferMatch;
import com.aldogrand.sbpc.connectors.model.Position;
import com.aldogrand.sbpc.connectors.model.Price;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.connectors.model.Score;
import com.aldogrand.sbpc.connectors.model.SettledBet;
import com.aldogrand.sbpc.dataaccess.AccountDao;
import com.aldogrand.sbpc.dataaccess.BetDao;
import com.aldogrand.sbpc.dataaccess.BettingPlatformDao;
import com.aldogrand.sbpc.dataaccess.ConnectorDao;
import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.MetaTagDao;
import com.aldogrand.sbpc.dataaccess.OfferDao;
import com.aldogrand.sbpc.dataaccess.PositionDao;
import com.aldogrand.sbpc.dataaccess.PriceDao;
import com.aldogrand.sbpc.dataaccess.ReportJobInfoDao;
import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.SettledBetDao;
import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.SourceEventIncidentDao;
import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.BetDto;
import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.MetaTagDto;
import com.aldogrand.sbpc.dataaccess.model.ModelFactory;
import com.aldogrand.sbpc.dataaccess.model.OfferDto;
import com.aldogrand.sbpc.dataaccess.model.PositionDto;
import com.aldogrand.sbpc.dataaccess.model.PriceDto;
import com.aldogrand.sbpc.dataaccess.model.ReportJobInfoDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.dataaccess.model.ScoreDto;
import com.aldogrand.sbpc.dataaccess.model.SettledBetDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventIncidentDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto;
import com.aldogrand.sbpc.model.BettingPlatform;
import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.sbpc.model.ScoreType;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.sbpc.model.SourceMarket;
import com.aldogrand.sbpc.model.SourceRunner;
import com.aldogrand.sbpc.services.update.impl.Update;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.KFCEventFactoryImpl;
import com.aldogrand.kfc.msg.events.fetcher.AccountReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.BettingPlatformReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.ConnectorReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventIncidentReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.MarketsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.OffersReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PositionsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PricesReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SettledBetsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceRunnersReceivedEvent;
import com.aldogrand.kfc.msg.events.raw.AccountUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.BettingPlatformCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.BettingPlatformUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.ConnectorCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.ConnectorUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.EventIncidentCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PositionCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PositionUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceRemovedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerUpdatedEvent;
import com.aldogrand.kfc.services.mysql.SourceDataWriterService;
import com.aldogrand.kfc.utils.general.DateUtils;
import com.aldogrand.kfc.utils.general.ObjectUtils;
/**
 * 
 * <p>
 * <b>Title</b> DataWriterServiceImpl
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * 
 * @author aldogrand
 *
 */
@Transactional
public class SourceDataWriterServiceImpl implements SourceDataWriterService
{

	private final Logger		logger				= LogManager.getLogger(getClass());

	private static final String	JOB_SETTLED_BETS	= "SETTLED_BETS";

	private static final String	STATUS_ERROR		= "ERROR";

	private static final String	STATUS_SUCCEED		= "SUCCEED";
	
	private static final List<EventStatus> LIVE_EVENT_STATUS = Arrays.asList(
			EventStatus.FIRST_HALF, EventStatus.HALF_TIME, EventStatus.SECOND_HALF, EventStatus.EXTRA_TIME_FIRST_HALF,
			EventStatus.BEFORE_EXTRA_TIME, EventStatus.EXTRA_TIME_HALF_TIME, EventStatus.EXTRA_TIME_SECOND_HALF,
			EventStatus.BEFORE_PENALTY_SHOOTOUT, EventStatus.PENALTY_SHOOTOUT);
	private static String LMAX_KEY_HEADER = "KEY";
	private List<Long> dangerballConnectors;
	
	private List<String> dangerballBettingPlatforms;

	private static final int scale = 8;
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	
	private ConnectorDao		connectorDao;

	private AccountDao			accountDao;

	private BettingPlatformDao	bettingPlatformDao;

	private EventDao			eventDao;

	private SourceEventDao		sourceEventDao;

	private MetaTagDao			metaTagDao;

	private MarketDao			marketDao;

	private SourceMarketDao		sourceMarketDao;

	private RunnerDao			runnerDao;

	private SourceRunnerDao		sourceRunnerDao;

	private PriceDao			priceDao;

	private PositionDao			positionDao;

	private OfferDao			offerDao;

	private BetDao				betDao;

	private SourceEventIncidentDao sourceEventIncidentDao;

	private SettledBetDao		settledBetDao;

	private ReportJobInfoDao	reportJobInfoDao;

	private KFCProducer			kafkaPublisher;
	
	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel		errorChannel;
	
	@Autowired
	@Qualifier("LMAXInputChannelDataProcessor")
	private MessageChannel     lmaxInputChannelDataProcessor;

	@Autowired
	@Qualifier("KafkaProducerChannel")
	private MessageChannel     kafkaProducerChannel;

	@Override
	public void write(@Headers MessageHeaders headers, SourceEventReceivedEvent payload)
	{
		try
		{
			logger.debug("Event write request: " + payload + ", headers: " + headers);			
			logger.debug(String.format("In OrderRouter.  connectorId=%s  Event=%s ", 
					payload.getIntegrationModuleId(), payload.getEvent().toString()));
			logger.info("DW key:"+headers.get("KEY") +" T:"+Thread.currentThread().getName());
			updateEvent(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating SourceEventReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceEventReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceEventReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating SourceEventReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceEventReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceEventReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, SourceMarketsReceivedEvent payload)
	{
		try
		{
			logger.debug("Market write request: " + payload + ", headers: " + headers);
			updateMarket(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating SourceMarketsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceMarketsReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating SourceMarketsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceMarketsReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, MarketsClosedStatusReceivedEvent payload)
	{
		try
		{
			logger.debug("Market Closed Status write request: " + payload + ", headers: " + headers);
			updateMarketClosedStatus(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating SourceMarketsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<MarketsClosedStatusReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceMarketsReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating SourceMarketsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<MarketsClosedStatusReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceMarketsReceivedEvent.", ae);
			}
		}
	}


	@Override
	public void write(@Headers MessageHeaders headers, SourceRunnersReceivedEvent payload)
	{
		try
		{
			logger.debug("Runner write request: " + payload + ", headers: " + headers);
			updateRunner(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating SourceRunnerReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnersReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceRunnerReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating SourceRunnerReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnersReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating SourceRunnerReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, PricesReceivedEvent payload)
	{
		try
		{
			logger.debug("Price write request: " + payload + ", headers: " + headers);
			updatePrices(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating PricesReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<PricesReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating PricesReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating PricesReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<PricesReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating PricesReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, AccountReceivedEvent payload)
	{
		try
		{
			logger.debug("Account write request: " + payload + ", headers: " + headers);
			updateAccountData(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating AccountReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<AccountReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating AccountReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating AccountReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<AccountReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating AccountReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, ConnectorReceivedEvent payload)
	{
		try
		{
			logger.debug("Connector write request: " + payload + ", headers: " + headers);
			updateConnector(payload.getConnectorModel());
		} catch (RuntimeException rte)
		{
			logger.error("Error updating ConnectorReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<ConnectorReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating ConnectorReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating ConnectorReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<ConnectorReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating ConnectorReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, EventIncidentReceivedEvent payload)
	{
		try
		{
			logger.debug("Event Incident Received write request: " + payload + ", headers: " + headers);
			updateEventIncident(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating EventIncidentReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<EventIncidentReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating EventIncidentReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating EventIncidentReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<EventIncidentReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating EventIncidentReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, OffersReceivedEvent payload)
	{
		try
		{
			logger.debug("Offer write request: " + payload + ", headers: " + headers);
			updateOffer(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating OfferReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<OffersReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating OfferReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating OfferReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<OffersReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating OfferReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, PositionsReceivedEvent payload)
	{
		try
		{
			logger.debug("Position write request: " + payload + ", headers: " + headers);
			updatePosition(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating PositionReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<PositionsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating PositionReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating PositionReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<PositionsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating PositionReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, SettledBetsReceivedEvent payload)
	{
		try
		{
			logger.debug("SettledBet write request: " + payload + ", headers: " + headers);
			settledBet(payload.getIntegrationModuleId(), payload.getAccountId(), payload.getSettledBets());
		} catch (RuntimeException rte)
		{
			logger.error("Error updating SettledBetsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SettledBetsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating SettledBetsReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating SettledBetsReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<SettledBetsReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating SettledBetsReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, BettingPlatformReceivedEvent payload)
	{
		try
		{
			logger.debug("Betting Platform write request: " + payload + ", headers: " + headers);
			updateBettingPlatform(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating BettingPlatformReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<BettingPlatformReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating BettingPlatformReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating BettingPlatformReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<BettingPlatformReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating BettingPlatformReceivedEvent.", ae);
			}
		}
	}

	@Override
	public void write(@Headers MessageHeaders headers, EventsClosedStatusReceivedEvent payload)
	{
		try
		{
			updateEventClosedStatus(payload);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating EventsClosedStatusReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<EventsClosedStatusReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating EventsClosedStatusReceivedEvent.", rte);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating EventsClosedStatusReceivedEvent.");
			if (errorChannel != null)
			{
				MessageBuilder<EventsClosedStatusReceivedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating EventsClosedStatusReceivedEvent.", ae);
			}
		}
	}

	/**
	 * Update a Account from the {@link Account}
	 * @param payload TODO
	 */
	@Transactional
	public void updateAccountData(final AccountReceivedEvent payload)
	{
		String integrationModuleId = payload.getIntegrationModuleId();
		Long accountId = payload.getAccountId();
		Account account  = payload.getAccount();
		
		assert integrationModuleId != null;
		assert account != null;
		assert accountId != null;

		// If we receive an update account then accountDto should exist
		try
		{
			long startTime = System.currentTimeMillis();
			AccountDto accountDto = accountDao.getAccount(accountId);

			Long connectorId = Long.valueOf(integrationModuleId);
			boolean updateRequired = false;

			if (account.getBalance() != null)
			{
				if (accountDto.getBalance() != null)
				{
					if (Math.abs(account.getBalance().doubleValue() - accountDto.getBalance().doubleValue()) > 0.01)
					{
						accountDto.setBalance(account.getBalance());
						updateRequired = true;
					}
				} else
				{
					accountDto.setBalance(account.getBalance());
					updateRequired = true;
				}
			} else
			{
				logger.warn(String.format("Error updating balance for account %s from connector %s. Balance was null.", 
						accountId, integrationModuleId));
			}

			if (account.getAvailableAmount() != null)
			{
				if (accountDto.getAvailableAmount() != null)
				{
					if (Math.abs(account.getAvailableAmount().doubleValue() - accountDto.getAvailableAmount().doubleValue()) > 0.01)
					{
						accountDto.setAvailableAmount(account.getAvailableAmount());
						updateRequired = true;
					}
				} else
				{
					accountDto.setAvailableAmount(account.getAvailableAmount());
					updateRequired = true;
				}
			} else
			{

				logger.warn(String.format("Error available-amount balance for account %s from connector %s. Available-amount was null.", 
						accountId, integrationModuleId));
			}

			if (updateRequired)
			{
				accountDto = accountDao.updateAccount(accountDto);
				AccountUpdatedEvent accountUpdated = new AccountUpdatedEvent();
				accountUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
				accountUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
				accountUpdated.setAccount(ModelFactory.createAccount(accountDto));
				KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
				kafkaProducerChannel.send(KFCEvent.createMessage(accountUpdated));
			}

			long endTime = System.currentTimeMillis();
			
			logger.debug(String.format("Completed updating account %s from integrationModuleId %s in %s seconds.", 
					accountId, integrationModuleId, Double.toString(((endTime - startTime) / 1000.0))));
		} catch (NumberFormatException t)
		{

			logger.error(String.format("Format error in the integrationModuleId Id %s.", integrationModuleId), t);

			if (errorChannel != null)
			{
				MessageBuilder<Account> builder = MessageBuilder.withPayload(account).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", t);
			}
		} catch (RuntimeException rte) {
			logger.error(String.format("Error updating account %s from integrationModuleId %s.", accountId, integrationModuleId), rte);			

			if (errorChannel != null)
			{
				MessageBuilder<Account> builder = MessageBuilder.withPayload(account).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", rte);
			}
		}
	}

	/**
	 * Update a integrationModuleId
	 * 
	 * @param integrationModuleId
	 *            Connector model
	 */
	@Transactional
	public void updateConnector(Connector integrationModule)
	{
		assert integrationModule != null;

		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			ConnectorDto connectorDto = connectorDao.getConnector(integrationModule.getName());
			Update.Type updated = null;
			Date now = new Date();

			// New integrationModuleId
			if (connectorDto == null)
			{
				connectorDto = new ConnectorDto();
				connectorDto.setCreationTime(now);
				updated = Update.Type.ADDED;
			} else
			{
                if (!ObjectUtils.areEqual(connectorDto.isEnabled(), integrationModule.isEnabled())) {                  
                  connectorDto.setLastChangeTime(now);
                }
                if (!ObjectUtils
                    .areEqual(connectorDto.isEventContributor(), integrationModule.isEventContributor())) {                  
                  connectorDto.setLastChangeTime(now);
                }
                if (!ObjectUtils.areEqual(connectorDto.isOfferManagementEnabled(),
                    integrationModule.isOfferManagementEnabled())) {                  
                  connectorDto.setLastChangeTime(now);
                }                
				updated = Update.Type.CHANGED;
			}

			connectorDto.setName(integrationModule.getName());
			connectorDto.setEnabled(integrationModule.isEnabled());
			connectorDto.setEventContributor(integrationModule.isEventContributor());
			connectorDto.setOfferManagementEnabled(integrationModule.isOfferManagementEnabled());			
			connectorDto.setLastFetchTime(now);

			connectorDto = doUpdateConnector(connectorDto);
			if (updated.equals(Update.Type.ADDED))
			{
				// Send to kafka a integrationModuleId event created
				ConnectorCreatedEvent connectorCreated = new ConnectorCreatedEvent();
				connectorCreated.setIntegrationModuleId(String.valueOf(connectorDto.getId()));
				connectorCreated.setIntegrationModuleName(connectorDto.getName());
				connectorCreated.setConnectorData(ModelFactory.createConnector(connectorDto));
				kafkaProducerChannel.send(KFCEvent.createMessage(connectorCreated));

			} else if (updated.equals(Update.Type.CHANGED))
			{
				// Send to kafka a integrationModuleId event updated
				ConnectorUpdatedEvent connectorUpdated = new ConnectorUpdatedEvent();
				connectorUpdated.setIntegrationModuleId(String.valueOf(connectorDto.getId()));
				connectorUpdated.setIntegrationModuleName(connectorDto.getName());
				connectorUpdated.setConnectorData(ModelFactory.createConnector(connectorDto));
				kafkaProducerChannel.send(KFCEvent.createMessage(connectorUpdated));
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating integrationModuleId %s.", integrationModule), te.getMostSpecificCause());
		
			if (errorChannel != null)
			{
				MessageBuilder<Connector> builder = MessageBuilder.withPayload(integrationModule).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", te);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating from integrationModuleId %s.", integrationModule), rte);

			if (errorChannel != null)
			{
				MessageBuilder<Connector> builder = MessageBuilder.withPayload(integrationModule).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", rte);
			}
		}
	}

	/**
	 * Update the event from the {@link Event}.
	 * @param payload TODO
	 */
	@Transactional
	public void updateEvent(final SourceEventReceivedEvent payload)
	{
		String integrationModuleId = payload.getIntegrationModuleId(); 
		Event event = payload.getEvent();
		assert event != null;
		assert integrationModuleId != null;

		logger.debug(String.format("Updating event %s from integrationModuleId %s.", event, integrationModuleId));

		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			Long connectorId = Long.valueOf(integrationModuleId);
			// Write in Database
			Update<SourceEvent> update = doUpdateEvent(connectorId, event);
			// Send write notifications
			if (update != null)
			{
				if (update.getType() != null && update.getType().equals(Update.Type.ADDED))
				{
					// Send to kafka a source event created
					SourceEventCreatedEvent eventCreated = new SourceEventCreatedEvent();
					eventCreated.setIntegrationModuleId(payload.getIntegrationModuleId());
					eventCreated.setIntegrationModuleName(payload.getIntegrationModuleName());
					eventCreated.setSourceEvent(update.getSubject());
					kafkaProducerChannel.send(KFCEvent.createMessage(eventCreated));
				
				} else if (update.getType() != null && update.getType().equals(Update.Type.CHANGED))
				{
					// Send to kafka a source event updated
					SourceEventUpdatedEvent eventUpdated = new SourceEventUpdatedEvent();
					eventUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
					eventUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
					eventUpdated.setSourceEvent(update.getSubject());
					kafkaProducerChannel.send(KFCEvent.createMessage(eventUpdated));
					
					boolean creator = eventUpdated.getSourceEvent().isCreator();						
					if (creator) {
					  MessageBuilder<SourceEventUpdatedEvent> builder = MessageBuilder.withPayload(eventUpdated).setHeader(LMAX_KEY_HEADER, eventUpdated.getSourceEvent().getId().toString());
                      lmaxInputChannelDataProcessor.send(builder.build());
					}
				}
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating event %s from connector %s.", event, integrationModuleId), te.getMostSpecificCause());
		
			if (errorChannel != null)
			{
				MessageBuilder<Event> builder = MessageBuilder.withPayload(event).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("\"Transaction error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", te);
			}
		} catch (NumberFormatException t) {
			
			logger.error(String.format("Format error in the connector Id %s.", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<Event> builder = MessageBuilder.withPayload(event).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", t);
			}
		} catch (RuntimeException rte) {
			logger.error(String.format("Error updating event %s from connector %s.", event, integrationModuleId), rte);
			
			if (errorChannel != null)
			{
				MessageBuilder<Event> builder = MessageBuilder.withPayload(event).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Format error in the integrationModuleId Id \\\"{}\\\".\", new Object[] { integrationModuleId }", rte);
			}
		}
	}

	/**
	 * Update the market from the {@link Market}.
	 *
	 * @param market
	 */
	@Transactional
	public void updateMarket(final SourceMarketsReceivedEvent market)
	{
		assert market != null;
		List<Update<SourceMarket>> updates = new ArrayList<Update<SourceMarket>>();

		String integrationModuleId = market.getIntegrationModuleId();
		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();;
			Long connectorId = Long.valueOf(integrationModuleId);
			if (market.getMarkets() != null) {
				for (Market aMarket : market.getMarkets()) {
					// Save market in DB
					updates.add(doUpdateMarket(connectorId, aMarket));
				}
				
				for (Update<SourceMarket> update : updates) {
					if (update != null && update.getType() != null && update.getType().equals(Update.Type.ADDED))
					{
						// send to kafka market creation event
						SourceMarketCreatedEvent marketCreated = new SourceMarketCreatedEvent();
						marketCreated.setIntegrationModuleId(market.getIntegrationModuleId());
						marketCreated.setIntegrationModuleName(market.getIntegrationModuleName());
						marketCreated.setSourceMarket(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(marketCreated));
					} else if (update != null && update.getType() != null && update.getType().equals(Update.Type.CHANGED))
					{
						// send to kafka market update event
						SourceMarketUpdatedEvent marketUpdated = new SourceMarketUpdatedEvent();
						marketUpdated.setIntegrationModuleId(market.getIntegrationModuleId());
						marketUpdated.setIntegrationModuleName(market.getIntegrationModuleName());
						marketUpdated.setSourceMarket(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(marketUpdated));
						boolean creator = marketUpdated.getSourceMarket().isCreator();                   
                        if (creator) {
                          MessageBuilder<SourceMarketUpdatedEvent> builder = MessageBuilder.withPayload(marketUpdated).setHeader(LMAX_KEY_HEADER, marketUpdated.getSourceMarket().getId().toString());
                          lmaxInputChannelDataProcessor.send(builder.build());
                        }
					}
				}
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating market %s from connector %s.", market, integrationModuleId), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketsReceivedEvent> builder = MessageBuilder.withPayload(market).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("\"Transaction error in the market Id \\\"{}\\\".\", new Object[] { market }", te);
			}
		} catch (NumberFormatException t)
		{
			logger.error(String.format("Format error in the market %s.", market), t);
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketsReceivedEvent> builder = MessageBuilder.withPayload(market).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Format error in the market Id \"{}\".", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating market %s from connector %s.", market, integrationModuleId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketsReceivedEvent> builder = MessageBuilder.withPayload(market).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Error in the market Id \\\"{}\\\".\", new Object[] { market }", rte);
			}
		}
	}

	/**
	 * Update the runner from the {@link Runner}.
	 *
	 * @param runner
	 */
	@Transactional
	public void updateRunner(final SourceRunnersReceivedEvent runner)
	{
		String connectorId = runner.getIntegrationModuleId();
		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();;
			List<Update<SourceRunner>> updates = doUpdateRunner(connectorId, runner.getRunners());
			if (CollectionUtils.isNotEmpty(updates))
			{
				for(Update<SourceRunner> update: updates)
				{
					if (update.getType() != null && update.getType().equals(Update.Type.ADDED))
					{
						// send to kafka runner creation event
						SourceRunnerCreatedEvent runnerCreated = new SourceRunnerCreatedEvent();
						runnerCreated.setIntegrationModuleId(runner.getIntegrationModuleId());
						runnerCreated.setIntegrationModuleName(runner.getIntegrationModuleName());
						runnerCreated.setSourceRunner(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(runnerCreated));
					} else if (update.getType() != null && update.getType().equals(Update.Type.CHANGED))
					{
						// send to kafka runner update event
						SourceRunnerUpdatedEvent runnerUpdated = new SourceRunnerUpdatedEvent();
						runnerUpdated.setIntegrationModuleId(runner.getIntegrationModuleId());
						runnerUpdated.setIntegrationModuleName(runner.getIntegrationModuleName());
						runnerUpdated.setSourceRunner(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(runnerUpdated));
						
						boolean creator = runnerUpdated.getSourceRunner().isCreator();
                        if (creator) {
                          MessageBuilder<SourceRunnerUpdatedEvent> builder = MessageBuilder.withPayload(runnerUpdated).setHeader(LMAX_KEY_HEADER, runnerUpdated.getSourceRunner().getId().toString());
                          lmaxInputChannelDataProcessor.send(builder.build());
                        }
					}
				}
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating runner %s.", runner), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnersReceivedEvent> builder = MessageBuilder.withPayload(runner).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("\"Transaction error in the runner Id \\\"{}\\\".\", new Object[] { runner }", te);
			}
		} catch (NumberFormatException t)
		{
			logger.error(String.format("Format error in the runner %s.", runner), t);
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnersReceivedEvent> builder = MessageBuilder.withPayload(runner).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Format error in the runner Id \"{}\".", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating runner %s.", runner), rte);
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnersReceivedEvent> builder = MessageBuilder.withPayload(runner).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Error in the runner Id \\\"{}\\\".\", new Object[] { runner }", rte);
			}
		}
	}

	/**
	 * Update the betting platform from the {@link BettingPlatform}.
	 * @param payload TODO
	 */
	@Transactional
	public void updateBettingPlatform(final BettingPlatformReceivedEvent payload)
	{
		String integrationModuleId = payload.getIntegrationModuleId();
		String bettingPlatformName = payload.getBettingPlatform();
		assert integrationModuleId != null;
		assert bettingPlatformName != null;

		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			Long connectorId = Long.valueOf(integrationModuleId);
			Update<BettingPlatform> update = doUpdateBettingPlatform(Long.valueOf(connectorId), bettingPlatformName);

			// send write notifications to kafka
			if (update != null)
			{
				if (update.getType().equals(Update.Type.ADDED))
				{
					// send to kafka runner creation event
					BettingPlatformCreatedEvent bpCreated = new BettingPlatformCreatedEvent();
					bpCreated.setIntegrationModuleId(payload.getIntegrationModuleId());
					bpCreated.setIntegrationModuleName(payload.getIntegrationModuleName());
					bpCreated.setBettingPlatform(update.getSubject());
					kafkaProducerChannel.send(KFCEvent.createMessage(bpCreated));
				} else if (update.getType().equals(Update.Type.CHANGED))
				{
					// send to kafka runner update event
					BettingPlatformUpdatedEvent bpUpdated = new BettingPlatformUpdatedEvent();
					bpUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
					bpUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
					bpUpdated.setBettingPlatform(update.getSubject());
					kafkaProducerChannel.send(KFCEvent.createMessage(bpUpdated));
				}
			}
		} catch (TransactionException te) {
			logger.error(String.format("Error updating betting platform %s.", bettingPlatformName), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(bettingPlatformName).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating betting platform.", te);
			}
		} catch (NumberFormatException t) {
			logger.error(String.format("Format error in the connector Id %s.", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(bettingPlatformName).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Format error in the integrationModuleId Id \"{}\".", t);
			}
		} catch (RuntimeException rte) {
			logger.error(String.format("Error updating betting platform %s.", bettingPlatformName), rte);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(bettingPlatformName).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Error updating betting platform name \\\"{}\\\".\", new Object[] { bettingPlatformName }", rte);
			}
		}
	}

	/**
	 * Update the market close status from the {@link Market}.
	 * @param payload TODO
	 */
	@Transactional
	public void updateMarketClosedStatus(final MarketsClosedStatusReceivedEvent payload)
	{
		String integrationModuleId = payload.getIntegrationModuleId();
		List<Market> markets =  payload.getMarkets();
		String sourceEventId = payload.getEventId();
		assert integrationModuleId != null;
		assert markets != null;
		assert !markets.isEmpty();

		SourceMarketUpdatedEvent marketUpdated = null;

		logger.debug(String.format("Updating market statuses for connector %s.", integrationModuleId));
		// Order the list of markets in a Map
		final ConcurrentHashMap<String, Market> idMarket = new ConcurrentHashMap<String, Market>();
		for (Market market : markets)
		{
			idMarket.put(market.getId(), market);
		}

		Update<SourceMarket> update = null;
		try
		{
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			Long connectorId = Long.valueOf(integrationModuleId);
			final ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			if (connectorDto == null) {
				logger.warn(String.format("Unable to update the status of the events from integrationModuleId %s "
						+ "There is no data for the integrationModuleId.", integrationModuleId));
			}

			List<Update<SourceMarket>> updatedSourceMarkets = new ArrayList<Update<SourceMarket>>();
			final List<SourceMarketDto> sourceMarketDtos = sourceMarketDao.getNonClosedSourceMarkets(connectorId, sourceEventId);
			for (SourceMarketDto sourceMarketDto : sourceMarketDtos)
			{
				if ((!idMarket.isEmpty()))
				{
					// We just update the sourceMarkets from the creator
					if ((!idMarket.containsKey(sourceMarketDto.getSourceId())) && (sourceMarketDto.isCreator()))
					{
						update = doCloseMarket(sourceMarketDto);
						if (update == null)
						{
							continue;
						}
						updatedSourceMarkets.add(update);
					}
				}
			}
			for (Update<SourceMarket> sourceMarketUpdated : updatedSourceMarkets)
			{
				// send to kafka market updated event
				marketUpdated = new SourceMarketUpdatedEvent();
				marketUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
				marketUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
				marketUpdated.setSourceMarket(sourceMarketUpdated.getSubject());
				kafkaProducerChannel.send(KFCEvent.createMessage(marketUpdated));
			}

		} catch (TransactionException te) {
			
			logger.error(String.format("Error updating market status of sourceEventId: %s.", sourceEventId), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(integrationModuleId + "--" + sourceEventId).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating market status from sourceEventId.", te);
			}
		} catch (NumberFormatException t) {
			
			logger.error(String.format("Format error in the integrationModuleId Id %s.", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(integrationModuleId + "--" + sourceEventId).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Format error in the integrationModuleId Id \"{}\".", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating the status close for the list of markets: %s.", markets), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Market>> builder = MessageBuilder.withPayload(markets).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("\"Error updating the list of markets \\\"{}\\\".\", new Object[] { markets }", rte);
			}
		}
	}

	/**
	 * Update prices
	 * @param payload TODO
	 */
	@Transactional
	public void updatePrices(final PricesReceivedEvent payload)
	{
		KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
		boolean liveEvent = false;
		String integrationModuleId = payload.getIntegrationModuleId(); 
		List<Price> prices = payload.getPrices();
		PriceCreatedEvent priceCreated = null;
		PriceRemovedEvent priceRemoved = null;
		PriceUpdatedEvent priceUpdated = null;

		if (prices != null && !prices.isEmpty())
		{
			// All the prices are related to the same market
			String market = prices.get(0).getMarketId();
			try
			{
				// Update prices				
				Long connectorId = Long.valueOf(integrationModuleId);
				logger.debug(String.format("retrieved  %s prices from integrationModuleId %s for market %s", 
						prices.size(), integrationModuleId, market));

				ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
				if (connectorDto == null)
				{
					logger.warn(String.format("Unable to update prices for integrationModuleId %s. There is no data for the integrationModuleId.", 
							connectorId));
					return;
				}
				SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorId, market);
				EventDto event = sourceMarketDto.getSourceEvent().getEvent();
				SourceEventIncidentDto latestIncident = null;
				if (LIVE_EVENT_STATUS.contains(event.getEventStatus())) {
					liveEvent = true;
					latestIncident = this.sourceEventIncidentDao.getlatestEventIncidentReceivedForEvent(event.getId());
				}

				// Group the prices by their runner, side and betting platform
				// use a TreeMap here so that it will be sorted by pricekey to prevent deadlock issue seen in PF-581
				final TreeMap<PriceKey, List<Price>> priceGroups = new TreeMap<PriceKey, List<Price>>(); // Price Key ->
																											// price
																											// list
				for (Price price : prices)
				{
					if (liveEvent && latestIncident != null && 
							CollectionUtils.isNotEmpty(dangerballConnectors) && CollectionUtils.isNotEmpty(dangerballBettingPlatforms)) {

						ScoreDto score = latestIncident.getScore();
						boolean scoreMismatch = true;
						if (price.getHomeScore() != null && price.getAwayScore() != null) {
							if (ScoreType.GOAL.equals(score.getType())) {
								scoreMismatch = score.getHome() != price.getHomeScore() ? true : false;
							} else if (ScoreType.GOAL.equals(score.getType())) {
								scoreMismatch = score.getAway() != price.getAwayScore() ? true : false;
							}
						}
						
						if (scoreMismatch) {
							continue;
						}

					}

					String sourceRunnerId = price.getRunnerId();
					String bettingPlatformName = price.getBettingPlatformName();
					PriceSide side = price.getSide();
					PriceKey key = new PriceKey(sourceRunnerId, bettingPlatformName, side);

					// Check if the betting platform name configured as a source of dangerball prices, if not and event 
					// is live continue to next price
					if (liveEvent && CollectionUtils.isNotEmpty(dangerballBettingPlatforms) && 
							!dangerballBettingPlatforms.contains(bettingPlatformName)) {
						continue;
					}
					
					List<Price> groupedPrices = priceGroups.get(key);
					if (groupedPrices == null)
					{
						groupedPrices = new ArrayList<Price>();
						priceGroups.put(key, groupedPrices);
					}
					groupedPrices.add(price);
				}

				// Sort each group's prices so that they are in order of their sequence
				for (PriceKey key : priceGroups.keySet()) {
					List<Price> groupedPrices = priceGroups.get(key);
					// Sort the prices
					Collections.sort(groupedPrices, key.getSide().equals(PriceSide.LAY) ? Collections.reverseOrder(PRICE_COMPARATOR)
							: PRICE_COMPARATOR);
				}

//				SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorId, market);
				
				List<Update<com.aldogrand.sbpc.model.Price>> priceUpdates = doUpdatePrices(connectorDto, sourceMarketDto.getMarket(), priceGroups);
								
				if (priceUpdates != null) {
					for (Update<com.aldogrand.sbpc.model.Price> updatedPrice : priceUpdates) {
						if (updatedPrice.getType() != null && updatedPrice.getType().equals(Update.Type.ADDED)) {
							// Send price creation event
							priceCreated = new PriceCreatedEvent();
							priceCreated.setIntegrationModuleId(payload.getIntegrationModuleId());
							priceCreated.setIntegrationModuleName(payload.getIntegrationModuleName());
							priceCreated.setPrice(updatedPrice.getSubject());
							kafkaProducerChannel.send(KFCEvent.createMessage(priceCreated));
						} else if (updatedPrice.getType() != null && updatedPrice.getType().equals(Update.Type.REMOVED)) {
							// send price removed event
							priceRemoved = new PriceRemovedEvent();
							priceRemoved.setIntegrationModuleId(payload.getIntegrationModuleId());
							priceRemoved.setIntegrationModuleName(payload.getIntegrationModuleName());
							priceRemoved.setPrice(updatedPrice.getSubject());
							kafkaProducerChannel.send(KFCEvent.createMessage(priceRemoved));
						} else if (updatedPrice.getType() != null && updatedPrice.getType().equals(Update.Type.CHANGED)) {
							// send price updated event
							priceUpdated = new PriceUpdatedEvent();
							priceUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
							priceUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
							priceUpdated.setPrice(updatedPrice.getSubject());
							kafkaProducerChannel.send(KFCEvent.createMessage(priceUpdated));
						}
					}
				}
			} catch (TransactionException te) {
				logger.error(String.format("Error updating  prices created: %s or prices removed %s ", priceCreated, priceRemoved), 
						te.getMostSpecificCause());
				if (errorChannel != null)
				{
					MessageBuilder<List<Price>> builder = MessageBuilder.withPayload(prices).setHeader(ERROR_CHANNEL, "errorChanel");
					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
				} else
				{
					throw new RuntimeException("Error updating prices.", te);
				}
			} catch (NumberFormatException t) {
				logger.error(String.format("Format error in the integrationModuleId Id %s ", integrationModuleId), t);
				if (errorChannel != null)
				{
					MessageBuilder<List<Price>> builder = MessageBuilder.withPayload(prices).setHeader(ERROR_CHANNEL, "errorChanel");
					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
				} else
				{
					throw new RuntimeException("Format error in the integrationModuleId Id \"{}\".", t);
				}
			} catch (RuntimeException rte) {
				logger.error(String.format("Error updating prices %s ", prices), rte);
				if (errorChannel != null)
				{
					MessageBuilder<List<Price>> builder = MessageBuilder.withPayload(prices).setHeader(ERROR_CHANNEL, "errorChanel");
					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
				} else
				{
					throw new RuntimeException("\"Error updating prices \\\"{}\\\".\", new Object[] { prices }", rte);
				}
			}
		}
	}

	/**
	 * Update Positions
	 * @param payload TODO
	 */
	@Transactional
	public void updatePosition(final PositionsReceivedEvent payload)
	{
		KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
		String integrationModuleId = payload.getIntegrationModuleId();
		List<Position> positions = payload.getPositions();
		Account account = payload.getAccount(); 
		assert integrationModuleId != null;
		assert positions != null;
		assert account != null;

		logger.debug(String.format("Updating position %s from integrationModuleId %s for account %s.", positions, integrationModuleId, account.getUsername()));

		try
		{
			Long connectorId = Long.valueOf(integrationModuleId);
			List<Update<com.aldogrand.sbpc.model.Position>> positionUpdates = doUpdatePosition(connectorId, positions, account);

			if (CollectionUtils.isNotEmpty(positionUpdates))
			{
				for(Update<com.aldogrand.sbpc.model.Position> positionUpdate: positionUpdates)
				{
					if (positionUpdate.getType() != null && positionUpdate.getType() != null && positionUpdate.getType().equals(Update.Type.ADDED))
					{
						// send offer creation event
						PositionCreatedEvent positionCreated = new PositionCreatedEvent();
						positionCreated.setIntegrationModuleId(payload.getIntegrationModuleId());
						positionCreated.setIntegrationModuleName(payload.getIntegrationModuleName());
						positionCreated.setPosition(positionUpdate.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(positionCreated));
					} else if (positionUpdate.getType() != null && positionUpdate.getType() != null && positionUpdate.getType().equals(Update.Type.CHANGED))
					{
						// send offer update event
						PositionUpdatedEvent positionUpd = new PositionUpdatedEvent();
						positionUpd.setIntegrationModuleId(payload.getIntegrationModuleId());
						positionUpd.setIntegrationModuleName(payload.getIntegrationModuleName());
						positionUpd.setPosition(positionUpdate.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(positionUpd));
					}
				}
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating position %s for integrationModuleId %s.", positions, integrationModuleId), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<List<Position>> builder = MessageBuilder.withPayload(positions).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating positions.", te);
			}
		} catch (NumberFormatException t)
		{
			logger.error(String.format("Format error in the integrationModuleId Id %s ", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<List<Position>> builder = MessageBuilder.withPayload(positions).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Error updating positions.", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating position %s from integrationModuleId Id %s ", positions, integrationModuleId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Position>> builder = MessageBuilder.withPayload(positions).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating positions.", rte);
			}
		}
	}

	/**
	 * Update Offers
	 * @param payload TODO
	 */
	@Transactional
	public void updateOffer(final OffersReceivedEvent payload)
	{
		KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
		String integrationModuleId = payload.getIntegrationModuleId(); 
		List<Offer> offers = payload.getOffers(); 
		Account account = payload.getAccount();
		if (CollectionUtils.isEmpty(offers))
		{
			return;
		}

		if (account == null)
		{
			return;
		}

		if (integrationModuleId == null)
		{
			return;
		}

		logger.debug(String.format("Updating offers %s from integrationModuleId %s for account %s.", 
				offers, integrationModuleId, account.getUsername()));
		try
		{
			Long connectorId = Long.valueOf(integrationModuleId);
			List<Update<com.aldogrand.sbpc.model.Offer>> updates = doUpdateOffer(connectorId, offers, account);

			// Send write notifications to kafka
			if (CollectionUtils.isNotEmpty(updates))
			{
				for(Update<com.aldogrand.sbpc.model.Offer> update: updates){
					if (update.getType() != null && update.getType().equals(Update.Type.ADDED))
					{
						// send offer creation event
						OfferCreatedEvent offerCreated = new OfferCreatedEvent();
						offerCreated.setIntegrationModuleId(payload.getIntegrationModuleId());
						offerCreated.setIntegrationModuleName(payload.getIntegrationModuleName());
						offerCreated.setOffer(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(offerCreated));
					} else if (update.getType() != null && update.getType().equals(Update.Type.CHANGED))
					{
						// send offer update event
						OfferUpdatedEvent offerUpdated = new OfferUpdatedEvent();
						offerUpdated.setIntegrationModuleId(payload.getIntegrationModuleId());
						offerUpdated.setIntegrationModuleName(payload.getIntegrationModuleName());
						offerUpdated.setOffer(update.getSubject());
						kafkaProducerChannel.send(KFCEvent.createMessage(offerUpdated));
					}
				}
			}
		} catch (TransactionException te)
			{
			logger.error(String.format("Error updating Offers %s for integrationModuleId %s.", offers, integrationModuleId), 
					te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<List<Offer>> builder = MessageBuilder.withPayload(offers).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating offers.", te);
			}
		} catch (NumberFormatException t)
		{
			logger.error(String.format("Format error for integrationModuleId %s.", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<List<Offer>> builder = MessageBuilder.withPayload(offers).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Error updating offer.", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating offers %s from integrationModuleId %s", offers, integrationModuleId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Offer>> builder = MessageBuilder.withPayload(offers).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating offer.", rte);
			}
		}
	}

	/**
	 * Update Event Incidents
	 * @param payload TODO
	 */
	@Transactional
	public void updateEventIncident(final EventIncidentReceivedEvent payload)
	{
		KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
		String integrationModuleId = payload.getIntegrationModuleId();
		EventIncident eventIncident = payload.getEventIncident();
		assert integrationModuleId != null;
		assert eventIncident != null;

		SourceEventIncidentDto sourceEventIncidentDto = null;
		try
		{
		    logger.debug(String.format("Updating Event %s from integrationModuleId %s for received Incidents ",
                eventIncident.getSourceEventId(), integrationModuleId));        
			Long connectorId = Long.valueOf(integrationModuleId);
			Update<com.aldogrand.sbpc.model.EventIncident> updatedEventIncident = null;

			// Check if the Event exists as if not no need to continue
			long sourceEventId = eventIncident.getSourceEventId();
			final SourceEventDto sourceEvent = this.sourceEventDao.getSourceEvent(connectorId, String.valueOf(sourceEventId));

			// FIXME: sourceEvent has to be mapped to save event incidents?
			if (sourceEvent == null || sourceEvent.getEvent() == null) {
				logger.warn(String.format("Not processing event incidents for source event : %s as not currently mapped", sourceEventId));
				return;
			}

			// Create incident if not existing
			if (eventIncident.getType() != null)
			{
				SourceEventIncidentDto matchedIncident =
						sourceEventIncidentDao.getIncident(
							eventIncident.getEventPhase(),
							eventIncident.getSequenceNumber(),
							eventIncident.getType(),
							eventIncident.getParticipantId(),
							DateUtils.toTime(eventIncident.getElapsedTime())
						);


				// Already mapped and sent, only send new incidents
				if (matchedIncident == null)
				{
					sourceEventIncidentDto = doCreateEventIncident(connectorId, eventIncident);
				}
			}

			// Send write notification
			if (sourceEventIncidentDto != null)
			{
				updatedEventIncident = new Update<com.aldogrand.sbpc.model.EventIncident>(ModelFactory.createEventIncident(
						eventIncident.getCurrentDangerBallStatus(), sourceEventIncidentDto), Update.Type.ADDED);

				// Send to kafka an eventIncident event creation
				EventIncidentCreatedEvent event = new EventIncidentCreatedEvent();
				event.setIntegrationModuleId(payload.getIntegrationModuleId());
				event.setIntegrationModuleName(payload.getIntegrationModuleName());
				event.setEventIncident(updatedEventIncident.getSubject());
				kafkaProducerChannel.send(KFCEvent.createMessage(event));
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating Source Event %s with incidents from integrationModuleId %s", 
					eventIncident.getSourceEventId(), integrationModuleId), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<EventIncident> builder = MessageBuilder.withPayload(eventIncident).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating incidents.", te);
			}
		} catch (NumberFormatException t) {
			logger.error(String.format("Format error in the integrationModuleId Id  %s", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<EventIncident> builder = MessageBuilder.withPayload(eventIncident).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Error updating incidents.", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating Source Event %s with incidents from integrationModuleId %s", 
					eventIncident.getSourceEventId(), integrationModuleId),	rte);
			if (errorChannel != null)
			{
				MessageBuilder<EventIncident> builder = MessageBuilder.withPayload(eventIncident).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating incidents.", rte);
			}
		}
	}

	/**
	 * Update Event Closed Status
	 * @param payload TODO
	 */
	@Transactional
	public void updateEventClosedStatus(final EventsClosedStatusReceivedEvent payload)
	{
		KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
		String integrationModuleId = payload.getIntegrationModuleId();
		List<Event> events = payload.getEvents();
		assert integrationModuleId != null;
		assert events != null;
		assert !events.isEmpty();

		SourceEventUpdatedEvent eventUpdatedEvent = null;
		SourceMarketUpdatedEvent marketUpdatedEvent = null;

		logger.debug(String.format("Updating the status of the events for the integrationModuleId %s", integrationModuleId));
		try
		{
			Long connectorId = Long.valueOf(integrationModuleId);

			// Order the list of events in a Map
			final ConcurrentHashMap<String, Event> idEvents = new ConcurrentHashMap<String, Event>();
			for (Event event : events)
			{
				idEvents.put(event.getId(), event);
			}

			List<Update<SourceEvent>> sourceEventsUpdated = doUpdateEventClosedStatus(connectorId, idEvents);

			// Update the source markets of the source event.
			// If an source event is closed the source markets must to be closed explicity
			List<Update<SourceMarket>> sourceMarketsUpdated = new ArrayList<Update<SourceMarket>>();

			for (Update<SourceEvent> updatedSourceEvent : sourceEventsUpdated)
			{
				final List<SourceMarketDto> sourceMarketDtos = sourceMarketDao.getNonClosedSourceMarkets(connectorId, updatedSourceEvent.getSubject()
						.getSourceId());

				for (SourceMarketDto sourceMarketDto : sourceMarketDtos)
				{
					Update<SourceMarket> marketUpdated = doCloseMarket(sourceMarketDto);
					if (marketUpdated != null)
					{
						sourceMarketsUpdated.add(marketUpdated);
					}
				}
			}

			for (Update<SourceEvent> updatedSourceEvent : sourceEventsUpdated)
			{
				eventUpdatedEvent = new SourceEventUpdatedEvent();
				eventUpdatedEvent.setIntegrationModuleId(payload.getIntegrationModuleId());
				eventUpdatedEvent.setIntegrationModuleName(payload.getIntegrationModuleName());
				eventUpdatedEvent.setSourceEvent(updatedSourceEvent.getSubject());
				kafkaProducerChannel.send(KFCEvent.createMessage(eventUpdatedEvent));
			}

			for (Update<SourceMarket> updatedSourceMarket : sourceMarketsUpdated)
			{
				marketUpdatedEvent = new SourceMarketUpdatedEvent();
				marketUpdatedEvent.setIntegrationModuleId(payload.getIntegrationModuleId());
				marketUpdatedEvent.setIntegrationModuleName(payload.getIntegrationModuleName());
				marketUpdatedEvent.setSourceMarket(updatedSourceMarket.getSubject());
				kafkaProducerChannel.send(KFCEvent.createMessage(marketUpdatedEvent));

			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating status of events from integrationModuleId %s", integrationModuleId), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<List<Event>> builder = MessageBuilder.withPayload(events).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating status of events.", te);
			}
		} catch (NumberFormatException t)
		{
			logger.error(String.format("Error updating status of events from integrationModuleId %s", integrationModuleId), t);
			if (errorChannel != null)
			{
				MessageBuilder<List<Event>> builder = MessageBuilder.withPayload(events).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), t)).build());
			} else
			{
				throw new RuntimeException("Error updating status of events.", t);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating status of events from integrationModuleId %s", integrationModuleId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Event>> builder = MessageBuilder.withPayload(events).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating status of events.", rte);
			}
		}
	}

	/**
	 * Update SettleBet
	 *  
	 * @param integrationModuleId
	 * @param accountId
	 * @param settledBets
	 */
	@Transactional
	public void settledBet(String integrationModuleId, Long accountId, List<SettledBet> settledBets)
	{
		assert integrationModuleId != null;
		assert settledBets != null;

		SettledBetDto settledBetDto = null;
		final ConnectorDto connectorDto = connectorDao.getConnector(Long.valueOf(integrationModuleId));
		if (connectorDto == null)
		{
			return;
		}

		try
		{
			List<SettledBetDto> settledBetsDto = new ArrayList<SettledBetDto>();
			if (settledBets != null)
			{
				for (SettledBet settledBet : settledBets)
				{
					settledBetDto = doSettledBet(connectorDto.getId(), accountId, settledBet);
					if (settledBetDto != null)
					{
						settledBetsDto.add(settledBetDto);
					}
				}

				Date now = new Date();
				if (!settledBetsDto.isEmpty())
				{
					doUpdateSettledBetsReportJobInfoStatus(true, now, connectorDto.getId(), accountId);
				} else
				{
					doUpdateSettledBetsReportJobInfoStatus(false, now, connectorDto.getId(), accountId);
				}

				for (SettledBetDto dto : settledBetsDto)
				{
					// TODO: send data to kafka bus??
				}
			}
		} catch (RuntimeException e) {
			logger.error(String.format("Error updating settledBet from integrationModuleId %s", integrationModuleId), e);
			if (errorChannel != null)
			{
				MessageBuilder<SettledBetDto> builder = MessageBuilder.withPayload(settledBetDto).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
			} else
			{
				throw new RuntimeException("Error settledBet.", e);
			}
		}
	}

	/**
	 * Update SettleBet
	 * 
	 * @param integrationModuleId
	 * @param accountId
	 * @param settledBet
	 */
	@Transactional
	public SettledBetDto doSettledBet(Long connectorId, Long accountId, SettledBet settledBet)
	{
		assert connectorId != null;
		assert accountId != null;
		assert settledBet != null;

		SettledBetDto settledBetDto = null;
		try
		{
			OfferDto offerDto = offerDao.getOffer(connectorId, settledBet.getOfferId(), settledBet.getRunnerId(), settledBet.getMarketId(),
					settledBet.getEventId());
			if (offerDto != null)
			{

				BetDto betDto = betDao.getBet(offerDto.getId(), settledBet.getBetId());
				if (betDto != null)
				{
					settledBetDto = getSettledBetDao().getSettledBet(betDto.getId());

					if (settledBetDto == null)
					{
						settledBetDto = new SettledBetDto();
						settledBetDto.setBet(betDto);
						settledBetDto.setVersion(0L);
					}

					settledBetDto.setProfitLoss(settledBet.getProfitLoss());
					settledBetDto.setSettledTime(settledBet.getSettledTime());
					settledBetDto = getSettledBetDao().saveSettledBet(settledBetDto);

				} else
				{
					logger.warn("There is no bet in the DB for the settled bet fetched " + settledBet.getBetId());
				}

			} else
			{
				logger.warn("There is no offer in the DB for the settled bet fetched with offer " + settledBet.getOfferId());
			}
		} catch (TransactionException te) {
			
			logger.error(String.format("Error updating doSettledBet from integrationModuleId %s", connectorId), 
					te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SettledBet> builder = MessageBuilder.withPayload(settledBet).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating doSettledBet.", te);
			}
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating settledBet from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<SettledBet> builder = MessageBuilder.withPayload(settledBet).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating doSettledBet.", rte);
			}
		}

		return settledBetDto;
	}

	/**
	 * Do Update SettleBet
	 * 
	 * @param executionCorrect
	 * @param lastExecution
	 * @param connectorId
	 * @param accountId
	 */
	@Transactional
	public void doUpdateSettledBetsReportJobInfoStatus(final Boolean executionCorrect, final Date lastExecution, final Long connectorId,
			final Long accountId)
	{
		try
		{
			ReportJobInfoDto reportJobInfoDto = reportJobInfoDao.getReportJobInfo(JOB_SETTLED_BETS, accountId, connectorId);
			if (executionCorrect)
			{
				reportJobInfoDto.setJobStatus(STATUS_SUCCEED);
				reportJobInfoDto.setLastUpdate(lastExecution);
			} else
			{
				reportJobInfoDto.setJobStatus(STATUS_ERROR);
			}
			reportJobInfoDao.saveReportJobInfo(reportJobInfoDto);
		} catch (RuntimeException rte) {
			logger.error(String.format("Error updating settledBet from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<Long> builder = MessageBuilder.withPayload(connectorId).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating doUpdateSettledBetsReportJobInfoStatus.", rte);
			}
		}
	}

	/**
	 * Create Incident Event
	 * 
	 * @param connectorId
	 * @param eventIncident
	 */
	@Transactional
	public SourceEventIncidentDto doCreateEventIncident(final Long connectorId, final EventIncident eventIncident)
	{
		try
		{
			long sourceEventId = eventIncident.getSourceEventId();
			SourceEventDto sourceEvent = this.sourceEventDao.getSourceEvent(connectorId, String.valueOf(sourceEventId));

			SourceEventIncidentDto sourceEventIncidentDto = new SourceEventIncidentDto(
					eventIncident.getEventPhase(),
					eventIncident.getSequenceNumber(),
					DateUtils.toTime(eventIncident.getElapsedTime()),
					eventIncident.getType(),
					eventIncident.getCurrentDangerBallStatus(),
					eventIncident.getParticipantId());			
			sourceEventIncidentDto.setSourceEvent(sourceEvent);
			
			final Score score = eventIncident.getScore();
			
			sourceEventIncidentDto.setScore(new ScoreDto(sourceEvent, score
					.getType(), score.getHome(), score.getAway()));			

			return sourceEventIncidentDao.saveEventIncident(sourceEventIncidentDto);
		} catch (RuntimeException rte) {
			logger.error(String.format("Error updating Source Event %s with incidents from integrationModuleId %s", 
					eventIncident.getSourceEventId(), connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<EventIncident> builder = MessageBuilder.withPayload(eventIncident).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error updating status of events.", rte);
			}
			return null;
		}
	}

	/**
	 * The update of the offer into the database
	 * 
	 * @param connectorId
	 * @param offer
	 * @param account
	 */
	@Transactional
	public List<Update<com.aldogrand.sbpc.model.Offer>> doUpdateOffer(final Long connectorId, final List<Offer> offers, final Account account)
	{
		try
		{
			AccountDto accountDto = accountDao.getAccount(connectorId, account.getUsername());
			if (accountDto == null)
			{
				logger.warn(String.format("Unable to update offers %s for account %s. No account %s is configured for connector %s.", 
						offers, account.getUsername(), account.getUsername(), connectorId));	
			
				return null;
			}
			
			Map<String, SourceRunnerDto> sourceRunnerMap = new HashMap<String, SourceRunnerDto>();
			List<Update<com.aldogrand.sbpc.model.Offer>> updateList = new ArrayList<Update<com.aldogrand.sbpc.model.Offer>>();
			for(Offer offer: offers){
				
				SourceRunnerDto sourceRunnerDto = null;				
				if (!sourceRunnerMap.containsKey(offer.getRunnerId())) {
					sourceRunnerDto = sourceRunnerDao.getSourceRunner(connectorId, offer.getRunnerId());
					sourceRunnerMap.put(offer.getRunnerId(), sourceRunnerDto);
				} else {
				  sourceRunnerDto = sourceRunnerMap.get(offer.getRunnerId());
				}
				
				if (sourceRunnerDto == null){
					logger.warn(String.format("Unable to update offer %s for runner %s. No runner %s is configured for connector %s.", 
							offer, offer.getRunnerId(), offer.getRunnerId(), connectorId));	
					
					return null;
				}
				RunnerDto runnerDto = sourceRunnerDto.getRunner();
				// could be an unmapped runner
				if (runnerDto == null)
				{
					return null;
				}	
	
				ConnectorDto connectorDto = sourceRunnerDto.getConnector();
				Update.Type updateType = null;
				OfferDto offerDto = offerDao.getOffer(connectorDto.getId(), offer.getId());
				Date now = new Date();

				// New offer
				if (offerDto == null)
				{
					offerDto = new OfferDto();
					offerDto.setCreationTime(now);
					offerDto.setConnector(connectorDto);
					offerDto.setRunner(runnerDto);
					offerDto.setAccount(accountDto);
					offerDto.setLastChangeTime(now);

					if (offer.getMatches() != null & !offer.getMatches().isEmpty())
					{
						BettingPlatformDto bettingPlatform = null;
						List<BetDto> bets = new ArrayList<BetDto>();
						for (OfferMatch match : offer.getMatches())
						{
						    BetDto betDto = checkOfferToAddBet(offerDto, match, bettingPlatform, null, connectorDto);
						    bets.add(betDto);
						}
						offerDto.setBets(bets);
					}
					updateType = Update.Type.ADDED;
					logger.debug(String.format("About to create offer creating offer %s  offerDto %s from connector %s.", 
							offer, offerDto, connectorId));	
				// Existing offer
				} else {
				  
				    List<BetDto> bets = new ArrayList<BetDto>();
					if (offer.getMatches() != null && !offer.getMatches().isEmpty())
					{ 
						BettingPlatformDto bettingPlatform = null;						
						
						offerDto.getBets().clear();
						
						for (OfferMatch match : offer.getMatches())
						{
							BetDto betDto = betDao.getBet(offerDto.getId(), match.getId(), false);							
							if (betDto == null) {
							  updateType = Update.Type.CHANGED;
							}							  
							bets.add(checkOfferToAddBet(offerDto, match, bettingPlatform, betDto, connectorDto));
						}
						offerDto.setBets(bets);
					}

					// ++ 2015-02-03 Paco: Will only send to Kafka if Update.Type is Changed, it means when there is a change in the offer. 
					if (ObjectUtils.compare(offerDto.getOdds(), offer.getOdds().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(offerDto.getOddsType(), offer.getOddsType())) {						
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(offerDto.getDecimalOdds(), offer.getDecimalOdds().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {						
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(offerDto.getAvailableAmount(), offer.getAvailableAmount().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(offerDto.getCurrency(), offer.getCurrency())) {						
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(offerDto.getStatus(), offer.getStatus())) {						
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(offerDto.getSide(), offer.getSide())) {
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(offerDto.getMatchedAmount(), offer.getMatchedAmount().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(offerDto.getStake(), offer.getStake().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(formatter.format(offerDto.getOfferTime()), formatter.format(offer.getOfferTime()))) {
						updateType = Update.Type.CHANGED;
					}					

					if (updateType != null) {
						offerDto.setLastChangeTime(now);
					}
				}
				offerDto.setSourceId(offer.getId());
				offerDto.setOdds(offer.getOdds());
				offerDto.setOddsType(offer.getOddsType());
				offerDto.setDecimalOdds(offer.getDecimalOdds());
				offerDto.setStake(offer.getStake());
				offerDto.setMatchedAmount(offer.getMatchedAmount());
				offerDto.setAvailableAmount(offer.getAvailableAmount());
				offerDto.setCurrency(offer.getCurrency());
				offerDto.setOfferTime(offer.getOfferTime());
				offerDto.setSide(offer.getSide());
				offerDto.setLastFetchTime(now);
				offerDto.setStatus(offer.getStatus());

				if (offer.getStatus().equals(OfferStatus.UNMATCHED) && offer.getMatchedAmount().doubleValue() > 0)
				{
					// Paper over bug in Bpapi where offers with 0 available_amount are still set to 'open' (equivalent
					// to 'UNMATCHED' in SBPC)
					Double matchedStakeDiff = Math.abs(offer.getMatchedAmount().doubleValue()) - Math.abs(offer.getStake().doubleValue());

						if (matchedStakeDiff < 0.01) {
							if (offer.getAvailableAmount().doubleValue() < 0.01) {
								offerDto.setStatus(OfferStatus.MATCHED);
								updateType = Update.Type.CHANGED;
							}
						}
					}
					else 
						if (!ObjectUtils.areEqual(offerDto.getStatus(), offer.getStatus())) {
							offerDto.setStatus(offer.getStatus());
							updateType = Update.Type.CHANGED;
						}


					if (offer.getMatches() != null) {
						// && offer.getMatches().size() > 0
						//                            LogSF.debug(logger, "offer.getMatches() \"{}\" ", new Object[] { offerDto.getBets() });

						for (OfferMatch match : offer.getMatches()) {
							//                                LogSF.debug(logger, "offerDto \"{}\" match \"{}\" ", new Object[] { offerDto, match });

							BetDto betDto = betDao.getBet(offerDto.getId(), match.getId(), true);
							if (betDto == null) // new matched bet
							{
								// LogSF.debug(logger, "offerDto \"{}\" betDto \"{}\" ", new Object[] { offerDto, betDto });

								betDto = new BetDto();
								betDto.setOffer(offerDto);

								if (match.getBettingPlatformName() != null) {
									for (BettingPlatformDto bettingPlatformDto : connectorDto.getBettingPlatforms()) {
										if (bettingPlatformDto.getName().equals(match.getBettingPlatformName())) {
											betDto.setBettingPlatform(bettingPlatformDto);
											break;
										}
									}
								}

								betDto.setSourceId(match.getId());
								betDto.setOdds(match.getOdds());
								betDto.setOddsType(match.getOddsType());
								betDto.setDecimalOdds(match.getDecimalOdds());
								betDto.setStake(match.getStake());
								betDto.setCurrency(match.getCurrency());
								betDto.setCreationTime(match.getMatchTime());
								offerDto.getBets().add(betDto);

								updateType = Update.Type.CHANGED;
							}
						}
					}

					if ((updateType != null) && (updateType.equals(Update.Type.CHANGED))) {
						offerDto.setLastChangeTime(now);
					}
					offerDto.setLastFetchTime(now);

				offerDto = offerDao.saveOffer(offerDto);
				
				if (updateType != null)
				{
					logger.debug(String.format("Updating updateType %s  offer %s offerDto %s offerDto.bets %s.", 
							updateType, offer, offerDto, offerDto.getBets()));
					updateList.add(new Update<com.aldogrand.sbpc.model.Offer>(ModelFactory.createOffer(offerDto), updateType));
				} else {
					logger.debug(String.format("No updating offer %s from integrationModuleId %s. Position has not changed.", offer, connectorId));
				}
			}
	
			return updateList;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating the offer into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Offer>> builder = MessageBuilder.withPayload(offers).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error the offer into the database.", rte);
			}
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * The update of the position into the database
	 * 
	 * @param connectorId
	 * @param position
	 * @param account
	 */
	@Transactional
	public List<Update<com.aldogrand.sbpc.model.Position>> doUpdatePosition(final Long connectorId, final List<Position> positions, final Account account)
	{
		try
		{
			AccountDto accountDto = accountDao.getAccount(connectorId, account.getUsername());
			if (accountDto == null)
			{						
				logger.warn(String.format("Unable to update positions %s for account %s. No account %s is configured for integrationModuleId %s.", 
						positions,  account.getUsername(), account.getUsername(), connectorId));
				return null;
			}
			
			List<Update<com.aldogrand.sbpc.model.Position>> updates = new ArrayList<Update<com.aldogrand.sbpc.model.Position>>();
			if(CollectionUtils.isNotEmpty(positions))
			{
				Map<String, SourceRunnerDto> sourceRunnerMap = new HashMap<String, SourceRunnerDto>();
				for(Position position: positions)
				{					
					SourceRunnerDto sourceRunnerDto = null;				
					if (!sourceRunnerMap.containsKey(position.getRunnerId())) {
						sourceRunnerDto = sourceRunnerDao.getSourceRunner(connectorId, position.getRunnerId());
						sourceRunnerMap.put(position.getRunnerId(), sourceRunnerDto);
					} else {
					  sourceRunnerDto = sourceRunnerMap.get(position.getRunnerId());
					}
					
					if (sourceRunnerDto == null)
					{
						logger.warn(String.format("Unable to update position %s for runner %s. No runner %s is configured for integrationModuleId %s.", 
								position, position.getRunnerId(), position.getRunnerId(), connectorId));
						return null;
					}

					RunnerDto runnerDto = sourceRunnerDto.getRunner();
					// could be an unmapped runner
					if (runnerDto == null)
					{
						return null;
					}

					ConnectorDto connectorDto = sourceRunnerDto.getConnector();
					BettingPlatformDto bettingPlatformDto = bettingPlatformDao.getBettingPlatform(connectorDto, position.getBettingPlatformName());
					if (bettingPlatformDto == null)
					{
						logger.warn(String.format("Not updating position %s . There is no betting platform  %s. for connector %s.", 
								position, position.getBettingPlatformName(), connectorDto.getName()));	
						return null;
					}

					Update.Type updateType = null;
					PositionDto positionDto = positionDao.getPosition(runnerDto.getId(), bettingPlatformDto.getId(), accountDto.getId());
					Date now = new Date();

					// New position
					if (positionDto == null)
					{
						positionDto = new PositionDto();
						positionDto.setCreationTime(now);
						positionDto.setRunner(runnerDto);
						positionDto.setAccount(accountDto);
						positionDto.setBettingPlatform(bettingPlatformDto);
						positionDto.setLastChangeTime(now);
						updateType = Update.Type.ADDED;

						// Existing position
					} else
					{
						// ++ 2015-02-03 Paco: Will only send to Kafka if Update.Type is Changed, it means when there is a change in the position. 
						if (ObjectUtils.compare(positionDto.getValue(), position.getValue().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
							updateType = Update.Type.CHANGED;
						}
						if (!ObjectUtils.areEqual(positionDto.getCurrency(), position.getCurrency())) {
							updateType = Update.Type.CHANGED;
						}
						// -- Paco.
						
						if (updateType != null) {
		                    positionDto.setLastChangeTime(now);
						}
					}

					positionDto.setCurrency(position.getCurrency());
					positionDto.setValue(position.getValue());
					positionDto.setLastFetchTime(now);
					positionDto = positionDao.savePosition(positionDto);

					if (updateType != null) {
						updates.add(new Update<com.aldogrand.sbpc.model.Position>(ModelFactory.createPosition(positionDto), updateType));
					} else {
						logger.debug(String.format("No updating position %s from integrationModuleId %s. Position has not changed.", position, connectorId));
					}
				}

			}

			return updates;

		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating position into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Position>> builder = MessageBuilder.withPayload(positions).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error the position into the database.", rte);
			}
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * Currently this method is deleting and creating prices, without update.
	 * 
	 * Prices are for runners and connectorId of the market parameter
	 * 
	 * @param connectorId
	 *            Provider id
	 * @param marketId
	 *            kfc market id
	 * @param priceGroups
	 *            Prices grouped
	 * @return A list of price model
	 */
	@Transactional
	public List<Update<com.aldogrand.sbpc.model.Price>> doUpdatePrices(final ConnectorDto connectorDto, final MarketDto marketDto,
			final TreeMap<PriceKey, List<Price>> priceGroups) {
		try {
			List<Update<com.aldogrand.sbpc.model.Price>> updates = new ArrayList<Update<com.aldogrand.sbpc.model.Price>>();

			// Gather a list of all preexisting prices on all runners of the market for the integrationModuleId
			// Also create a map of runnerDtos to which all changes are made			
			Map<Long, List<PriceDto>> preexistingPrices = new HashMap<Long, List<PriceDto>>();
			Map<Long, RunnerDto> mapOfRunnerDtos = new HashMap<Long, RunnerDto>();

			for (RunnerDto runnerDto : marketDto.getRunners()) {
				List<PriceDto> pricesForRunnerAndConnector = new ArrayList<PriceDto>();
				pricesForRunnerAndConnector = priceDao.getRunnerPrices(connectorDto.getId(), runnerDto.getId(), null, null);
				preexistingPrices.put(runnerDto.getId(), pricesForRunnerAndConnector);
				mapOfRunnerDtos.put(runnerDto.getId(), runnerDto);
			}
			
			/*
			 * For each source runner - runner prices are created Map to convert sourceRunner ids to runner ids as
			 * integrationModuleId prices refer to source runner ids
			 */
			Map<String, SourceRunnerDto> sourceRunnerMap = new HashMap<String, SourceRunnerDto>();
			Map<String, Long> sourceRunnerIdMap = new HashMap<String, Long>(); // source runner id -> runner id
			for (PriceKey key : priceGroups.keySet()) {
				List<Price> groupedPrices = priceGroups.get(key);
				// Get the runner id
				Long runnerId = sourceRunnerIdMap.get(key.getSourceRunnerId());
				if (runnerId == null) {					
					SourceRunnerDto sourceRunnerDto = null;				
					if (!sourceRunnerMap.containsKey(key.getSourceRunnerId())) {
						sourceRunnerDto = sourceRunnerDao.getSourceRunner(connectorDto.getId(), key.getSourceRunnerId());
						sourceRunnerMap.put(key.getSourceRunnerId(), sourceRunnerDto);
					} else {
					  sourceRunnerDto = sourceRunnerMap.get(key.getSourceRunnerId());
					}
					if (sourceRunnerDto != null) {
						RunnerDto runnerDto = sourceRunnerDto.getRunner();
						if (runnerDto != null) {
							runnerId = runnerDto.getId();
							sourceRunnerIdMap.put(key.getSourceRunnerId(), runnerId);
						}
					}
				}

				// Update the prices for the runner-side-betting platform group
				if (runnerId != null && groupedPrices != null) {
					// The preexistingPrice list is cleared inline of all updated prices
					// Then, below, all untouched prices are removed as they must no longer be being returned in the
					// integrationModuleId response
					updates.addAll(createRunnerPrices(connectorDto, mapOfRunnerDtos.get(runnerId), key, groupedPrices, preexistingPrices.get(runnerId)));

		            // For each runner of the market, remove old prices in database
		            for (RunnerDto runnerDto : mapOfRunnerDtos.values())
		            {
		                List<PriceDto> pricesToRemove = new ArrayList<PriceDto>();
		                // delete all prices by runner and connectorId
		                if (preexistingPrices.get(runnerDto.getId()).size() > 0)
		                {
		                    // pricesToRemove.addAll(preexistingPrices.get(runnerDto.getId()));
		                    for (PriceDto priceToRemove : preexistingPrices.get(runnerDto.getId()))
		                    {
		                    	// runnerDto.getPrices().remove(priceToRemove);
		                        updates.add(new Update<com.aldogrand.sbpc.model.Price>(ModelFactory.createPrice(priceToRemove), Update.Type.REMOVED));
		                        pricesToRemove.add(priceToRemove);
		                    }
		                }
		                priceDao.deletePrices(pricesToRemove);
		            }
				} else {
					logger.warn(String.format("Unable to create prices from connector %s for runner %s. The runner is not mapped to the integrationModuleId.", 
							connectorDto.getId(), key.sourceRunnerId));
				}
			}
			return updates;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating the deleting and creating prices into the database from integrationModuleId %s", connectorDto.getId()), rte);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(marketDto.getId() + "--" + connectorDto.getId()).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error the deleting and creating prices into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Create the {@link PriceDto}s for the {@link RunnerDto} with the given {@link Price}s from the
	 * {@link ConnectorDto} with the connectorId and the {@link BettingPlatformDto} with the given name.
	 * 
	 * 
	 * @param connectorId
	 *            Identity of integrationModuleId/provider
	 * @param runnerDto
	 *            Identity of runner
	 * @param key
	 *            Inner model of prices
	 * @param prices
	 *            List of prices *
	 * @return List of prices created
	 */
	@Transactional
	public List<Update<com.aldogrand.sbpc.model.Price>> createRunnerPrices(final ConnectorDto connectorDto, final RunnerDto runnerDto,
			final PriceKey key, final List<Price> prices, final List<PriceDto> preexistingPrices)
	{
		try {
			logger.debug(String.format("Updating %s prices for runner Id %s from integrationModuleId %s and betting platform %s.", 
					key.getSide(), runnerDto.getId(), connectorDto.getId(), key.getBettingPlatformName()));
			
			BettingPlatformDto bettingPlatformDto = bettingPlatformDao.getBettingPlatform(connectorDto, key.getBettingPlatformName());
			if (bettingPlatformDto == null) {
				logger.warn(String.format("Unable to update prices for runner Id %s from integrationModuleId %s. There is no "
						+ "no betting platform named %s configured for the integrationModuleId", 
						runnerDto.getId(), connectorDto.getId(), key.getBettingPlatformName()));
				return Collections.emptyList();
			}

			Update.Type updateType = null;
			Date now = new Date();
			List<Update<com.aldogrand.sbpc.model.Price>> updates = new ArrayList<Update<com.aldogrand.sbpc.model.Price>>();
			int sequence = 1;
			for (Price price : prices) {
				PriceDto priceDto = null;
				// get the actual priceDto
				for (PriceDto tempPriceDto : preexistingPrices) {
					if ((tempPriceDto.getBettingPlatform().getId().equals(bettingPlatformDto.getId())) && (tempPriceDto.getSide().equals(key.getSide())) 
							&& (tempPriceDto.getSequence().equals(sequence))) {
						priceDto = tempPriceDto;
						preexistingPrices.remove(tempPriceDto); // delete found price from list
						break;
					}
				}
				
				// New price
				if (priceDto == null) {
					priceDto = new PriceDto();
					priceDto.setBettingPlatform(bettingPlatformDto);
					priceDto.setRunner(runnerDto);
					priceDto.setSide(key.getSide());
					priceDto.setSequence(sequence);
					priceDto.setOdds(price.getOdds());
					priceDto.setOddsType(price.getOddsType());
					priceDto.setDecimalOdds(price.getDecimalOdds());
					priceDto.setAvailableAmount(price.getAvailableAmount());
					priceDto.setCurrency(price.getCurrency());
					priceDto.setCreationTime(now);
					priceDto.setLastChangeTime(now);
					priceDto.setLastFetchTime(now);
					priceDao.savePrice(priceDto);
					runnerDto.getPrices().add(priceDto);
					
					updateType = Update.Type.ADDED;
				// Update existing price
				} else {					
					if (ObjectUtils.compare(priceDto.getOdds(), price.getOdds().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(priceDto.getOddsType(), price.getOddsType())) {
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(priceDto.getDecimalOdds(), price.getDecimalOdds().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (ObjectUtils.compare(priceDto.getAvailableAmount(), price.getAvailableAmount().setScale(scale,BigDecimal.ROUND_HALF_UP)) != 0) {
						updateType = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(priceDto.getCurrency(), price.getCurrency())) {
						updateType = Update.Type.CHANGED;
					}

					if (updateType != null) {
						priceDto.setLastChangeTime(now);
					}

                    priceDto.setBettingPlatform(bettingPlatformDto);
                    priceDto.setRunner(runnerDto);
                    priceDto.setSide(key.getSide());
                    priceDto.setSequence(sequence);
                    priceDto.setOdds(price.getOdds());
                    priceDto.setOddsType(price.getOddsType());
                    priceDto.setDecimalOdds(price.getDecimalOdds());
                    priceDto.setAvailableAmount(price.getAvailableAmount());
                    priceDto.setCurrency(price.getCurrency());
					priceDto.setLastFetchTime(now);
					
					priceDao.savePrice(priceDto);
				}
				
				sequence++;
				if (updateType != null) {
					updates.add(new Update<com.aldogrand.sbpc.model.Price>(ModelFactory.createPrice(priceDto), updateType));
				}
			}

			return updates;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error updating creating prices for the runners into the database from integrationModuleId %s", connectorDto.getId()), rte);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(runnerDto.getName()).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error the creating prices for the runners into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Save the event and return the result
	 * 
	 * @param connectorId
	 *            Identity of the integrationModuleId
	 * @param event
	 *            Event from the provider
	 * @return Update<SourceEvent> if doesn't save return null
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Update<SourceEvent> doUpdateEvent(Long connectorId, Event event)
	{
		try
		{
			Update.Type type = null;
			ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			if (connectorDto == null)
			{
				return null;
			}

			Date now = new Date();
			SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), event.getId());

			// New Event
			if (sourceEventDto == null)
			{
				sourceEventDto = new SourceEventDto();
				sourceEventDto.setConnector(connectorDto);
				sourceEventDto.setCreationTime(now);
				sourceEventDto.setLastChangeTime(now);
				sourceEventDto.setCreator(false);
				type = Update.Type.ADDED;
				// Existing event
			} else
			{
				// ++ 2015-02-03 Paco: Will only send to Kafka if Update.Type is Changed, it means when there is a change in the SourceEvent. 
				if (!ObjectUtils.areEqual(sourceEventDto.getSourceName(), event.getName())) {
					type = Update.Type.CHANGED;
				}
				if (!ObjectUtils.areEqual(sourceEventDto.getStatus(), event.getStatus())) {
					type = Update.Type.CHANGED;
				}
				if(!ObjectUtils.areEqual(formatter.format(sourceEventDto.getStartTime()), formatter.format(event.getStartTime()))){
//					if (Math.abs(sourceEventDto.getStartTime().getTime() - event.getStartTime().getTime()) > 1000) { 	<<<<	bad date comparison
					type = Update.Type.CHANGED;
				}
				// -- Paco.
				
				if (type != null) {
		            sourceEventDto.setLastChangeTime(now);
				}
			}

			sourceEventDto.setSourceId(event.getId());
			sourceEventDto.setSourceName(event.getName());
			sourceEventDto.setStartTime(event.getStartTime());
			sourceEventDto.setStatus(event.getStatus());
			sourceEventDto.setLastFetchTime(now);
			for (MetaTag tag : event.getMetaTags())
			{				
				MetaTagDto tagDto = metaTagDao.getMetaTag(tag.getName(), tag.getType());
				if (tagDto == null)
				{					
					tagDto = new MetaTagDto();
					tagDto.setName(tag.getName());
					tagDto.setType(tag.getType());
					tagDto = metaTagDao.saveMetaTag(tagDto);				
				}
				sourceEventDto.getMetaTags().add(tagDto);
			}
			sourceEventDto = sourceEventDao.saveSourceEvent(sourceEventDto);
			
			return new Update<SourceEvent>(ModelFactory.createSourceEvent(sourceEventDto), type);
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error saving the event into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<Event> builder = MessageBuilder.withPayload(event).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error the saving the event into the database.", rte);
			}
			return null;
		}
	}
	

	/**
	 * Save the market and return the result
	 * 
	 * @param connectorId
	 *            Identity of the integrationModuleId
	 * @param market
	 *            Market from the provider
	 * @return Update<SourceMarket> if doesn't save return null
	 */
	@Transactional
	public Update<SourceMarket> doUpdateMarket(final Long connectorId, final Market market)
	{
		try
		{
			Update.Type type = null;
			Update<SourceMarket> update = null;

			ConnectorDto connectorDto = connectorDao.getConnector(Long.valueOf(connectorId));
			if (connectorDto == null) {
				logger.warn(String.format("Unable to update market %s from integrationModuleId %s. There is no data for the integrationModuleId.", 
						market, connectorId));
				return update;
			}

			Date now = new Date();
			SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), market.getEventId());
			SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), market.getId());

			if (sourceMarketDto == null)
			{
				sourceMarketDto = new SourceMarketDto();
				sourceMarketDto.setConnector(connectorDto);
				sourceMarketDto.setCreationTime(now);
				sourceMarketDto.setLastChangeTime(now);
				sourceMarketDto.setCreator(false);
				type = Update.Type.ADDED;
			} else
			{
				// ++ 2015-02-03 Paco: Will only send to Kafka if Update.Type is Changed, it means when there is a change in the SourceMarket.
				if (!ObjectUtils.areEqual(sourceMarketDto.getSourceName(), market.getName())) {
					type = Update.Type.CHANGED;
				}
				if (!ObjectUtils.areEqual(sourceMarketDto.getType(), market.getType())) {
					type = Update.Type.CHANGED;
				}
				if (!ObjectUtils.areEqual(sourceMarketDto.getPeriod(), market.getPeriod())) {
					type = Update.Type.CHANGED;
				}
				if (sourceMarketDto.getHandicap() != market.getHandicap()) {
					type = Update.Type.CHANGED;
				}
				if (!ObjectUtils.areEqual(sourceMarketDto.getMarketStatus(), market.getMarketStatus())) {
					type = Update.Type.CHANGED;
				}
				// -- Paco.
				
				if (type != null) {
				  sourceMarketDto.setLastChangeTime(now);
                }
			}

			sourceMarketDto.setSourceEvent(sourceEventDto);
			sourceMarketDto.setSourceId(market.getId());
			sourceMarketDto.setSourceName(market.getName());
			sourceMarketDto.setType(market.getType());
			sourceMarketDto.setHandicap(market.getHandicap());
			sourceMarketDto.setPeriod(market.getPeriod());
			sourceMarketDto.setMarketStatus(market.getMarketStatus());
			sourceMarketDto.setLastFetchTime(now);
			sourceMarketDto = sourceMarketDao.saveSourceMarket(sourceMarketDto);

			return new Update<SourceMarket>(ModelFactory.createSourceMarket(sourceMarketDto), type);
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error saving the market into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<Market> builder = MessageBuilder.withPayload(market).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error saving the market into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Save the runner and return the result
	 * 
	 * @param connectorId
	 *            Identity of the integrationModuleId
	 * @param runner
	 *            Runner from the provider
	 * @return Update<SourceRunner> if doesn't save return null
	 */
	@Transactional
	public List<Update<SourceRunner>> doUpdateRunner(String connectorId, List<Runner> runners)
	{
		try
		{

			ConnectorDto connectorDto = connectorDao.getConnector(Long.valueOf(connectorId));
			if (connectorDto == null)
			{
				logger.warn(String.format("Unable to update runners %s from integrationModuleId %s. There is no data for the integrationModuleId.", 
						runners, connectorId));
				return Collections.EMPTY_LIST;
			}
			
			List<Update<SourceRunner>> updates = new ArrayList<Update<SourceRunner>>();
			for(Runner runner: runners)
			{
				Update.Type type = null;
				Update<SourceRunner> update = null;
				Date now = new Date();
				SourceRunnerDto sourceRunnerDto = sourceRunnerDao.getSourceRunner(connectorDto.getId(), runner.getId());
				SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), runner.getMarketId());

				if ((sourceMarketDto == null) || (sourceMarketDto.getMarket() == null && !connectorDto.isPushSourceData()))
				{
					logger.warn(String.format("No updating runner %s from integrationModuleId %s. There is no data for the market Id %s.", runner, connectorId, runner.getMarketId()));
					return Collections.EMPTY_LIST;
				}

				if (sourceRunnerDto == null)
				{
					type = Update.Type.ADDED;
					sourceRunnerDto = new SourceRunnerDto();
					sourceRunnerDto.setConnector(connectorDto);
					sourceRunnerDto.setSourceMarket(sourceMarketDto);
					sourceRunnerDto.setCreator(false);
					sourceRunnerDto.setCreationTime(now);
					sourceRunnerDto.setLastChangeTime(now);
				} else
				{
					// ++ 2015-02-03 Paco: Will only send to Kafka if Update.Type is Changed, it means when there is a change in the SourceRunner.
					if (!ObjectUtils.areEqual(sourceRunnerDto.getSourceName(), runner.getName())) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getType(), runner.getType())) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getSide(), runner.getSide())) {
						type = Update.Type.CHANGED;
					}
					if (sourceRunnerDto.getRotationNumber() != runner.getRotationNumber()) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getHandicap(), runner.getHandicap())) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getSequence(), runner.getSequence())) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getRunnerStatus(), runner.getRunnerStatus())) {
						type = Update.Type.CHANGED;
					}
					if (!ObjectUtils.areEqual(sourceRunnerDto.getResultStatus(), runner.getResultStatus())) {
						type = Update.Type.CHANGED;
					}
					// -- Paco.
					
					if (type != null) {                    
					  sourceRunnerDto.setLastChangeTime(now);					  
					}
				}

				sourceRunnerDto.setSourceId(runner.getId());
				sourceRunnerDto.setSourceName(runner.getName());
				sourceRunnerDto.setType(runner.getType());
				sourceRunnerDto.setSide(runner.getSide());
				sourceRunnerDto.setHandicap(runner.getHandicap());
				sourceRunnerDto.setRotationNumber(runner.getRotationNumber());
				sourceRunnerDto.setSequence(runner.getSequence());
				sourceRunnerDto.setRunnerStatus(runner.getRunnerStatus());
				sourceRunnerDto.setResultStatus(runner.getResultStatus());				
				sourceRunnerDto.setLastFetchTime(now);
				sourceRunnerDto = sourceRunnerDao.saveSourceRunner(sourceRunnerDto);

				if (type != null) {
					updates.add(new Update<SourceRunner>(ModelFactory.createSourceRunner(sourceRunnerDto), type));					
				} else {
					logger.debug(String.format("No updating runner %s from integrationModuleId %s. Runner has not changed.", runner, connectorId));
				}
			}
			return updates;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error saving the runner into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<List<Runner>> builder = MessageBuilder.withPayload(runners).setHeader(ERROR_CHANNEL, "errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error saving the runner into the database.", rte);
			}
			return Collections.EMPTY_LIST;
		}
	}

	@Transactional
	public ConnectorDto doUpdateConnector(ConnectorDto connectorDto)
	{
		return connectorDao.saveConnector(connectorDto);
	}

	/**
	 * Save the Betting Platform and return the result
	 * 
	 * @param connectorId
	 *            Identity of the integrationModuleId
	 * @param bettingPlatformName
	 *            BettingPlatform from the provider
	 */
	@Transactional
	public Update<BettingPlatform> doUpdateBettingPlatform(final Long connectorId, final String bettingPlatformName)
	{
		try
		{
			ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			if (connectorDto == null)
			{
				logger.warn(String.format("Error updating betting platform %s from integrationModuleId %s. There is no data for the integrationModuleId", 
						bettingPlatformName, connectorId));
				return null;
			}

			Update.Type updateType = null;
			Date now = new Date();
			BettingPlatformDto bettingPlatformDto = bettingPlatformDao.getBettingPlatform(connectorDto, bettingPlatformName);

			// New betting platform
			if (bettingPlatformDto == null)
			{
				bettingPlatformDto = new BettingPlatformDto();
				bettingPlatformDto.setName(bettingPlatformName);
				bettingPlatformDto.setConnector(connectorDto);
				bettingPlatformDto.setCreationTime(now);
				updateType = Update.Type.ADDED;
				// Existing betting platform
			} else
			{
				updateType = Update.Type.CHANGED;
			}

			bettingPlatformDto.setLastChangeTime(now);
			bettingPlatformDto.setLastFetchTime(now);
			bettingPlatformDto = bettingPlatformDao.saveBettingPlatform(bettingPlatformDto);
			return (updateType != null ? new Update<BettingPlatform>(ModelFactory.createBettingPlatform(bettingPlatformDto), updateType) : null);
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error saving the betting platform into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<String> builder = MessageBuilder.withPayload(bettingPlatformName).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error saving the betting platform into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Save the Event Closed Update and return the result
	 * 
	 * @param connectorId
	 *            Identity of the integrationModuleId
	 * @param idEvents
	 *            BettingPlatform from the provider
	 */
	@Transactional
	public List<Update<SourceEvent>> doUpdateEventClosedStatus(final Long connectorId, final ConcurrentHashMap<String, Event> idEvents)
	{
		try
		{
			List<Update<SourceEvent>> eventsToReturn = new ArrayList<Update<SourceEvent>>();

			final ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			if (connectorDto == null)
			{
				logger.warn(String.format("Error updating status of the events from integrationModuleId %s. There is no data for the integrationModuleId", connectorId));
				return eventsToReturn;
			}

			// Get all the non closed sourceEvents for this integrationModuleId
			final List<SourceEventDto> sourceEventDtos = sourceEventDao.getNonClosedSourceEvents(connectorDto.getId());

			if ((sourceEventDtos != null) && (idEvents != null) && (!idEvents.isEmpty()))
			{
				for (SourceEventDto sourceEventDto : sourceEventDtos)
				{
					// If the source is in Data Base but we did not received it through the API, then it is closed
					if (!idEvents.containsKey(sourceEventDto.getSourceId()))
					{
						// Only if it is the creator it will be updated
						if (sourceEventDto.isCreator())
						{
							sourceEventDto.setStatus(EventStatus.CLOSED);

							// Update the eventDto
							sourceEventDao.saveSourceEvent(sourceEventDto);
							eventsToReturn.add(new Update<SourceEvent>(ModelFactory.createSourceEvent(sourceEventDto), Update.Type.CHANGED));
						}
					}
				}
			}
			return eventsToReturn;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error saving the Event close status into the database from integrationModuleId %s", connectorId), rte);
			if (errorChannel != null)
			{
				MessageBuilder<Long> builder = MessageBuilder.withPayload(connectorId).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error saving the Event close status into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Save the close market and return the result
	 * 
	 * @param sourceMarketDto
	 *            Identity of the integrationModuleId
	 */
	@Transactional
	public Update<SourceMarket> doCloseMarket(final SourceMarketDto sourceMarketDto)
	{
		try
		{
			Date now = new Date();
			Update<SourceMarket> update = null;

			// go across the different sourceMarkets
			if (sourceMarketDto != null)
			{
				// Update the status
				sourceMarketDto.setMarketStatus(MarketStatus.CLOSED);
				sourceMarketDto.setLastChangeTime(now);

				// Updating the source entity in the DB
				sourceMarketDao.saveSourceMarket(sourceMarketDto);
				update = new Update<SourceMarket>(ModelFactory.createSourceMarket(sourceMarketDto), Update.Type.CHANGED);
			}

			return update;
		} catch (RuntimeException rte)
		{
			logger.error(String.format("Error closing market %s into the database", sourceMarketDto), rte);		
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketDto> builder = MessageBuilder.withPayload(sourceMarketDto).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error saving the Event close status into the database.", rte);
			}
			return null;
		}
	}

	/**
	 * Check offer to identify the betting platform and create a bet in our system. If already exists a bet in the
	 * system then do nothing.
	 * 
	 * @param offerDto
	 *            Offer
	 * @param match
	 *            OfferMatch
	 * @param bettingPlatform
	 *            Betting platform
	 * @param betDto
	 *            Bet
	 * @param connectorDto
	 *            Connector or provider model
	 * @return BettingPlatform model
	 */
	@Transactional
	public BetDto checkOfferToAddBet(OfferDto offerDto, OfferMatch match, BettingPlatformDto bettingPlatform, BetDto betDto,
			ConnectorDto connectorDto)
	{
	    BetDto bet = new BetDto();
		try {
			if (betDto == null) {
				if (bettingPlatform != null && bettingPlatform.getName().equalsIgnoreCase(match.getBettingPlatformName()))
				{
				  bet = createBetDto(offerDto, match, bettingPlatform);

				} else	{
					BettingPlatformDto bettingPlatformDto = bettingPlatformDao.getBettingPlatform(connectorDto, match.getBettingPlatformName());
					bet =createBetDto(offerDto, match, bettingPlatformDto);
				}
			}
			return bet;
		} catch (RuntimeException rte) {
			if (errorChannel != null) {
				MessageBuilder<OfferDto> builder = MessageBuilder.withPayload(offerDto).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else {
				throw new RuntimeException("Error updating check of offer to identify the betting platform.", rte);
			}
			return null;
		}
	}

	/**
	 * Create a Bet DTO model
	 * 
	 * @param offerDto
	 *            Offer
	 * @param match
	 *            OfferMatch
	 * @param bettingPlatformDto
	 *            Betting platform
	 * @return BetDto data model
	 */
	@Transactional
	private BetDto createBetDto(OfferDto offerDto, OfferMatch match, BettingPlatformDto bettingPlatformDto)
	{
		try
		{
			BetDto betDto = new BetDto();
			betDto.setOffer(offerDto);
			betDto.setBettingPlatform(bettingPlatformDto);
			betDto.setSourceId(match.getId());
			betDto.setOdds(match.getOdds());
			betDto.setOddsType(match.getOddsType());
			betDto.setDecimalOdds(match.getDecimalOdds());
			betDto.setStake(match.getStake());
			betDto.setCurrency(match.getCurrency());
			betDto.setCreationTime(match.getMatchTime());

			return betDto;
		} catch (RuntimeException rte)
		{
			if (errorChannel != null)
			{
				MessageBuilder<OfferDto> builder = MessageBuilder.withPayload(offerDto).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			} else
			{
				throw new RuntimeException("Error creating Bet Dto.", rte);
			}
			return null;
		}
	}

	// ////// Setters & Getters /////////
	public ConnectorDao getConnectorDao()
	{
		return connectorDao;
	}

	@Required
	public void setConnectorDao(ConnectorDao connectorDao)
	{
		this.connectorDao = connectorDao;
	}

	public AccountDao getAccountDao()
	{
		return accountDao;
	}

	@Required
	public void setAccountDao(AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}

	public BettingPlatformDao getBettingPlatformDao()
	{
		return bettingPlatformDao;
	}

	@Required
	public void setBettingPlatformDao(BettingPlatformDao bettingPlatformDao)
	{
		this.bettingPlatformDao = bettingPlatformDao;
	}

	public EventDao getEventDao()
	{
		return eventDao;
	}

	@Required
	public void setEventDao(EventDao eventDao)
	{
		this.eventDao = eventDao;
	}

	public SourceEventDao getSourceEventDao()
	{
		return sourceEventDao;
	}

	@Required
	public void setSourceEventDao(SourceEventDao sourceEventDao)
	{
		this.sourceEventDao = sourceEventDao;
	}

	public MetaTagDao getMetaTagDao()
	{
		return metaTagDao;
	}

	@Required
	public void setMetaTagDao(MetaTagDao metaTagDao)
	{
		this.metaTagDao = metaTagDao;
	}

	public MarketDao getMarketDao()
	{
		return marketDao;
	}

	@Required
	public void setMarketDao(MarketDao marketDao)
	{
		this.marketDao = marketDao;
	}

	public SourceMarketDao getSourceMarketDao()
	{
		return sourceMarketDao;
	}

	@Required
	public void setSourceMarketDao(SourceMarketDao sourceMarketDao)
	{
		this.sourceMarketDao = sourceMarketDao;
	}

	public RunnerDao getRunnerDao()
	{
		return runnerDao;
	}

	@Required
	public void setRunnerDao(RunnerDao runnerDao)
	{
		this.runnerDao = runnerDao;
	}

	public SourceRunnerDao getSourceRunnerDao()
	{
		return sourceRunnerDao;
	}

	@Required
	public void setSourceRunnerDao(SourceRunnerDao sourceRunnerDao)
	{
		this.sourceRunnerDao = sourceRunnerDao;
	}

	public PriceDao getPriceDao()
	{
		return priceDao;
	}

	@Required
	public void setPriceDao(PriceDao priceDao)
	{
		this.priceDao = priceDao;
	}

	public PositionDao getPositionDao()
	{
		return positionDao;
	}

	@Required
	public void setPositionDao(PositionDao positionDao)
	{
		this.positionDao = positionDao;
	}

	public OfferDao getOfferDao()
	{
		return offerDao;
	}

	@Required
	public void setOfferDao(OfferDao offerDao)
	{
		this.offerDao = offerDao;
	}

	public BetDao getBetDao()
	{
		return betDao;
	}

	@Required
	public void setBetDao(BetDao betDao)
	{
		this.betDao = betDao;
	}

	public SettledBetDao getSettledBetDao()
	{
		return settledBetDao;
	}

	@Required
	public void setSettledBetDao(SettledBetDao settledBetDao)
	{
		this.settledBetDao = settledBetDao;
	}

	public ReportJobInfoDao getReportJobInfoDao()
	{
		return reportJobInfoDao;
	}

	@Required
	public void setReportJobInfoDao(ReportJobInfoDao reportJobInfoDao)
	{
		this.reportJobInfoDao = reportJobInfoDao;
	}

	public KFCProducer getKafkaPublisher()
	{
		return kafkaPublisher;
	}
	@Required
	public void setKafkaPublisher(KFCProducer kafkaPublisher)
	{
		this.kafkaPublisher = kafkaPublisher;
	}
	/**
	 * @return the dangerballConnectors
	 */
	public String getDangerballConnectors()
	{
		return dangerballConnectors.toString();
	}

	/**
	 * 
	 * @param dangerballConnectors
	 */
	public void setDangerballConnectors(String dangerballConnectorsString) {
		if (StringUtils.isNoneEmpty(dangerballConnectorsString)) {
			String[] dangerballConnectorsArray = dangerballConnectorsString.split(",");
			this.dangerballConnectors = new ArrayList<Long>();
			for (String dangerballConnector : dangerballConnectorsArray) {
				this.dangerballConnectors.add(Long.parseLong(dangerballConnector));
			}
		}
	}
	

	/**
	 * @return the dangerballBettingPlatforms
	 */
	public String getDangerballBettingPlatform() {
		return dangerballBettingPlatforms.toString();
	}
	

	/**
	 * @return the dangerballBettingPlatforms
	 */
	public List<String> getDangerballBettingPlatforms() {
		return dangerballBettingPlatforms;
	}

	/**
	 * @param dangerballBettingPlatforms the dangerballBettingPlatforms to set
	 */
	public void setDangerballBettingPlatforms(
			List<String> dangerballBettingPlatforms) {
		this.dangerballBettingPlatforms = dangerballBettingPlatforms;
	}

	/**
	 * 
	 * @param dangerballBettingPlatforms
	 */
	public void setDangerballBettingPlatform(String dangerballBettingPlatforms) {
		if (StringUtils.isNoneEmpty(dangerballBettingPlatforms)) {
			String[] dangerballBettingPlatformsArray = dangerballBettingPlatforms.split(",");
			this.dangerballBettingPlatforms = new ArrayList<String>();
			for (String dangerballBettingPlatform : dangerballBettingPlatformsArray) {
				this.dangerballBettingPlatforms.add(dangerballBettingPlatform);
			}
		}
	}

	/**
	 * <p>
	 * <b>Title</b> PriceKey
	 * </p>
	 * <p>
	 * <b>Description</b> A key used to group {@link PriceDto}s during update.
	 * </p>
	 * <p>
	 * <b>Company</b> AldoGrand Consultancy Ltd
	 * </p>
	 * <p>
	 * <b>Copyright</b> Copyright (c) 2013
	 * </p>
	 * 
	 * @author Aldo Grand
	 * @version 1.0
	 */
	private static class PriceKey implements Serializable, Comparable
	{
		private static final long	serialVersionUID	= -2249929637635505952L;

		private final String		sourceRunnerId;

		private final String		bettingPlatformName;

		private final PriceSide		side;

		/**
		 * @param sourceRunnerId
		 * @param bettingPlatformName
		 * @param side
		 */
		public PriceKey(String sourceRunnerId, String bettingPlatformName, PriceSide side)
		{
			super();
			this.sourceRunnerId = sourceRunnerId;
			this.bettingPlatformName = bettingPlatformName;
			this.side = side;
		}

		/**
		 * @return the sourceRunnerId
		 */
		public String getSourceRunnerId()
		{
			return sourceRunnerId;
		}

		/**
		 * @return the bettingPlatformName
		 */
		public String getBettingPlatformName()
		{
			return bettingPlatformName;
		}

		/**
		 * @return the side
		 */
		public PriceSide getSide()
		{
			return side;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("PriceKey [sourceRunnerId=");
			builder.append(sourceRunnerId);
			builder.append(", bettingPlatformName=");
			builder.append(bettingPlatformName);
			builder.append(", side=");
			builder.append(side);
			builder.append("]");
			return builder.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bettingPlatformName == null) ? 0 : bettingPlatformName.hashCode());
			result = prime * result + ((sourceRunnerId == null) ? 0 : sourceRunnerId.hashCode());
			result = prime * result + ((side == null) ? 0 : side.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			PriceKey other = (PriceKey) obj;
			if (bettingPlatformName == null)
			{
				if (other.bettingPlatformName != null)
				{
					return false;
				}
			} else if (!bettingPlatformName.equals(other.bettingPlatformName))
			{
				return false;
			}
			if (sourceRunnerId == null)
			{
				if (other.sourceRunnerId != null)
				{
					return false;
				}
			} else if (!sourceRunnerId.equals(other.sourceRunnerId))
			{
				return false;
			}
			if (side != other.side)
			{
				return false;
			}
			return true;
		}

		@Override
		public int compareTo(Object o)
		{
			if (getClass() != o.getClass())
			{
				return 0;
			}
			PriceKey other = (PriceKey) o;

			int bpDiff = this.bettingPlatformName.compareTo(other.bettingPlatformName);
			if (bpDiff != 0)
			{
				return bpDiff;
			} else
			{
				int srDiff = this.sourceRunnerId.compareTo(other.sourceRunnerId);
				if (srDiff != 0)
				{
					return srDiff;
				} else
				{
					return this.side.compareTo(other.side);
				}
			}
		}
	}

	private static final Comparator<Price>	PRICE_COMPARATOR	= new Comparator<Price>() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Price p1, Price p2) {
			return p2.getDecimalOdds().compareTo(p1.getDecimalOdds());
		}
	};

	// Precision of IEEE754 Decimal32 format but with RoundingMode.HALF_UP
	private static final MathContext CONTEXT = new MathContext(7, RoundingMode.HALF_UP);
	
  public MessageChannel getLmaxInputChannelDataProcessor() {
    return lmaxInputChannelDataProcessor;
  }

  public void setLmaxInputChannelDataProcessor(MessageChannel lmaxInputChannelDataProcessor) {
    this.lmaxInputChannelDataProcessor = lmaxInputChannelDataProcessor;
  }

  public SourceEventIncidentDao getSourceEventIncidentDao() {
    return sourceEventIncidentDao;
  }

  public void setSourceEventIncidentDao(SourceEventIncidentDao sourceEventIncidentDao) {
    this.sourceEventIncidentDao = sourceEventIncidentDao;
  }

	/**
     * Create the ScoreDto to store it in database.
     * 
     * @param crOffer the connector offer
     * @param connectorId the connector id
     * @return the score to store in database or null if no score was returned from connector
     */
    private ScoreDto createScoreDto(com.aldogrand.sbpc.connectors.model.Offer crOffer, Long connectorId) {
    	ScoreDto scoreDto = null;
    	
    	// only for offers with a score
    	if (crOffer != null && crOffer.getScore() != null) {
    		scoreDto = new ScoreDto();
    		// find the source event
    		for (SourceEventDto sourceEventDto : eventDao.getEvent(Long.parseLong(crOffer.getEventId())).getMappings()) {
    			if (sourceEventDto.getConnector().getId() == connectorId) {
    				scoreDto.setSourceEvent(sourceEventDto);
    				break;
    			}
    		}
    		
    		scoreDto.setType(crOffer.getScore().getType());
    		scoreDto.setHome(crOffer.getScore().getHome());
    		scoreDto.setAway(crOffer.getScore().getAway());
    	}
    	
    	return scoreDto;
    }
}
