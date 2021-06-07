package be.pxl.auctions.dao.impl;

import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.User;
import be.pxl.auctions.model.builder.AuctionBuilder;
import be.pxl.auctions.model.builder.UserBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class AuctionDaoImplTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private AuctionDaoImpl auctionDao;

	@Test
	public void auctionCanBeSavedAndRetrievedById() {
		Auction auction = AuctionBuilder.anAuction().withDescription("Description").withEndDate(LocalDate.of(2001, 2, 2)).build();
		long newAuctionId = auctionDao.saveAuction(auction).getId();
		entityManager.flush();
		entityManager.clear();

		Auction retrievedAuction = auctionDao.findAuctionById(newAuctionId);

		assertEquals(auction.getId(), retrievedAuction.getId());
		assertEquals(auction.getDescription(), retrievedAuction.getDescription());
		assertEquals(auction.getEndDate(), retrievedAuction.getEndDate());
	}

	@Test
	public void returnsNullWhenNoAuctionFoundWithGivenId() {
		Auction retrievedAuction = auctionDao.findAuctionById(Mockito.anyInt());
		assertNull(retrievedAuction);
	}

	@Test
	public void allAuctionsCanBeRetrieved() {
		int initialNumberOfAuctions = auctionDao.findAllAuctions().size();

		Auction auction = AuctionBuilder.anAuction().withDescription("Description").withEndDate(LocalDate.of(2001, 2, 2)).build();
		long newAuctionId = auctionDao.saveAuction(auction).getId();
		entityManager.flush();
		entityManager.clear();

		List<Auction> allAuctions = auctionDao.findAllAuctions();
		assertEquals(initialNumberOfAuctions + 1, allAuctions.size());
		assertEquals(1, allAuctions.stream().filter(a -> a.getId() == newAuctionId).count());
	}
}
