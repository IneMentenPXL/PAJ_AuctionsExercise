package be.pxl.auctions.model;

import be.pxl.auctions.model.builder.AuctionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuctionFindHighestBidTest {
    private Auction auction;
    private Bid bid1;
    private Bid bid2;

    @BeforeEach
    public void init() {
        auction = AuctionBuilder.anAuction().build();
        bid1 = new Bid();
        bid1.setAmount(100);
        bid2 = new Bid();
        bid2.setAmount(50);
    }

    @Test
    public void returnsHighestBidWhenAuctionHasBids() {
        bid1.setAuction(auction);
        bid2.setAuction(auction);
        auction.addBid(bid1);
        auction.addBid(bid2);

        Bid highestBid = auction.findHighestBid();
        assertNotNull(highestBid);
        assertEquals(bid1, highestBid);
        assertEquals(100, highestBid.getAmount());
    }

    @Test
    public void returnsNullWhenAuctionHasNoBids() {
        assertNull(auction.findHighestBid());
    }
}