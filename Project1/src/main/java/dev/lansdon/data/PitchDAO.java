package dev.lansdon.data;

import java.util.Set;

import dev.lansdon.models.Genre;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.Role;

public interface PitchDAO extends GenericDAO<Pitch> {
	Pitch add(Pitch p);
	Set<Pitch> getPitchesByGenre(Genre g);
	//Set<Pitch> getPitchesByRole(Role r);
}
