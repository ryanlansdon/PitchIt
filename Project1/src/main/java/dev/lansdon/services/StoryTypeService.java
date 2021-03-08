package dev.lansdon.services;

import dev.lansdon.models.StoryType;

import java.util.Set;

public interface StoryTypeService {
    public StoryType getStoryTypeById(Integer id);
    public Set<StoryType> getAllStoryTypes();
    public StoryType getStoryTypeByName(String name);
}
