package be.pxl.auctions.service;

import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.UserCreateResource;
import be.pxl.auctions.rest.resource.UserResource;
import be.pxl.auctions.util.EmailValidator;
import be.pxl.auctions.util.exception.DuplicateEmailException;
import be.pxl.auctions.util.exception.InvalidDateException;
import be.pxl.auctions.util.exception.InvalidEmailException;
import be.pxl.auctions.util.exception.RequiredFieldException;
import be.pxl.auctions.util.exception.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

	@Autowired
	private UserDao userDao;

	public List<UserResource> getAllUsers() {
		return userDao.findAllUsers().stream().map(this::mapToUserResource).collect(Collectors.toList());
	}

	public UserResource getUserById(long userId) {
		return userDao.findUserById(userId).map(this::mapToUserResource).orElseThrow(()  -> new UserNotFoundException("Unable to find User with id [" + userId + "]"));
	}

	public UserResource createUser(UserCreateResource userInfo) throws RequiredFieldException, InvalidEmailException, DuplicateEmailException, InvalidDateException {
		if (StringUtils.isBlank(userInfo.getFirstName())) {
			throw new RequiredFieldException("FirstName");
		}
		if (StringUtils.isBlank(userInfo.getLastName())) {
			throw new RequiredFieldException("LastName");
		}
		if (StringUtils.isBlank(userInfo.getEmail())) {
			throw new RequiredFieldException("Email");
		}
		if (!EmailValidator.isValid(userInfo.getEmail())) {
			throw new InvalidEmailException(userInfo.getEmail());
		}
		if (userInfo.getDateOfBirth() == null) {
			throw new RequiredFieldException("DateOfBirth");
		}
		Optional<User> existingUser = userDao.findUserByEmail(userInfo.getEmail());
		if (existingUser.isPresent()) {
			throw new DuplicateEmailException(userInfo.getEmail());
		}
		User user = mapToUser(userInfo);
		if (user.getDateOfBirth().isAfter(LocalDate.now())) {
			throw new InvalidDateException("DateOfBirth cannot be in the future.");
		}
		return mapToUserResource(userDao.saveUser(user));
	}

	private UserResource mapToUserResource(User user) {
		UserResource userResource = new UserResource();
		userResource.setId(user.getId());
		userResource.setFirstName(user.getFirstName());
		userResource.setLastName(user.getLastName());
		userResource.setDateOfBirth(user.getDateOfBirth());
		userResource.setAge(user.getAge());
		userResource.setEmail(user.getEmail());
		return userResource;
	}

	private User mapToUser(UserCreateResource userCreateResource) throws InvalidDateException {
		User user = new User();
		user.setFirstName(userCreateResource.getFirstName());
		user.setLastName(userCreateResource.getLastName());
		try {
			user.setDateOfBirth(userCreateResource.getDateOfBirth());
		} catch (DateTimeParseException e) {
			throw new InvalidDateException("[" + user.getDateOfBirth() + "] is not a valid date. Excepted format: dd/mm/yyyy");
		}
		user.setEmail(userCreateResource.getEmail());
		return user;
	}

}
