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
        List<Meeting> meetingList = meetingDAO.getAlertMeetings(twentyMin, thirtyMin);
        upcomingMeetings.addAll(meetingList);
        System.out.println(upcomingMeetings);
    }

    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
        Date now = new Date();
        Date soon = new Date();
        soon.setTime(new Date().getTime() + 900000);

        for(Meeting m : upcomingMeetings) {
            if(m.getStartTime().getTime() > now.getTime() && m.getStartTime().getTime() < soon.getTime() ) {
                upcomingMeetings.remove(m);
                for(Participation p : m.getParticipations()) {
                    if(p.isAlert()) {
                        String email = p.getParticipant().getEmail();
                        sendEmail(email, m);
                    }
                }
                User candidate = m.getSchedule().getCandidacy().getCandidate();
                if(candidate == null){
                    System.out.println("NULL");
                }
                else if(candidate.getAlert()){
                    String email = candidate.getEmail();
                    sendEmail(email, m);
                }

            }
        }
    }

    private void sendEmail(String email, Meeting meeting) {
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
            message.setSubject("You have an upcoming meeting");
            message.setText("You have an upcoming meeting\n");
            Transport.send(message);
        }
        catch(MessagingException mex){
            mex.printStackTrace();
        }
    }
}
