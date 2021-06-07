package be.pxl.auctions.rest.resource;

import java.time.LocalDate;

public class AuctionResource {
	private Long id;
	private String description;
	private LocalDate endDate;
	private boolean finished;
	private int numberOfBids;
	private double highestBid;
	private String highestBidBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	public double getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(double highestBid) {
		this.highestBid = highestBid;
	}

	public String getHighestBidBy() {
		return highestBidBy;
	}

	public void setHighestBidBy(String highestBidBy) {
		this.highestBidBy = highestBidBy;
	}
}
