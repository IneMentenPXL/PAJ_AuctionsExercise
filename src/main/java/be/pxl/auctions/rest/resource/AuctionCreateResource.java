package be.pxl.auctions.rest.resource;

import be.pxl.auctions.util.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AuctionCreateResource {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

	private String description;
	private String endDate;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDateAsString() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public LocalDate getEndDate() throws InvalidDateException {
		try {
			return LocalDate.parse(endDate, DATE_FORMAT);
		} catch (
				DateTimeParseException e) {
			throw new InvalidDateException("[" + endDate + "] is not a valid date. Excepted format: dd/mm/yyyy");
		}
	}
}
