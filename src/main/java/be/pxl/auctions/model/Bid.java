package be.pxl.auctions.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Bid {
    @Id
    @GeneratedValue
    private long id;
    private double amount;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.ALL)
    private Auction auction;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    public Bid() {
    }

    public Bid(User user, LocalDate date, double amount) {
        this.user = user;
        this.date = date;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }
}
