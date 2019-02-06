package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.aldogrand.sbpc.dataaccess.SourceEventIncidentDao;
import com.aldogrand.sbpc.dataaccess.model.SourceEventIncidentDto;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.IncidentType;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * Implementation of {@link SourceEventIncidentDao} for 
 * database interaction
 * 
 * @author aldogrand
 *
 */
public class SourceEventIncidentDaoJpaImpl extends GenericJpaDao<SourceEventIncidentDto> implements SourceEventIncidentDao {

	public SourceEventIncidentDaoJpaImpl() {
		super(SourceEventIncidentDto.class);
	}

	@Override
	public SourceEventIncidentDto getIncident(long id) {
		return getEntity(id);
	}
	
	@Override
	public SourceEventIncidentDto getIncident(EventPhase eventPhase, long sequenceNumber,
			IncidentType incidentType, long participantId, Date elapsedTime) {
		return executeCachedQuery("from SourceEventIncident ei where ei.eventPhase = ? and ei.sequenceNumber = ? and ei.incidentType = ? and ei.participantId = ? and ei.elapsedTime = ? ",
				eventPhase, sequenceNumber, incidentType, participantId, elapsedTime);
	}

	@Override
	public SourceEventIncidentDto saveEventIncident(SourceEventIncidentDto incident) {
		return saveEntity(incident);
	}

	@Override
	public boolean validateIncidentSequence(long eventId, long sequenceNumber, Date elapsedTime) {
		SourceEventIncidentDto laterIncident =  executeCachedQuery("from SourceEventIncident ei where ei.sourceEvent.event.id = ? and (ei.elapsedTime > ? or ei.sequenceNumber < ?)",
				eventId, elapsedTime, sequenceNumber);
		
		return laterIncident == null ? true : false;
	}

	@Override
	public SourceEventIncidentDto getlatestEventIncidentReceivedForEvent(long eventId) {
		List<SourceEventIncidentDto> eventIncidents = executeCachedQuery("from SourceEventIncident ei where ei.sourceEvent.event.id = ? order by ei.creationTime desc", 0, 1, eventId);
		// Max results set to 1 so return the result
		return CollectionUtils.isEmpty(eventIncidents) ? null : eventIncidents.get(0); 
	}
	
	@Override
	public long getlatestMatchIncidentSequenceReceivedForEvent(long eventId, long sequencePhase) {
		Long latestSeqNumber = executeCachedQuery("select max(sequenceNumber) from SourceEventIncident ei where ei.sourceEvent.event.id = ? and ei.sequencePhase = ?",
				Long.class, eventId, sequencePhase);
		return latestSeqNumber == null ? 0 : latestSeqNumber;
	}

}
