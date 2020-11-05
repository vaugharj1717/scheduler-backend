package com.Services;

import com.DAOs.DepartmentDAO;
import com.DAOs.ParticipationDAO;
import com.Entities.Department;
import com.Entities.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    ParticipationDAO participationDAO;

    @Override
    @Transactional
    public void setFeedback(Integer participationId, String feedback) throws Exception {
        try {
            Participation participation = participationDAO.getById(participationId);
            if (participation.isCanLeaveFeedback()) {
                participation.setFeedback(feedback);
                participationDAO.saveOrUpdate(participation);
            } else {
                throw new Exception("This participation can not leave a feedback");
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public List<Participation> getAllParticipationByMeetingId(Integer meetingId) {
        return participationDAO.getAllParticipationByMeetingId(meetingId);
    }
}
