package com.Services;

import com.Configuration.FileStorageProperties;
import com.DAOs.CandidacyDAO;
import com.DAOs.DepartmentDAO;
import com.DAOs.PositionDAO;
import com.DAOs.UserDAO;
import com.Entities.*;
import com.Entities.enumeration.Role;
import com.Exceptions.InvalidUserDeletionException;
import com.Exceptions.OldPasswordIncorrectException;
import com.Exceptions.RepeatedPasswordIncorrectException;
import com.Exceptions.UserNotAuthorizedException;
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
import java.nio.file.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService{
//    @Autowired
    private UserDAO userDAO;

//    @Autowired
    private PositionDAO positionDAO;

//    @Autowired
    private CandidacyDAO candidacyDAO;

//    @Autowired
    private PasswordEncoder encoder;

    private DepartmentDAO departmentDAO;

    private Path fileStorageLocation;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PositionDAO positionDAO,
                           CandidacyDAO candidacyDAO, PasswordEncoder encoder, DepartmentDAO departmentDAO,
                           FileStorageProperties fileStorageProperties) throws IOException {
        this.userDAO = userDAO;
        this.positionDAO = positionDAO;
        this.candidacyDAO = candidacyDAO;
        this.departmentDAO = departmentDAO;
        this.encoder = encoder;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new IOException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public List<User> getAllUsers(String loggedUserEmail){
        //no business logic
        List<User> userList = userDAO.getAllBesidesSuperAdminsAndSelf(loggedUserEmail);
        return userList;
    }

    @Transactional
    public List<User> getAllCandidates(){
        //no business logic
        List<User> userList = userDAO.getAllCandidates();
        return userList;
    }

    @Transactional
    public void changeRole(Role role, Integer userId){
        //no business logic
        userDAO.changeRole(role, userId);

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
            message.setFrom(new InternetAddress("uwecscheduler@gmail.com"));
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
    public User adminControlsCreateUser(String name, String email, String role, Integer departmentId) {
        User newUser = new User();
        String generatedPassword = PasswordGenerator.generatePassword();
        newUser.setName(name);
        newUser.setEmail(email);
        if(!role.equals(Role.CANDIDATE.toString())){
            newUser.setDepartment(departmentDAO.getById(departmentId));
        }
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
            message.setFrom(new InternetAddress("uwecscheduler@gmail.com"));
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
    public List<User> getCandidatesAndParticipants(String loggedUserEmail){
        return userDAO.getCandidatesAndParticipants(loggedUserEmail);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserMessage> getMessages(Integer userId, boolean isViewing){
        //if isViewing, then mark all messages that have userId as recipient as seen
        if(isViewing) {
            userDAO.markMessagesAsSeen(userId);
        }
        //retrieve all messages where this user is recipient or a sender
        List<UserMessage> messageList = userDAO.getMessagesByUserId(userId);
        //sort by date
        messageList.sort(new Comparator<UserMessage>(){
            @Override
            public int compare(UserMessage o1, UserMessage o2) {
                if(o1.getSentTime().before(o2.getSentTime())){
                    return 1;
                }
                else if(o2.getSentTime().before(o1.getSentTime())){
                    return -1;
                }
                else return 0;
            }
        });
        return messageList;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserMessage sendMessage(Integer senderId, Integer recipientId, String message) throws Exception{
        try{
            UserMessage newMessage = new UserMessage();
            User recipient = userDAO.getById(recipientId);
            User sender = userDAO.getById(senderId);
            newMessage.setReceiver(recipient);
            newMessage.setSender(sender);
            newMessage.setMessage(message);
            newMessage.setSentTime(new Date());
            newMessage.setSeen(false);
            return userDAO.saveMessage(newMessage);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void adminControlDeleteUser(Integer userId, String loggedUserEmail) throws Exception{
        User user = userDAO.getById(userId);
        if(user == null || user.getRole().equals(Role.SUPER_ADMIN) || user.getEmail().equals(loggedUserEmail)){
            throw new InvalidUserDeletionException("Invalid user");
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
        UserFile userFile = userDAO.getUserFileById(fileId);
        if(userFile == null) return null;
        String fileName = userFile.getFilename();
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
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

    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Integer fileId) throws IOException{
        UserFile userFile = userDAO.getUserFileById(fileId);
        if(userFile == null) throw new NoSuchFileException("File does not exist");
        String fileName = userFile.getFilename();
        try{
            userDAO.removeFile(userFile.getId());
        }
        catch(Exception e){
            throw new IOException();
        }
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        File fileToDelete = new File(filePath.toString());
        if(fileToDelete.delete()){
            return;
        }
        else{
            throw new IOException("Could not delete file");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserFile> getUserFiles(Integer userId){
        List<UserFile> userFileList = userDAO.getUserFilesByUserId(userId);
        return userFileList;
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateInfo(Integer userId, String name, String email, String address, String phone, String bio, String university, Integer departmentId){
        Department department;
        try{
            department = departmentDAO.getById(departmentId);
        }
        catch(Exception e) {
            department = null;
        }
        User user = userDAO.getById(userId);
       user.setEmail(email);
       user.setName(name);
       user.setAddress(address);
       user.setPhone(phone);
       user.setBio(bio);
       user.setUniversity(university);
       user.setDepartment(department);
       user = userDAO.saveOrUpdate(user);
       return user;
    }


    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String logUser, Integer userId, String oldPassword, String newPassword, String newPassword2) throws Exception {

        User user = userDAO.findByEmail(logUser);
        if(!user.getId().equals(userId)) {
            throw new UserNotAuthorizedException("User is not authorized");
        }
        if(!encoder.matches(oldPassword, user.getPassword())) {
            throw new OldPasswordIncorrectException("Old password incorrect");
        }
        if (!newPassword.equals(newPassword2)) {
            throw new RepeatedPasswordIncorrectException("New passwords did not match");
        }
        user.setPassword(encoder.encode(newPassword));
        userDAO.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public User getUserWithDepart(Integer userId) {
        User user = userDAO.getUserWithDepart(userId);
        return user;
    }

    @Override
    @Transactional
    public void setCandidateAlert(Integer userId, Boolean alert){
        userDAO.setCandidateAlert(userId, alert);
    }

    @Override
    @Transactional
    public void updateUserPosition(Integer userId, double lat, double lng){
        User user = userDAO.getById(userId);
        user.setLat(lat);
        user.setLng(lng);
        user.setCoordsLastUpdate(new Date());
        userDAO.saveOrUpdate(user);
        System.out.println("UPDATING USER POSITION");
    }

}
