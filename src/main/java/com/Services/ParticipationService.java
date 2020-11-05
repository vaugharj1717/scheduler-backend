package com.Services;

import com.Entities.Department;

import java.util.List;

public interface ParticipationService {
    void setFeedback (Integer participationId, String feedback) throws Exception;
}
