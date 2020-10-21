package com.Services;

import com.Configuration.FileStorageProperties;
import com.DAOs.CandidacyDAO;
import com.DAOs.PositionDAO;
import com.DAOs.UserDAO;
import com.Entities.*;
import com.Entities.enumeration.Role;
import com.Security.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private Path fileStorageLocation;

    @Autowired
    public UserServiceImpl(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new IOException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

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

    @Transactional
    public User adminControlsCreateUser(String name, String email, String role) {
        User newUser = new User();
        String generatedPassword = PasswordGenerator.generatePassword();
        newUser.setName(name);
        newUser.setEmail(email);
        if(role.equals("SUPER_ADMIN")) return null;
        newUser.setRole(Role.getFromName(role));
        newUser.setPassword(encoder.encode(generatedPassword));
        User savedUser = userDAO.saveOrUpdate(newUser);

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

        return savedUser;

    }

    @Transactional
    public void adminControlDeleteUser(Integer userId){
        User user = userDAO.getById(userId);
        if(user == null || user.getRole().equals(Role.SUPER_ADMIN)){
            return;
        }
        userDAO.remove(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserFile storeUserFile(MultipartFile file, Integer userId) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        //Create file, appending a number to filename if taken
        int appendedFileNumber = 0;
        File target = new File(targetLocation.toString());
        while(target.exists()){
            appendedFileNumber++;
            target = new File(this.fileStorageLocation.resolve(appendedFileNumber + "_" + fileName).toString());
        }
        if (appendedFileNumber == 0) {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(appendedFileNumber+"_"+fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        //create database UserFile object
        UserFile newUserFile = new UserFile();
        User user = userDAO.getById(userId);
        if(user == null) return null;
        newUserFile.setUser(user);
        if (appendedFileNumber == 0) {
            newUserFile.setFilename(fileName);
        } else {
            newUserFile.setFilename(appendedFileNumber + "_" + fileName);
        }
        UserFile savedUserFile = userDAO.addUserFile(newUserFile);
        return savedUserFile;
    }

    @Transactional(rollbackFor = Exception.class)
    public Resource loadUserFileAsResource(Integer fileId) throws IOException {
//        UserFile userFile = userDAO.getUserFileById(fileId);
//        if(userFile == null) return null;
//        String fileName = userFile.getFilename();
        try {
            Path filePath = this.fileStorageLocation.resolve("RyanVaughanResume.docx").normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new IOException("File not found", ex);
        }
    }
}
