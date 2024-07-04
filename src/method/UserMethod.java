package method;

import bookitem.Book;
import cart.Cart;
import ui.Login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class UserMethod implements UserMethodInterface {

    private Cart cart = new Cart(); // 장바구니 객체 추가
    @Override
    public void requestUserInfo(BufferedWriter out, BufferedReader in) throws IOException {
        if (Login.loggedInUserId != null) {
            out.write("USERINFO " + Login.loggedInUserId + "\n");
            out.flush();

            // 서버 응답 읽기
            String serverResponse;
            while ((serverResponse = in.readLine()) != null && !serverResponse.equals("END")) {
                System.out.println(serverResponse);
            }
        } else {
            System.out.println("로그인되지 않았습니다.");
        }
    }

    public void viewBooks(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        out.write("GETBOOKS\n");
        out.flush();

        Vector<Book> booksList = new Vector<>();
        String serverResponse;
        while (!(serverResponse = in.readLine()).equals("END")) {
            booksList.add(parseBook(serverResponse));
        }

        for (Book book : booksList) {
            System.out.println(createBookBox(book));
        }

        // 책 구매 로직 추가
        System.out.println("구매할 책의 ID를 입력하세요: ");
        String bookId = scanner.next();
        System.out.println("구매할 수량을 입력하세요: ");
        int quantity = scanner.nextInt();

        Book selectedBook = null;
        for (Book book : booksList) {
            if (book.getBookId().equals(bookId)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook != null && selectedBook.getStock() >= quantity) {
            cart.addBook(selectedBook, quantity);
            selectedBook.setStock(selectedBook.getStock() - quantity);
            updateStock(out, in, selectedBook);

            // 장바구니에 담긴 정보를 서버에 전송
            out.write(String.format("ADDTOCART,%s,%s,%s,%d,%d\n", Login.loggedInUserId, selectedBook.getBookId(), selectedBook.getBookName(), selectedBook.getPrice(), quantity));
            out.flush();
            System.out.println("책이 장바구니에 추가되었습니다.");
        } else {
            System.out.println("재고가 부족하거나 책 ID가 잘못되었습니다.");
        }
    }

    private void updateStock(BufferedWriter out,BufferedReader in, Book book) throws IOException {
        String updateStockCommand = String.format("BUYUPDATESTOCK,%s,%d\n", book.getBookId(), book.getStock());
        out.write(updateStockCommand);
        out.flush();

        String serverResponse = in.readLine();
        System.out.println("서버 응답: " + serverResponse); // 디버깅용 로그
    }

    private Book parseBook(String bookString) {
        try {
            String[] attributes = bookString.split(",");
            if (attributes.length != 8) {
                throw new IllegalArgumentException("Invalid book data: " + bookString);
            }
            String bookId = attributes[0].trim();
            String category = attributes[1].trim();
            int price = Integer.parseInt(attributes[2].trim());
            String bookName = attributes[3].trim();
            String date = attributes[4].trim();
            String writer = attributes[5].trim();
            String description = attributes[6].trim();
            int stock = Integer.parseInt(attributes[7].trim());

            return new Book(bookId, category, price, bookName, date, writer, description, stock);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + bookString);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing book data (array index out of bounds): " + bookString);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error parsing book data: " + bookString);
            e.printStackTrace();
        }
        return null;
    }

    private String createBookBox(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n+-------------------------------+\n");
        sb.append(String.format("| %-30s |\n", "책 ID: " + book.getBookId()));
        sb.append(String.format("| %-30s |\n", "카테고리: " + book.getCategory()));
        sb.append(String.format("| %-30s |\n", "가격: " + book.getPrice()));
        sb.append(String.format("| %-30s |\n", "책 이름: " + book.getBookName()));
        sb.append(String.format("| %-30s |\n", "출판일: " + book.getDate()));
        sb.append(String.format("| %-30s |\n", "작가: " + book.getWriter()));
        sb.append(String.format("| %-30s |\n", "설명: " + book.getDescription()));
        sb.append(String.format("| %-30s |\n", "재고: " + book.getStock()));
        sb.append("+-------------------------------+\n");
        return sb.toString();
    }

    public void viewCart(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        if (Login.loggedInUserId != null) {
            out.write("CARTINFO " + Login.loggedInUserId + "\n");
            out.flush();

            System.out.println("\n장바구니 내용:");
            String serverResponse;
            cart.clearCart(); // 기존 장바구니 내용을 지웁니다.
            while ((serverResponse = in.readLine()) != null && !serverResponse.equals("END")) {
                System.out.println(parseCartItem(serverResponse));
                addCartItemToCart(serverResponse); // 서버에서 가져온 장바구니 항목을 cart 객체에 추가합니다.
            }

            // 구매할지 묻는 부분 추가
            System.out.println("\n1. 장바구니 물품 구매");
            System.out.println("2. 메인 메뉴로 돌아가기");
            System.out.print("선택하세요: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                purchaseCart(out, in);
            } else {
                System.out.println("메인 메뉴로 돌아갑니다.");
            }
        } else {
            System.out.println("로그인되지 않았습니다.");
        }
    }

    private void addCartItemToCart(String cartItemString) {
        String[] attributes = cartItemString.split(", ");
        String bookId = attributes[0].split("=")[1];
        String bookName = attributes[1].split("=")[1];
        int price = Integer.parseInt(attributes[2].split("=")[1]);
        int quantity = Integer.parseInt(attributes[3].split("=")[1]);
        int totalPrice = Integer.parseInt(attributes[4].split("=")[1]);

        Book book = new Book(bookId, "", price, bookName, "", "", "", 0);
        cart.addBook(book, quantity);
    }

    private String parseCartItem(String cartItemString) {
        String[] attributes = cartItemString.split(", ");

        try {
            String bookId = attributes[0].split("=")[1];
            String bookName = attributes[1].split("=")[1];
            int price = Integer.parseInt(attributes[2].split("=")[1]);
            int quantity = Integer.parseInt(attributes[3].split("=")[1]);
            int totalPrice = Integer.parseInt(attributes[4].split("=")[1]);
            return String.format("책 ID: %s, 책 제목: %s, 개당 가격: %d, 수량: %d, 총 가격: %d", bookId, bookName, price, quantity, totalPrice);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            return "잘못된 데이터 형식: " + cartItemString;
        }
    }


    // 장바구니 구매 메서드 추가
    public void purchaseCart(BufferedWriter out, BufferedReader in) throws IOException {
        int totalCost = 0;
        System.out.println("장바구니 내용:");
        for (Map.Entry<Book, Integer> entry : cart.getItems().entrySet()) {
            Book book = entry.getKey();
            int price = book.getPrice();
            int quantity = entry.getValue();
            int cost = price * quantity;

            System.out.printf("책: %s, 가격: %d, 수량: %d, 비용: %d\n", book.getBookName(), price, quantity, cost);
            totalCost += cost;
        }

        System.out.printf("총 비용: %d\n", totalCost);

        // 총 가격을 포함한 구매 요청을 서버로 전송
        out.write(String.format("PURCHASE,%s,%d\n", Login.loggedInUserId, totalCost));
        out.flush();

        // 서버 응답 읽기
        String serverResponse = in.readLine();
        System.out.println("서버 응답: " + serverResponse); // 디버깅용 로그

        if ("구매가 완료되었습니다.".equals(serverResponse)) {
            cart.clearCart();
            System.out.println("구매가 완료되었습니다.");
        } else {
            System.out.println("구매에 실패했습니다: " + serverResponse);
        }
    }

    public void chargeCash(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        if (Login.loggedInUserId != null) {
            System.out.println("충전할 캐시 금액을 입력하세요: ");
            int chargeAmount = scanner.nextInt();

            out.write(String.format("CHARGECASH,%s,%d\n", Login.loggedInUserId, chargeAmount));
            out.flush();

            String serverResponse = in.readLine();
            System.out.println(serverResponse);
        } else {
            System.out.println("로그인되지 않았습니다.");
        }
    }
}
