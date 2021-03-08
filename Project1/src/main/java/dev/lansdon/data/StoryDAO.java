package dev.lansdon.data;

import dev.lansdon.models.Story;

public interface StoryDAO extends GenericDAO<Story> {
    public Story add(Story s);
}
