/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper.impl;

import com.aldogrand.sbpc.dataaccess.ParticipantDao;
import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.*;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.sbpc.model.SourceRunner;
import com.aldogrand.kfc.services.mysql.mapper.RunnerMappingWriterInterface;

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
import java.util.List;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

/**
 * <p>
 * <b>Title</b> RunnerMappingWriter.java
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
public class RunnerMappingWriter implements RunnerMappingWriterInterface
{
	private final Logger	logger	= LogManager.getLogger(getClass());

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	private RunnerDao		runnerDao;
	private SourceRunnerDao	sourceRunnerDao;
	private ParticipantDao	participantDao;
	private boolean			isLock	= false;

	/**
	 * 
	 */
	public RunnerMappingWriter(RunnerDao runnerDao, SourceRunnerDao sourceRunnerDao, ParticipantDao participantDao)
	{
		this.runnerDao = runnerDao;
		this.sourceRunnerDao = sourceRunnerDao;
		this.participantDao = participantDao;
	}

	/*
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.RunnerMappingWriterInterface#updateRunner(com.aldogrand
	 * .sbpc.model.Runner, com.aldogrand.sbpc.model.SourceRunner)
	 * 
	 * @param sourceRunner
	 * 
	 * @param runner
	 */
	@Override	
	public Runner updateRunner(SourceRunner sourceRunner, Runner runner) throws RuntimeException
	{
		Runner returnRunner = null;
		try
		{
			assert runner != null;
			assert sourceRunner != null;
			RunnerDto runnerDto = this.runnerDao.getRunner(runner.getId());
			SourceRunnerDto sourceRunnerDto = this.sourceRunnerDao.getSourceRunner(sourceRunner.getId(), isLock);
			if (runnerDto != null && sourceRunnerDto != null)
			{
				logger.debug(String.format("Mapping source runner %s to existing runner %s.", 
						sourceRunnerDto.getId(), runnerDto.getId()));
				ParticipantDto participantDto = runnerDto.getParticipant();
				if (participantDto != null)
				{
					NameVariantDto nameVariantDto = new NameVariantDto();
					nameVariantDto.setVariant(sourceRunnerDto.getSourceName());
					participantDto.getNameVariants().add(nameVariantDto);
					nameVariantDto = new NameVariantDto();
					nameVariantDto.setVariant(sourceRunnerDto.getSourceName().toLowerCase());
					participantDto.getNameVariants().add(nameVariantDto);
					participantDto = participantDao.saveParticipant(participantDto);
				}
				runnerDto.setLastChangeTime(new Date());
				runnerDto = runnerDao.saveRunner(runnerDto);

				sourceRunnerDto.setRunner(runnerDto);
				sourceRunnerDao.saveSourceRunner(sourceRunnerDto);

				logger.debug(String.format("Mapping source runner %s - %s from connector %s - %s to runner %s - %s.", 
						 sourceRunnerDto.getId(), sourceRunnerDto.getSourceName(), sourceRunnerDto.getConnector().getId(),
							sourceRunnerDto.getConnector().getName(), runnerDto.getId(), runnerDto.getName()));	
				returnRunner = ModelFactory.createRunner(runnerDto, false);
			}
		} catch (TransactionException te)
		{
			logger.error(String.format("Error updating the mapping for the existing runner %s.", sourceRunner.getSourceName() ), 
					te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunner> builder = MessageBuilder.withPayload(sourceRunner).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing runner.", te);
			}
			return null;
		} catch (AssertionError ae)
		{
			logger.error("Error updating the mapping for the existing runner.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunner> builder = MessageBuilder.withPayload(sourceRunner).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing runner.", ae);
			}
		}
		return returnRunner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.RunnerMappingWriterInterface#createRunner(com.aldogrand
	 * .sbpc.model.Runner, com.aldogrand.sbpc.model.SourceRunner)
	 * 
	 * @param sourceRunner
	 * 
	 * @param runner
	 */
	@Override	
	public Runner createRunner(SourceRunner sourceRunner, Runner runner) throws RuntimeException
	{
		try
		{
			assert sourceRunner != null;
			Runner returnRunner = null;
			SourceRunnerDto sourceRunnerDto = sourceRunnerDao.getSourceRunner(sourceRunner.getId(), isLock);
			if (sourceRunnerDto != null && sourceRunnerDto.getRunner() == null && sourceRunnerDto.getSourceMarket() != null
					&& sourceRunnerDto.getSourceMarket().getMarket() != null)
			{
				MarketDto marketDto = sourceRunnerDto.getSourceMarket().getMarket();
				logger.debug(String.format("Creating source runner %s.", 
						sourceRunnerDto.getId()));
				RunnerDto runnerDto = new RunnerDto();
				runnerDto.setMarket(marketDto);
				runnerDto.setName(sourceRunnerDto.getSourceName());
				runnerDto.setSequence(sourceRunnerDto.getSequence());
				if ((sourceRunnerDto.getType() != null) && (sourceRunnerDto.getType().equals(RunnerType.PARTICIPANT)))
				{
					List<ParticipantDto> participantDtos = participantDao.getParticipants(sourceRunnerDto.getSourceName(), null, null);

					/*
					 * No participant - create one.
					 */
					if (participantDtos.isEmpty())
					{
						ParticipantDto participantDto = new ParticipantDto();
						participantDto.setName(sourceRunnerDto.getSourceName());
						NameVariantDto nameVariantDto = new NameVariantDto();
						nameVariantDto.setVariant(sourceRunnerDto.getSourceName());
						participantDto.getNameVariants().add(nameVariantDto);
						nameVariantDto = new NameVariantDto();
						nameVariantDto.setVariant(sourceRunnerDto.getSourceName().toLowerCase());
						participantDto.getNameVariants().add(nameVariantDto);
						participantDto.setType(ParticipantType.TEAM);
						EventDto eventDto = marketDto.getEvent();
						if (eventDto != null)
						{
							for (MetaTagDto metaTagDto : eventDto.getMetaTags())
							{
								participantDto.getMetaTags().add(metaTagDto);
							}
						}
						participantDto = participantDao.saveParticipant(participantDto);

						runnerDto.setParticipant(participantDto);
						/*
						 * One participant - use it
						 */
					} else if (participantDtos.size() >= 1)
					{
						/*
						 * Multiple participants - select the appropriate TODO compare meta tags with event meta tags
						 * TODO for now just pick the first
						 */
						runnerDto.setParticipant(participantDtos.get(0));
					}
				}
				runnerDto.setType(sourceRunnerDto.getType());
				runnerDto.setSide(sourceRunnerDto.getSide());
				runnerDto.setHandicap(sourceRunnerDto.getHandicap());
				runnerDto.setRotationNumber(sourceRunnerDto.getRotationNumber());
				runnerDto.setRunnerStatus(sourceRunnerDto.getRunnerStatus());
				runnerDto.setResultStatus(sourceRunnerDto.getResultStatus());
				runnerDto.setLastChangeTime(new Date());

				runnerDto = runnerDao.saveRunner(runnerDto);
				sourceRunnerDto.setRunner(runnerDto);
				sourceRunnerDto.setCreator(true);
				sourceRunnerDao.saveSourceRunner(sourceRunnerDto);
				marketDto.getRunners().add(runnerDto);

				logger.debug(String.format("Mapping source runner %s - %s from connector %s - %s to runner %s - %s.", 
						 sourceRunnerDto.getId(), sourceRunnerDto.getSourceName(), sourceRunnerDto.getConnector().getId(),
							sourceRunnerDto.getConnector().getName(), runnerDto.getId(), runnerDto.getName()));	
				returnRunner = ModelFactory.createRunner(runnerDto, false);
			}
			return returnRunner;
		} catch (TransactionException te)
		{
			logger.error(String.format("Error creating the mapping for the existing runner %s.", sourceRunner.getSourceName() ), 
					te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunner> builder = MessageBuilder.withPayload(sourceRunner).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for the runner.", te);
			}
			return null;
		} catch (AssertionError ae)
		{
			logger.error("Error creating the mapping for the runner.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunner> builder = MessageBuilder.withPayload(sourceRunner).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for the runner.", ae);
			}
			return null;
		}
	}
}
