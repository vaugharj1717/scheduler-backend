package com.Services;

import com.DAOs.MeetingDAO;
import com.Entities.Meeting;
import com.Entities.Participation;
import com.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AlertSenderImpl implements AlertSender {
    @Autowired
    MeetingDAO meetingDAO;
    private Collection<Meeting> upcomingMeetings = Collections.synchronizedCollection(new HashSet<Meeting>());

    @Scheduled(fixedRate = 10000)
    public void getAlertMeetings() {
        Date twentyMin = new Date();
        Date thirtyMin = new Date();
        twentyMin.setTime(new Date().getTime() + 1200000);
        thirtyMin.setTime(new Date().getTime() + 1800000);
//        twentyMin.setTime(new Date().getTime() + 1440000);
//        thirtyMin.setTime(new Date().getTime() + 1600000);
        List<Meeting> meetingList = meetingDAO.getAlertMeetings(twentyMin, thirtyMin);
        upcomingMeetings.addAll(meetingList);
        System.out.println(upcomingMeetings);
    }

    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
        Date now = new Date();
        Date soon = new Date();
        soon.setTime(new Date().getTime() + 900000);
//        soon.setTime(new Date().getTime() + 1440000);

        for(Meeting m : upcomingMeetings) {
            if(m.getStartTime().getTime() > now.getTime() && m.getStartTime().getTime() < soon.getTime() ) {
                upcomingMeetings.remove(m);
                for(Participation p : m.getParticipations()) {
                    if(p.isAlert()) {
                        System.out.println("SENDING EMAIL TO PARTICIPANT");
                        String email = p.getParticipant().getEmail();
                        sendEmail(false, email, m, p.getParticipant().getName());
                    }
                }
                User candidate = m.getSchedule().getCandidacy().getCandidate();
                if(candidate.getAlert()){
                    System.out.println("SENDING EMAIL TO CANDIDATE");
                    String email = candidate.getEmail();
                    sendEmail(true, email, m, candidate.getName());
                }

            }
        }
    }

    private void sendEmail(boolean isCandidate, String email, Meeting meeting, String name) {
        String meetingType = meeting.getMeetingType().toString().equals("MEET_FACULTY")? "meeting with faculty" : "presentation to students";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String startTime = formatter.format(meeting.getStartTime());
        String building = meeting.getLocation().getBuildingName();
        String roomNumber = meeting.getLocation().getRoomNumber().toString();
        String candidateName = meeting.getSchedule().getCandidacy().getCandidate().getName();

        //send email
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("smpt.gmail.com", "localhost");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("uwecscheduler@gmail.com", "scheduler$@#!");
            }
        });
        //session.setDebug(true);
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("uwecscheduler@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Meeting Alert");
            if(isCandidate) {
                message.setText(candidateName + ",\n\nYou have a " + meetingType + " at " + startTime + " in room " + building + " " + roomNumber + "\n\n-UWEC Scheduling System");
            } else {
                message.setText(name + ",\n\nYou are a participant of " + candidateName + "'s " + meetingType + " at " + startTime + " in room " + building + " " + roomNumber + "\n\n-UWEC Scheduling System");
            }
            Transport.send(message);
        }
        catch(MessagingException mex){
            mex.printStackTrace();
        }
    }
}
