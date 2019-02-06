package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.MetaTagDao;
import com.aldogrand.sbpc.dataaccess.model.MetaTagDto;
import com.aldogrand.sbpc.model.MetaTagType;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> MetaTagDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link MetaTagDao}.
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
public class MetaTagDaoJpaImpl extends GenericJpaDao<MetaTagDto> implements
        MetaTagDao
{
    /**
     * Create a new {@link MetaTagDaoJpaImpl}.
     */
    public MetaTagDaoJpaImpl()
    {
        super(MetaTagDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MetaTagDao#getMetaTag(long)
     */
    @Override
    public MetaTagDto getMetaTag(long id)
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MetaTagDao
     * #getMetaTag(java.lang.String, com.aldogrand.sbpc.model.MetaTagType)
     */
    @Override
    public MetaTagDto getMetaTag(String name, MetaTagType type)
    {
        assert name != null;
        
        if (type != null)
        {
            return executeQuery("from MetaTag t where t.name = ?1 and t.type = ?2", 
                    name, type);
        }
        else
        {
            return executeQuery("from MetaTag t where t.name = ?1 and t.type is null", 
                    name);
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MetaTagDao#getMetaTags(
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MetaTagDto> getMetaTags(Integer offset, Integer maxResults)
    {
        return executeQuery("from MetaTag t order by t.name", offset, maxResults);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MetaTagDao#getMetaTags(java.util.List, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MetaTagDto> getMetaTags(List<MetaTagType> types,
            Integer offset, Integer maxResults)
    {
        assert types != null;
        
        return executeQuery("from MetaTag t where t.type in (?1) order by t.name", 
                offset, maxResults, types);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MetaTagDao#saveMetaTag(
     * com.aldogrand.sbpc.dataaccess.model.MetaTagDto)
     */
    @Override
    public MetaTagDto saveMetaTag(MetaTagDto metaTag)
    {
        return saveEntity(metaTag);
    }
}
