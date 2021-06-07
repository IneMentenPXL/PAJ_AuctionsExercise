package be.pxl.auctions.rest;


import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionResource;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.service.AuctionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/auctions")
public class AuctionRest {
    private static final Logger LOGGER = LogManager.getLogger(AuctionRest.class);

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public List<AuctionResource> getAllAuctions() {
        return auctionService.findAuctions();
    }

    @PostMapping
    public void createAuction(@RequestBody AuctionCreateResource auctionCreateResource) {
        auctionService.createAuction(auctionCreateResource);
    }

    @PostMapping("{auctionId}/bids")
    public void createBid(@PathVariable("auctionId") long auctionId, @RequestBody BidCreateResource bidCreateResource) {
        auctionService.doBid(auctionId, bidCreateResource);
    }
}
