package dev.lansdon.data;

import dev.lansdon.data.hibernate.*;

public class DAOFactory {
	public static UserDAO getUserDAO() { return new UserHibernate(); }

	public static InfoRequestDAO getInfoRequestDAO() {
		return new InfoRequestHibernate();
	}
	
	public static PitchDAO getPitchDAO() {
		return new PitchHibernate();
	}

	public static GenreDAO getGenreDAO() { return new GenreHibernate(); }

	public static StoryTypeDAO getStoryTypeDAO() { return new StoryTypeHibernate(); }
}
