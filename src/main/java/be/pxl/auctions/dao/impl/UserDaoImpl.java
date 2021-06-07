package be.pxl.auctions.dao.impl;

import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User saveUser(User user) {
        LOGGER.info("Saving user [" + user.getEmail() + "]");
        entityManager.persist(user);
        return user;
    }

    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> userQuery = entityManager.createNamedQuery("findUserByEmail", User.class);
        userQuery.setParameter("email", email);
        try {
            return Optional.of(userQuery.getSingleResult());
        } catch (NoResultException e) {
            LOGGER.info("No user found with email [" + email + "]");
            return Optional.empty();
        }
    }

    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    public List<User> findAllUsers() {
        TypedQuery<User> findAllQuery = entityManager.createNamedQuery("findAllUsers", User.class);
        return findAllQuery.getResultList();
    }
}
