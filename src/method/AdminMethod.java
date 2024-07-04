package method;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminMethod implements AdminMethodInterface {
    @Override
    public void addBook(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        System.out.println("책 ID를 입력하세요: ");
        String bookId = scanner.next();
        System.out.println("카테고리를 입력하세요: ");
        String category = scanner.next();
        System.out.println("가격을 입력하세요: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리
        System.out.println("책 이름을 입력하세요: ");
        String bookName = scanner.nextLine();
        System.out.println("출판일을 입력하세요: ");
        String date = scanner.next();
        System.out.println("작가를 입력하세요: ");
        String writer = scanner.next();
        scanner.nextLine(); // 개행 문자 처리
        System.out.println("설명을 입력하세요: ");
        String description = scanner.nextLine();
        System.out.println("재고를 입력하세요: ");
        int stock = scanner.nextInt();

        String bookInfo = bookId + "," + category + "," + price + "," + bookName + "," + date + "," + writer + "," + description + "," + stock;
        out.write("ADDBOOK " + bookInfo + "\n");
        out.flush();

        String serverResponse = in.readLine(); // 서버 응답 받기
        System.out.println(serverResponse);

    }

    @Override
    public void updateBookStock(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        System.out.println("재고를 수정할 책 ID를 입력하세요: ");
        String bookId = scanner.next();
        System.out.println("새 재고 수량을 입력하세요: ");
        int newStock = scanner.nextInt();

        String stockInfo = bookId + "," + newStock;
        out.write("UPDATESTOCK " + stockInfo + "\n");
        out.flush();

        String serverResponse = in.readLine(); // 서버 응답 받기
        System.out.println(serverResponse);

    }

    public void deleteBook(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        System.out.println("삭제할 책의 ID를 입력하세요: ");
        String bookId = scanner.next();

        out.write(String.format("DELETEBOOK,%s\n", bookId));
        out.flush();

        // 서버 응답 읽기
        String serverResponse = in.readLine();
        System.out.println("서버 응답: " + serverResponse); // 디버깅용 로그
    }

    public void viewUsers(BufferedWriter out, BufferedReader in) throws IOException {
        out.write("GETUSERS\n");
        out.flush();

        System.out.println("\n회원 목록:");
        String serverResponse;
        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("END")) {
            System.out.println(parseUser(serverResponse));
        }
    }

    private String parseUser(String userString) {
        String[] attributes = userString.split(", ");
        if (attributes.length < 5) {
            return "잘못된 데이터 형식: " + userString;
        }

        try {
            String id = attributes[0].split("=")[1];
            String password = attributes[1].split("=")[1];
            String name = attributes[2].split("=")[1];
            String auth = attributes[3].split("=")[1];
            int cash = Integer.parseInt(attributes[4].split("=")[1]);
            return String.format("ID: %s, 이름: %s, 권한: %s, 캐시: %d", id, name, auth, cash);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            return "잘못된 데이터 형식: " + userString;
        }
    }
}
