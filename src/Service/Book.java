package Service;

import java.io.*;
import java.util.Vector;
import Object.BookObject;

public class Book {

    public static Vector<BookObject> loadBooksFromFile(String filePath) {
        Vector<BookObject> bookVector = new Vector<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                String bookId = tokens[0].split("=")[1];
                String category = tokens[1].split("=")[1];
                int price = Integer.parseInt(tokens[2].split("=")[1]);
                String bookName = tokens[3].split("=")[1];
                String date = tokens[4].split("=")[1];
                String writer = tokens[5].split("=")[1];
                String description = tokens[6].split("=")[1];
                int stock = Integer.parseInt(tokens[7].split("=")[1]);

                BookObject book = new BookObject(bookId, category, price, bookName, date, writer, description, stock);
                bookVector.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookVector;
    }

    public static void saveBookToFile(BookObject book, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // append 모드로 열기
            writer.write("bookId=" + book.getBookId() + ", category=" + book.getCategory() + ", price=" + book.getPrice() +
                    ", bookName=" + book.getBookName() + ", date=" + book.getDate() + ", writer=" + book.getWriter() +
                    ", description=" + book.getDescription() + ", stock=" + book.getStock() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BookObject getBookObject(String clientMessage) {
        String[] tokens = clientMessage.substring(8).split(",");
        String bookId = tokens[0].trim();
        String category = tokens[1].trim();
        int price = Integer.parseInt(tokens[2].trim());
        String bookName = tokens[3].trim();
        String date = tokens[4].trim();
        String writer = tokens[5].trim();
        String description = tokens[6].trim();
        int stock = Integer.parseInt(tokens[7].trim());

        return new BookObject(bookId, category, price, bookName, date, writer, description, stock);
    }

    public static void saveAllBooksToFile(Vector<BookObject> bookVector, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BookObject book : bookVector) {
                writer.write("bookId=" + book.getBookId() + ", category=" + book.getCategory() + ", price=" + book.getPrice() +
                        ", bookName=" + book.getBookName() + ", date=" + book.getDate() + ", writer=" + book.getWriter() +
                        ", description=" + book.getDescription() + ", stock=" + book.getStock() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
