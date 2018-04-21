package com.restroplus.model;

import com.restroplus.model.Item;
import com.restroplus.model.Restaurant;

import java.util.Date;
import java.util.List;



public class Booking {

	private Integer bookingId;
	private String tableCategory;
	private List<Item> orderedItems;
	private String userEmail;
	private Double billingAmount;
	private Integer numberOfTablesBooked;
	private Integer restaurantId;
	private Restaurant restaurant;
	private String bookingDate;
	private String bookedByUserId;




	/**
	 * @return the bookedByUserId
	 */
	public String getBookedByUserId() {
		return bookedByUserId;
	}
	/**
	 * @param bookedByUserId the bookedByUserId to set
	 */
	public void setBookedByUserId(String bookedByUserId) {
		this.bookedByUserId = bookedByUserId;
	}


	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}
	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	/**
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the tableCategory
	 */
	public String getTableCategory() {
		return tableCategory;
	}
	/**
	 * @param tableCategory the tableCategory to set
	 */
	public void setTableCategory(String tableCategory) {
		this.tableCategory = tableCategory;
	}
	/**
	 * @return the orderedItems
	 */
	public List<Item> getOrderedItems() {
		return orderedItems;
	}
	/**
	 * @param orderedItems the orderedItems to set
	 */
	public void setOrderedItems(List<Item> orderedItems) {
		this.orderedItems = orderedItems;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * @return the billingAmount
	 */
	public Double getBillingAmount() {
		return billingAmount;
	}
	/**
	 * @param billingAmount the billingAmount to set
	 */
	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}
	/**
	 * @return the numberOfTablesBooked
	 */
	public Integer getNumberOfTablesBooked() {
		return numberOfTablesBooked;
	}
	/**
	 * @param numberOfTablesBooked the numberOfTablesBooked to set
	 */
	public void setNumberOfTablesBooked(Integer numberOfTablesBooked) {
		this.numberOfTablesBooked = numberOfTablesBooked;
	}
	/**
	 * @return the restaurantId
	 */
	public Integer getRestaurantId() {
		return restaurantId;
	}
	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingAmount == null) ? 0 : billingAmount.hashCode());
		result = prime * result + ((bookedByUserId == null) ? 0 : bookedByUserId.hashCode());
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((bookingId == null) ? 0 : bookingId.hashCode());
		result = prime * result + ((numberOfTablesBooked == null) ? 0 : numberOfTablesBooked.hashCode());
		result = prime * result + ((orderedItems == null) ? 0 : orderedItems.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		result = prime * result + ((tableCategory == null) ? 0 : tableCategory.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (billingAmount == null) {
			if (other.billingAmount != null)
				return false;
		} else if (!billingAmount.equals(other.billingAmount))
			return false;
		if (bookedByUserId == null) {
			if (other.bookedByUserId != null)
				return false;
		} else if (!bookedByUserId.equals(other.bookedByUserId))
			return false;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (bookingId == null) {
			if (other.bookingId != null)
				return false;
		} else if (!bookingId.equals(other.bookingId))
			return false;
		if (numberOfTablesBooked == null) {
			if (other.numberOfTablesBooked != null)
				return false;
		} else if (!numberOfTablesBooked.equals(other.numberOfTablesBooked))
			return false;
		if (orderedItems == null) {
			if (other.orderedItems != null)
				return false;
		} else if (!orderedItems.equals(other.orderedItems))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (tableCategory == null) {
			if (other.tableCategory != null)
				return false;
		} else if (!tableCategory.equals(other.tableCategory))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", tableCategory=" + tableCategory + ", orderedItems=" + orderedItems
				+ ", userEmail=" + userEmail + ", billingAmount=" + billingAmount + ", numberOfTablesBooked="
				+ numberOfTablesBooked + ", restaurant=" + restaurant + ", bookingDate=" + bookingDate
				+ ", bookedByUserId=" + bookedByUserId + "]";
	}




}
