package dev.lansdon.services;

import dev.lansdon.models.InfoRequest;
import dev.lansdon.models.Pitch;
import dev.lansdon.models.User;

public interface InfoRequestService {
    InfoRequest addRequestByUsers(User requested, User editor, InfoRequest ir);
    InfoRequest addRequestByPitch(User editor, Pitch pitch, InfoRequest ir);
    void updateRequest(InfoRequest ir);
}
