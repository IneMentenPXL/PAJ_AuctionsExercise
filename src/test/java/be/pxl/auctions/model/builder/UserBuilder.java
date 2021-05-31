package be.pxl.auctions.model.builder;

import be.pxl.auctions.model.User;

import java.time.LocalDate;

public final class UserBuilder {
    public static final String FIRST_NAME = "Mark";
    public static final String LAST_NAME = "Zuckerberg";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1989, 5, 3);
    public static final String EMAIL = "mark@facebook.com";

    private long id;
    private String firstName = FIRST_NAME;
    private String lastName = LAST_NAME;
    private String email = EMAIL;
    private LocalDate dateOfBirth = DATE_OF_BIRTH;

    private UserBuilder() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
