package com.controllers;


import com.App;
import com.Controllers.MeetingController;
import com.Entities.Candidacy;
import com.Entities.Meeting;
import com.Entities.Meeting;
import com.Entities.enumeration.MeetingType;
import com.Entities.enumeration.Role;
import com.Services.MeetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class MeetingControllerTest {

    @Mock
    private MeetingService meetingService;

    @InjectMocks
    private MeetingController meetingController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(meetingController).build();
    }

    @Test
    public void testCreateMeeting() throws Exception{
        //mock dependency behavior
        Meeting meeting = new Meeting();
        meeting.setId(1); meeting.setMeetingType(MeetingType.MEET_FACULTY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1 = sdf.parse("2020/01/01 12:00:00");
        Date date2 = sdf.parse("2020/01/01 13:00:00");
        meeting.setStartTime(date1); meeting.setEndTime(date2);
        List<Boolean> canViewFeedbackList = new ArrayList<Boolean>(); canViewFeedbackList.add(true);
        List<Boolean> canGiveFeedbackList = new ArrayList<Boolean>(); canGiveFeedbackList.add(true);
        List<Integer> participantList = new ArrayList<Integer>(); participantList.add(3);
        when(meetingService.createMeeting(1, 2, date1, date2,MeetingType.MEET_FACULTY, canViewFeedbackList,
                canGiveFeedbackList, participantList
                )).thenReturn(meeting);

        //establish expected result
        ObjectWriter ow = new ObjectMapper().writer();
        String expected = ow.writeValueAsString(meeting);

        //perform test
        JSONObject request = new JSONObject();
        request.put("locationId", 2);
        request.put("meetingType", "MEET_FACULTY");
        request.put("startTime", "2020/01/01 12:00:00");
        request.put("endTime", "2020/01/01 13:00:00");
        JSONObject participation = new JSONObject();
        participation.put("canLeaveFeedback", "true");
        participation.put("canViewFeedback", "true");
        participation.put("participantId", 3);
        JSONArray arrNode = new JSONArray();
        arrNode.put(participation);
        request.put("participations", arrNode);
        String requestJson = request.toString();

        String response = mockMvc
                .perform(post("/meeting/1")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expected);
    }

    @Test
    public void testDeleteMeeting() throws Exception{
        //define return value of dependency
        doNothing().when(meetingService).deleteMeeting(1);
        String expectedResponse = "1";

        //perform test
        String response = mockMvc.perform(delete("/meeting/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert(response.equals(expectedResponse));
    }


}
