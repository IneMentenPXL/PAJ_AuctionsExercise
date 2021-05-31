package be.pxl.auctions.model;

import be.pxl.auctions.model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserGetAgeTest {

    private User user;

    @BeforeEach
    public void init() {
        user = UserBuilder.aUser().build();
    }

    @Test
    public void returnsCorrectAgeWhenHavingBirthdayToday() {
        user.setDateOfBirth(LocalDate.now().withYear(2000));
        assertEquals(21, user.getAge());
    }

    @Test
    public void returnsCorrectAgeWhenHavingBirthdayTomorrow() {
        user.setDateOfBirth(LocalDate.now().plusDays(1).withYear(2000));
        assertEquals(20, user.getAge());
    }

    @Test
    public void returnsCorrectAgeWhenBirthdayWasYesterday() {
        user.setDateOfBirth(LocalDate.now().minusDays(1).withYear(2000));
        assertEquals(21, user.getAge());
    }
}
