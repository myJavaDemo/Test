package www.java.web.beans;

import java.io.Serializable;

import com.java.domain.Book;

public class CartItem implements Serializable {

	private Book book;
	public CartItem(Book book){
		this.book=book;
	}
	private int quantity;
	private float totalPrice;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotalPrice() {
		return book.getPrice()*quantity;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
