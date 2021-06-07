package be.pxl.auctions.model.builder;

import be.pxl.auctions.rest.resource.BidCreateResource;

public final class BidCreateResourceBuilder {
    public static final String EMAIL = "mark@facebook.com";
    public static final double PRICE = 20.5;

    private String email = EMAIL;
    private double price = PRICE;

    private BidCreateResourceBuilder() {
    }

    public static BidCreateResourceBuilder aBidCreateResource() {
        return new BidCreateResourceBuilder();
    }

    public BidCreateResourceBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public BidCreateResourceBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public BidCreateResource build() {
        BidCreateResource bidCreateResource = new BidCreateResource();
        bidCreateResource.setPrice(price);
        bidCreateResource.setEmail(email);
        return bidCreateResource;
    }
}
