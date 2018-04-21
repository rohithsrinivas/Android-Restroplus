package com.restroplus.model;


public class Item {

	private String itemName;
	private Double price;
	private String itemCategory;
	private Integer quantity;
	private boolean inCart;
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the itemCategory
	 */
	public String getItemCategory() {
		return itemCategory;
	}
	/**
	 * @param itemCategory the itemCategory to set
	 */
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isInCart() {
		return inCart;
	}

	public void setInCart(boolean inCart) {
		this.inCart = inCart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Item item = (Item) o;

		if (inCart != item.inCart) return false;
		if (itemName != null ? !itemName.equals(item.itemName) : item.itemName != null)
			return false;
		if (price != null ? !price.equals(item.price) : item.price != null) return false;
		if (itemCategory != null ? !itemCategory.equals(item.itemCategory) : item.itemCategory != null)
			return false;
		return quantity != null ? quantity.equals(item.quantity) : item.quantity == null;
	}

	@Override
	public int hashCode() {
		int result = itemName != null ? itemName.hashCode() : 0;
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (itemCategory != null ? itemCategory.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (inCart ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Item{" +
				"itemName='" + itemName + '\'' +
				", price=" + price +
				", itemCategory='" + itemCategory + '\'' +
				", quantity=" + quantity +
				", inCart=" + inCart +
				'}';
	}
}
