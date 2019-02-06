package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.ParticipantDto;

import java.util.List;

/**
 * <p>
 * <b>Title</b> ParticipantDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link ParticipantDto}s.
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
public interface ParticipantDao
{
    /**
     * Get the {@link ParticipantDto} with the given id.
     * @param id
     * @return
     */
    ParticipantDto getParticipant(long id);
    
    /**
     * Get the number of {@link ParticipantDto}s that have either the name or a 
     * variant of the name given.
     * @param name
     * @return
     */
    int getNumberOfParticipants(String name);
    
    /**
     * Get the {@link ParticipantDto}s that have either the name or a variant of the name given.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param name
     * @param offset
     * @param maxResults
     * @return
     */
    List<ParticipantDto> getParticipants(String name, Integer offset, Integer maxResults);
    
    /**
     * Save the  returning the updated version.
     * @param participant
     * @return
     */
    ParticipantDto saveParticipant(ParticipantDto participant);
}
