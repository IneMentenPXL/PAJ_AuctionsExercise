package be.pxl.auctions.dao;

import be.pxl.auctions.model.Auction;

import java.util.List;

public interface AuctionDao {
    Auction saveAuction(Auction auction);
    Auction findAuctionById(long auction);
    List<Auction> findAllAuctions();
}
