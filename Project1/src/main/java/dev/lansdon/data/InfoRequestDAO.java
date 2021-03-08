package dev.lansdon.data;

import dev.lansdon.models.InfoRequest;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

import java.util.Set;

public interface InfoRequestDAO extends GenericDAO<InfoRequest> {
	public InfoRequest add(InfoRequest ir);
}
