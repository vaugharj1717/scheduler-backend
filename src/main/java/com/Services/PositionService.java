package com.Services;

import com.Entities.Candidacy;
import com.Entities.Position;

import java.util.List;

public interface PositionService {
    public List<Position> getPositionsByDepartment (Integer id);
    public Candidacy assignCandidateToPosition(Integer positionId, Integer candidateId);
}