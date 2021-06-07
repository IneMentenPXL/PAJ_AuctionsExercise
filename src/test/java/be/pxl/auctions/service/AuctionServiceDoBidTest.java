package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.model.builder.AuctionBuilder;
import be.pxl.auctions.model.builder.BidCreateResourceBuilder;
import be.pxl.auctions.model.builder.UserBuilder;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionResource;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.util.exception.InvalidBidException;
import be.pxl.auctions.util.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceDoBidTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Mock
    private UserDao userDao;

    @Mock
    private AuctionDao auctionDao;

    @InjectMocks
    private AuctionService auctionService;
    private BidCreateResource bidCreateResource;
    private AuctionResource auctionResource;
    private Auction auction;

    @Captor
    private ArgumentCaptor<Bid> bidArgumentCaptor;

    @BeforeEach
    public void init() {
        bidCreateResource = BidCreateResourceBuilder.aBidCreateResource().build();
        AuctionCreateResource auctionCreateResource = new AuctionCreateResource();
        auctionCreateResource.setDescription("Description");
        auctionCreateResource.setEndDate(LocalDate.now().plusDays(1).format(FORMATTER));
        auctionResource = auctionService.createAuction(auctionCreateResource);
        auction = AuctionBuilder.anAuction().withId(1L).withDescription("Description").withEndDate(LocalDate.now().plusDays(1)).build();
    }

    @Test
    public void throwsExceptionWhenUserNotFound() {
        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(auction);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> auctionService.doBid(auctionResource.getId(), bidCreateResource));
    }

    @Test
    public void throwsExceptionWhenAuctionNotFound() {
        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(null);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidBidException.class, () -> auctionService.doBid(auctionResource.getId(), bidCreateResource));
    }

    @Test
    public void throwsExceptionWhenPriceToLow() {
        Bid bid = new Bid();
        bid.setAmount(25);
        bid.setAuction(auction);
        auction.addBid(bid);

        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(auction);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidBidException.class, () -> auctionService.doBid(auctionResource.getId(), bidCreateResource));
    }

    @Test
    public void throwsExceptionWhenUserHasHighestBid() {
        User user = UserBuilder.aUser().build();

        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(auction);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.of(user));
        auctionService.doBid(auctionResource.getId(), bidCreateResource);
        assertThrows(InvalidBidException.class, () -> auctionService.doBid(auctionResource.getId(), bidCreateResource));
    }

    @Test
    public void throwsExceptionWhenAuctionIsFinished() {
        auction.setEndDate(LocalDate.now());

        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(auction);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidBidException.class, () -> auctionService.doBid(auctionResource.getId(), bidCreateResource));
    }

    @Test
    public void addsBidWhenNoExceptionIsThrown() {
        auction = mock(Auction.class);
        User user = UserBuilder.aUser().withEmail(bidCreateResource.getEmail()).build();
        Bid bid = new Bid(user, LocalDate.now(), bidCreateResource.getPrice());

        when(auctionDao.findAuctionById(auctionResource.getId())).thenReturn(auction);
        when(userDao.findUserByEmail(bidCreateResource.getEmail())).thenReturn(Optional.of(user));
        auctionService.doBid(auctionResource.getId(), bidCreateResource);
        verify(auction, times(1)).addBid(bidArgumentCaptor.capture());
        assertEquals(bid.getAmount(), bidCreateResource.getPrice());
        assertEquals(bid.getUser().getEmail(), bidCreateResource.getEmail());
    }
}
