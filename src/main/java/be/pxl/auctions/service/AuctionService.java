package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionResource;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.util.exception.InvalidBidException;
import be.pxl.auctions.util.exception.RequiredFieldException;
import be.pxl.auctions.util.exception.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private UserDao userDao;

    public void doBid(Long auctionId, BidCreateResource bidCreateResource) {
        Auction auctionById = auctionDao.findAuctionById(auctionId);
        Optional<User> userByEmail = userDao.findUserByEmail(bidCreateResource.getEmail());
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException("Invalid user " + bidCreateResource.getEmail());
        }
        if (auctionById == null) {
            throw new InvalidBidException("Invalid auction " + auctionId);
        }

        Bid highestBid = auctionById.findHighestBid();
        if (highestBid != null) {
            if (bidCreateResource.getPrice() < highestBid.getAmount()) {
                throw new InvalidBidException("Price must be higher than " + highestBid.getAmount());
            }
            if (highestBid.getUser() == userByEmail.get()) {
                throw new InvalidBidException(userByEmail.get().getFirstName() + " " + userByEmail.get().getLastName() + " already has highest bid");
            }
        }
        if (auctionById.isFinished()) {
            throw new InvalidBidException("Auction is finished");
        }
        Bid bid = new Bid(userByEmail.get(), auctionById.getEndDate(), bidCreateResource.getPrice());
        auctionById.addBid(bid);
        auctionDao.saveAuction(auctionById);
    }

    public List<AuctionResource> findAuctions() {
        return auctionDao.findAllAuctions().stream().map(this::mapAuction).collect(Collectors.toList());
    }

    public AuctionResource createAuction(AuctionCreateResource auctionCreateResource) {
        if (StringUtils.isBlank(auctionCreateResource.getDescription())) {
            throw new RequiredFieldException("Description");
        }
        if (StringUtils.isBlank(auctionCreateResource.getEndDateAsString())) {
            throw new RequiredFieldException("EndDate");
        }

        Auction auction = new Auction();
        auction.setDescription(auctionCreateResource.getDescription());
        auction.setEndDate(auctionCreateResource.getEndDate());
        auctionDao.saveAuction(auction);
        return mapAuction(auction);
    }

    private AuctionResource mapAuction(Auction auction) {
        AuctionResource auctionResource = new AuctionResource();
        auctionResource.setId(auction.getId());
        auctionResource.setDescription(auction.getDescription());
        auctionResource.setEndDate(auction.getEndDate());
        return auctionResource;
    }
}
