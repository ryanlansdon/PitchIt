package dev.lansdon.services;

import java.util.Set;

import dev.lansdon.models.Genre;
import dev.lansdon.models.Pitch;

public interface PitchService {
	public Integer createPitch(Pitch p);
	public void updatePitch(Pitch p);
	public Pitch getPitchById(Integer id);
	public Set<Pitch> getPitchesByGenre(Genre g);
	public Set<Pitch> getAllPitches();
}
