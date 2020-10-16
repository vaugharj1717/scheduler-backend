package com.Services;

import com.DAOs.CandidacyDAO;
import com.DAOs.PositionDAO;
import com.DAOs.UserDAO;
import com.Entities.Candidacy;
import com.Entities.Position;
import com.Entities.Schedule;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Security.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;

    @Autowired
    PositionDAO positionDAO;

    @Autowired
    CandidacyDAO candidacyDAO;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public List<User> getAllCandidates(){
        //no business logic
        List<User> userList = userDAO.getAllCandidates();
        return userList;
    }

    @Transactional
    public List<User> getAllParticipants() {
        return userDAO.getAllParticipants();
    }

    @Transactional
    public Candidacy createCandidate(Integer positionId, String name, String email) {
        User newUser = new User();
        String generatedPassword = PasswordGenerator.generatePassword();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(Role.CANDIDATE);
        newUser.setPassword(encoder.encode(generatedPassword));

        Candidacy newCandidacy = new Candidacy();
        newCandidacy.setCandidate(newUser);
        Schedule newSchedule = new Schedule();
        newCandidacy.setSchedule(newSchedule);
        Position position = positionDAO.getById(positionId);
        newCandidacy.setPosition(position);

        Candidacy savedCandidacy = candidacyDAO.saveOrUpdate(newCandidacy);

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
            message.setFrom(new InternetAddress("vaugharj1717@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Invitation to UWEC Scheduler");
            message.setText("You have been provisioned an account on the UWEC Scheduling System\n" +
                    "Username: " + email + "\n" +
                    "Password: " + generatedPassword + "\n");
            Transport.send(message);
        }
        catch(MessagingException mex){
            //mex.printStackTrace();
            return null;
        }

        return savedCandidacy;

    }
}
