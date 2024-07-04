package cart;

import bookitem.Book;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Book, Integer> cartItems;

    public Cart() {
        cartItems = new HashMap<>();
    }

    public void addBook(Book book, int quantity) {
        cartItems.put(book, cartItems.getOrDefault(book, 0) + quantity);
    }

    public void removeBook(Book book) {
        cartItems.remove(book);
    }

    public Map<Book, Integer> getItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
