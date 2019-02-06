package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.ParticipantDao;
import com.aldogrand.sbpc.dataaccess.model.ParticipantDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> ParticipantDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link ParticipantDao}. 
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
public class ParticipantDaoJpaImpl extends GenericJpaDao<ParticipantDto>
        implements ParticipantDao
{
    /**
     * Create a new {@link ParticipantDaoJpaImpl}.
     */
    public ParticipantDaoJpaImpl()
    {
        super(ParticipantDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ParticipantDao#getParticipant(long)
     */
    @Override
    public ParticipantDto getParticipant(long id)
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ParticipantDao#getNumberOfParticipants(
     * java.lang.String)
     */
    @Override
    public int getNumberOfParticipants(String name)
    {
        assert name != null;
        
        Number count = executeQuery("select count(distinct p.id) from Participant p join p.nameVariants v where p.name = ?1 or v.variant = ?1", 
                Number.class, name);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ParticipantDao#getParticipants(
     * java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<ParticipantDto> getParticipants(String name, Integer offset,
            Integer maxResults)
    {
        assert name != null;
        
        return executeQuery("select distinct p from Participant p join p.nameVariants v where p.name = ?1 or v.variant = ?1", 
                offset, maxResults, name);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ParticipantDao#saveParticipant(
     * com.aldogrand.sbpc.dataaccess.model.ParticipantDto)
     */
    @Override
    public ParticipantDto saveParticipant(ParticipantDto participant)
    {
        assert participant != null;
        
        return saveEntity(participant);
    }
}
