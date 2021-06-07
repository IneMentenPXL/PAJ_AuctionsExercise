package be.pxl.auctions.dao.impl;

import be.pxl.auctions.model.User;
import be.pxl.auctions.model.builder.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class UserDaoImplTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserDaoImpl userDao;

	@Test
	public void userCanBeSavedAndRetrievedById() {
		User user = UserBuilder.aUser().build();
		long newUserId = userDao.saveUser(user).getId();
		entityManager.flush();
		entityManager.clear();

		Optional<User> retrievedUser = userDao.findUserById(newUserId);
		assertTrue(retrievedUser.isPresent());

		assertEquals(UserBuilder.FIRST_NAME, retrievedUser.get().getFirstName());
		assertEquals(UserBuilder.LAST_NAME, retrievedUser.get().getLastName());
		assertEquals(UserBuilder.EMAIL, retrievedUser.get().getEmail());
		assertEquals(UserBuilder.DATE_OF_BIRTH, retrievedUser.get().getDateOfBirth());
	}

	@Test
	public void userCanBeSavedAndRetrievedByEmail() {
		User user = UserBuilder.aUser().withEmail("elly@facebook.com").build();
		String newUserEmail = userDao.saveUser(user).getEmail();
		entityManager.flush();
		entityManager.clear();

		Optional<User> retrievedUser = userDao.findUserByEmail(newUserEmail);
		assertTrue(retrievedUser.isPresent());

		assertEquals(user.getEmail(), retrievedUser.get().getEmail());
	}

	@Test
	public void returnsNullWhenNoUserFoundWithGivenEmail() {
		Optional<User> retrievedUser = userDao.findUserByEmail("mail");
		assertFalse(retrievedUser.isPresent());
	}

	@Test
	public void allUsersCanBeRetrieved() {
		int initialNumberOfUsers = userDao.findAllUsers().size();

		User user = UserBuilder.aUser().withEmail("emma@facebook.com").build();
		String newUserEmail = userDao.saveUser(user).getEmail();
		entityManager.flush();
		entityManager.clear();

		List<User> allUsers = userDao.findAllUsers();
		assertEquals(initialNumberOfUsers + 1, allUsers.size());
		assertEquals(1, allUsers.stream().filter(u -> u.getEmail().equals(newUserEmail)).count());
	}

	@Test
	public void emptyOptionalWhenNoUserFoundWithGivenEmail() {
		Optional<User> user = userDao.findUserByEmail("mickey@disney.com");

		assertTrue(user.isEmpty());
	}
}
