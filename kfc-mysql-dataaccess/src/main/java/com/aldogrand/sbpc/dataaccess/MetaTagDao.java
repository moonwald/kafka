package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.MetaTagDto;
import com.aldogrand.sbpc.model.MetaTagType;

import java.util.List;

/**
 * <p>
 * <b>Title</b> MetaTagDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and savess.
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
public interface MetaTagDao
{
    /**
     * Get the {@link MetaTagDto} with the given id.
     * @param id
     * @return
     */
    MetaTagDto getMetaTag(long id);
    
    /**
     * Get the {@link MetaTagDto} with the name and type.
     * @param name The {@link MetaTagDto} name.
     * @param type The {@link MetaTagType}. Can be null.
     * @return
     */
    MetaTagDto getMetaTag(String name, MetaTagType type);
    
    /**
     * Get a list of {@link MetaTagDto}s.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<MetaTagDto> getMetaTags(Integer offset, Integer maxResults);

    /**
     * Get a list of {@link MetaTagDto}s filtering the results with the given {@link MetaTagType}s.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param types
     * @param offset
     * @param maxResults
     * @return
     */
    List<MetaTagDto> getMetaTags(List<MetaTagType> types, Integer offset, Integer maxResults);

    /**
     * Save the {@link MetaTagDto} returning the updated version.
     * @param metaTag
     * @return
     */
    MetaTagDto saveMetaTag(MetaTagDto metaTag);
}
