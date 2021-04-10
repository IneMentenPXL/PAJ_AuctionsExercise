package be.pxl.auctions.model;

import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.dao.impl.UserDaoImpl;
import be.pxl.auctions.rest.resource.UserDTO;
import be.pxl.auctions.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserGetAgeTest {

    private static final long USER_ID = 5L;
    private static final String MAIL = "mark@facebook.com";

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private User user;


    @BeforeEach
    public void init() {
        user = new User();
        user.setId(USER_ID);
        user.setFirstName("Mark");
        user.setLastName("Zuckerberg");
        user.setEmail(MAIL);
    }

    @Test
    public void returnsCorrectAgeWhenHavingBirthdayToday() {
        user.setDateOfBirth(LocalDate.now().withYear(2000));
        when(userDao.findUserById(USER_ID)).thenReturn(Optional.of(user));
        UserDTO userById = userService.getUserById(USER_ID);
        assertEquals(21, userById.getAge());
    }

    @Test
    public void returnsCorrectAgeWhenHavingBirthdayTomorrow() {
        user.setDateOfBirth(LocalDate.now().plusDays(1).withYear(2000));
        when(userDao.findUserById(USER_ID)).thenReturn(Optional.of(user));
        UserDTO userById = userService.getUserById(USER_ID);
        assertEquals(20, user.getAge());
    }

    @Test
    public void returnsCorrectAgeWhenBirthdayWasYesterday() {
        user.setDateOfBirth(LocalDate.now().minusDays(1).withYear(2000));
        when(userDao.findUserById(USER_ID)).thenReturn(Optional.of(user));
        UserDTO userById = userService.getUserById(USER_ID);
        assertEquals(21, user.getAge());
    }

}
