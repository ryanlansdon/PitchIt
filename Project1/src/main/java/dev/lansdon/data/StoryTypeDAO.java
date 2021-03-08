package dev.lansdon.data;

import dev.lansdon.models.StoryType;

public interface StoryTypeDAO extends GenericDAO<StoryType> {
    public StoryType add(StoryType st);
    public StoryType getByName(String name);
}
