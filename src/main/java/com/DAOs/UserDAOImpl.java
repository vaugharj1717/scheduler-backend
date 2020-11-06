package com.DAOs;

import com.Entities.User;
import com.Entities.UserFile;
import com.Entities.UserMessage;
import com.Entities.enumeration.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager em;

    public List<User> getAll(){
        List<User> userList = em.createQuery("SELECT u from User u", User.class)
                .getResultList();
        return userList;
    }

    public List<User> getAllBesidesSuperAdminsAndSelf(String email){
        List<User> userList = em.createQuery("SELECT u from User u WHERE u.role != :role and u.email != :email", User.class)
                .setParameter("role", Role.SUPER_ADMIN)
                .setParameter("email", email)
                .getResultList();
        return userList;
    }

    public List<User> getAllCandidates(){
        List<User> userList = em.createQuery("SELECT u from User u WHERE u.role = ?1", User.class)
                .setParameter(1, Role.CANDIDATE)
                .getResultList();
        return userList;
    }

    public List<User> getCandidatesAndParticipants(String userEmail){
        List<User> userList = em.createQuery("SELECT u from User u WHERE (u.role = ?1 or u.role = ?2) " +
                "AND u.email != ?3", User.class)
                .setParameter(1, Role.CANDIDATE)
                .setParameter(2, Role.PARTICIPANT)
                .setParameter(3, userEmail)
                .getResultList();
        return userList;
    }

    public User getById(Integer id){
        User user = em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        return user;
    }
    public User getByScheduleId(Integer scheduleId){
        User user = em.createQuery(
                "SELECT DISTINCT u FROM Schedule s JOIN s.candidacy c JOIN c.candidate u WHERE s.id = :scheduleId", User.class)
                .setParameter("scheduleId", scheduleId)
                .getSingleResult();
        return user;
    }
    public User saveOrUpdate(User user){
        User savedUser = em.merge(user);
        return savedUser;
    }

    public void remove(Integer id){
        em.remove(
                em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    public void removeFile(Integer id){
        em.remove(
                em.createQuery("SELECT uf FROM UserFile uf WHERE uf.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    public User findByEmail(String email){
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<User> getAllParticipants() {
        Query q = em.createQuery("select i from User i where UPPER(i.role) = ?1 or i.role = ?2");
        q.setParameter(1, Role.PARTICIPANT);
        q.setParameter(2, Role.DEPARTMENT_ADMIN);
        List<User> userList = q.getResultList();
        return userList;
    }

    public UserFile addUserFile(UserFile userFile){
        UserFile savedUserFile = em.merge(userFile);
        return savedUserFile;
    }

    public UserFile getUserFileById(Integer userFileId){
        UserFile userFile = em.createQuery(
                "SELECT uf FROM UserFile uf WHERE uf.id = :id", UserFile.class)
                .setParameter("id", userFileId)
                .getSingleResult();
        return userFile;
    }

    public List<UserFile> getUserFilesByUserId(Integer userId){
        return em.createQuery("SELECT uf FROM UserFile uf WHERE uf.user.id = :id", UserFile.class)
                .setParameter("id", userId)
                .getResultList();
    }

    public void changeRole(Role role, Integer userId){
        em.createQuery("UPDATE User u SET u.role = :role WHERE u.id = :userId")
               .setParameter("role", role)
               .setParameter("userId", userId)
               .executeUpdate();
    }

    public void markMessagesAsSeen(Integer recipientId){
        em.createQuery("UPDATE UserMessage m SET m.seen = TRUE WHERE m.receiver.id = :recipientId")
                .setParameter("recipientId", recipientId)
                .executeUpdate();
    }

    public List<UserMessage> getMessagesByUserId(Integer userId){
        return em.createQuery("SELECT m FROM UserMessage m LEFT JOIN FETCH m.receiver " +
                "LEFT JOIN FETCH m.sender WHERE (m.receiver.id = :id1 OR m.sender.id = :id2)", UserMessage.class)
                .setParameter("id1", userId)
                .setParameter("id2", userId)
                .getResultList();
    }

    public UserMessage saveMessage(UserMessage message){
        return em.merge(message);
    }

}
