package be.pxl.auctions.model;

import be.pxl.auctions.model.builder.AuctionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AuctionIsFinishedTest {
    private Auction auction;

    @BeforeEach
    public void init() {
        auction = AuctionBuilder.anAuction().build();
    }

    @Test
    public void returnsFalseWhenAuctionIsNotFinished() {
        auction.setEndDate(LocalDate.now().plusDays(1));
        assertFalse(auction.isFinished());
    }

    @Test
    public void returnsTrueWhenAuctionIsFinished() {
        auction.setEndDate(LocalDate.now());
        assertTrue(auction.isFinished());
    }
}
