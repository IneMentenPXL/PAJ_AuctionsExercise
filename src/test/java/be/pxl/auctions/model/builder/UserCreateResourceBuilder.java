package be.pxl.auctions.model.builder;

import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.UserCreateResource;

import java.time.LocalDate;

public final class UserCreateResourceBuilder {
    public static final String FIRST_NAME = "Mark";
    public static final String LAST_NAME = "Zuckerberg";
    public static final String DATE_OF_BIRTH = "03/05/1983";
    public static final String EMAIL = "mark@facebook.com";

    private String firstName = FIRST_NAME;
    private String lastName = LAST_NAME;
    private String email = EMAIL;
    private String dateOfBirth = DATE_OF_BIRTH;

    private UserCreateResourceBuilder() {
    }

    public static UserCreateResourceBuilder aUserCreateResource() {
        return new UserCreateResourceBuilder();
    }

    public UserCreateResourceBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserCreateResourceBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserCreateResourceBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCreateResourceBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserCreateResource build() {
        UserCreateResource userCreateResource = new UserCreateResource();
        userCreateResource.setDateOfBirth(dateOfBirth);
        userCreateResource.setEmail(email);
        userCreateResource.setFirstName(firstName);
        userCreateResource.setLastName(lastName);
        return userCreateResource;
    }
}
