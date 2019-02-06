package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.GFeedSettingDto;

/**
 * <p>
 * <b>Title</b> GFeedSettingDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link GFeedSettingDto}s.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Cillian Kelly
 * @version 1.0
 */
public interface GFeedSettingDao
{
    /**
     * Return the {@link GFeedSettingDto} with the given id
     * @param id
     * @return gFeedSetting
     */
    GFeedSettingDto getGFeedSetting(long id);

            
    /**
     * Get the {@link GFeedSettingDto}s that belongs to an {@link EventDto} with id
     * @param event
     * @return
     */
    GFeedSettingDto getGFeedSetting(EventDto event);
    

    /**
     * Save the {@link GFeedSettingDto} returning the updated version.
     * @param gFeedSetting
     * @return
     */
    GFeedSettingDto saveGFeedSetting(GFeedSettingDto gFeedSetting);
    
    /**
     * Delete the {@link GFeedSettingDto}.
     * @param gFeedSetting
     */
    void deleteGFeedSetting(GFeedSettingDto gFeedSetting);
}
