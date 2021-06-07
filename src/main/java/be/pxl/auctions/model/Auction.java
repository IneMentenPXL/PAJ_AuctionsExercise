package be.pxl.auctions.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Entity
@NamedQuery(name = "findAllAuctions", query = "SELECT a FROM Auction a")
public class Auction {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private LocalDate endDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Bid> bids = new ArrayList<>();

    public Auction() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public boolean isFinished() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(endDate);
    }

    public Bid findHighestBid() {
        if (getBids().isEmpty()) {
            return null;
        }
        double max = getBids().stream().mapToDouble(Bid::getAmount).max().getAsDouble();
        return getBids().stream().filter(bid -> bid.getAmount() == max).collect(Collectors.toList()).get(0);
    }
}
