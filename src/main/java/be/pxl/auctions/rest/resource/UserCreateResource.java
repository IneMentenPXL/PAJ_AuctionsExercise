package be.pxl.auctions.rest.resource;

import be.pxl.auctions.util.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserCreateResource {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirthAsString() {
        return dateOfBirth;
    }

    public LocalDate getDateOfBirth() throws InvalidDateException {
        try {
            return LocalDate.parse(dateOfBirth, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("[" + dateOfBirth + "] is not a valid date. Excepted format: dd/mm/yyyy");
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
