package be.pxl.auctions.dao.impl;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class AuctionDaoImpl implements AuctionDao {
    private static final Logger LOGGER = LogManager.getLogger(AuctionDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Auction saveAuction(Auction auction) {
        LOGGER.info("Saving auction [" + auction.getDescription() + "]");
        entityManager.persist(auction);
        return auction;
    }

    public Auction findAuctionById(long auctionId) {
        return entityManager.find(Auction.class, auctionId);
    }

    public List<Auction> findAllAuctions() {
        TypedQuery<Auction> findAllQuery = entityManager.createNamedQuery("findAllAuctions", Auction.class);
        return findAllQuery.getResultList();
    }
}
