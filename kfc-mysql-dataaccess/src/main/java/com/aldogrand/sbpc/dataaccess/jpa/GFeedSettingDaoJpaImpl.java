package com.aldogrand.sbpc.dataaccess.jpa;

import com.aldogrand.sbpc.dataaccess.GFeedSettingDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.GFeedSettingDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> GFeedSettingDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link GFeedSettingDao}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * 
 * @author Cillian Kelly
 * @version 1.0
 */
public class GFeedSettingDaoJpaImpl extends GenericJpaDao<GFeedSettingDto>
		implements GFeedSettingDao {

	/**
	 * Create a new {@link EventDaoJpaImpl}.
	 */
	public GFeedSettingDaoJpaImpl() {
		super(GFeedSettingDto.class);
	}

	@Override
	public GFeedSettingDto getGFeedSetting(long id) {

		return getEntity(id);

	}

	@Override
	public GFeedSettingDto getGFeedSetting(EventDto event) {

		assert event != null;

		return executeQuery("from GFeedSetting g where g.event = ?1", event);

	}

	@Override
	public GFeedSettingDto saveGFeedSetting(GFeedSettingDto gFeedSetting) {

		assert gFeedSetting != null;

		return saveEntity(gFeedSetting);
	}

	@Override
	public void deleteGFeedSetting(GFeedSettingDto gFeedSetting) {

		assert gFeedSetting != null;

		removeEntity(gFeedSetting);

	}

}
