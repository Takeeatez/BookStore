import Object.BookObject;

import java.io.*;
import java.net.*;
import Object.Person;
import Service.Book;
import Service.Login;
import Service.Register;
import Service.User;

import java.util.Vector;

import static Service.Book.*;
import static Service.Login.loadPersonsFromFile;
import static Service.User.clearUserCart;
import static Service.User.saveAllPersonsToFile;

public class Main {
    public static void main(String[] args) {
        Vector<Person> personVector = loadPersonsFromFile("src/Data/person.txt");
        Vector<BookObject> bookObjectVector = loadBooksFromFile("src/Data/book.txt"); // 서버 시작 시 책 정보 로드
        System.out.println(bookObjectVector);
        BufferedReader in = null; // 입력 리더
        BufferedWriter out = null; // 출력 리더
        ServerSocket listener = null; // 서버 대기 socket 개체
        Socket socket = null; // 연결 처리 socket 개체

        try {
            listener = new ServerSocket(5000);
            System.out.println("서버 연결 대기 시작 ....... ");
            socket = listener.accept();
            System.out.println("연결됨.");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 클라이언트 요청을 처리하는 루프
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("클라이언트로부터 받은 메시지: " + clientMessage);
                if (clientMessage.startsWith("CHECKID")) {
                    String id = clientMessage.substring(8).trim();

                    if (Register.isIdDuplicated(id, personVector)) {
                        out.write("아이디가 중복됩니다.\n");
                        out.flush();
                    } else {
                        out.write("사용 가능한 아이디입니다.\n");
                        out.flush();
                    }
                }

                if (clientMessage.startsWith("REGISTER")) {
                    String[] tokens = clientMessage.substring(9).split(",");
                    String id = tokens[0].trim();
                    String password = tokens[1].trim();
                    String name = tokens[2].trim();
                    String auth = tokens[3].trim();

                    Person newPerson = new Person(id, password, name, auth,0);
                    personVector.add(newPerson);
                    Register.savePersonToFile(newPerson, "src/Data/person.txt");

                    out.write("회원가입이 완료되었습니다.\n");
                    out.flush();
                }

                if (clientMessage.startsWith("LOGIN")) {
                    String[] tokens = clientMessage.substring(6).split(",");
                    String id = tokens[0].trim();
                    String password = tokens[1].trim();
                    String authType = tokens[2].trim(); // 로그인 유형 추가

                    boolean loginSuccess = Login.checkLogin(id, password, authType, personVector);

                    if (loginSuccess) {
                        out.write("로그인 성공\n");
                    } else {
                        out.write("로그인 실패\n");
                    }
                    out.flush();
                }
                if (clientMessage.startsWith("USERINFO")) {
                    String id = clientMessage.substring(8).trim();
                    Person person = User.getPersonInfo(id, personVector);
                    if (person != null) {
                        out.write("ID: " + person.getId() + "\n");
                        out.write("이름: " + person.getName() + "\n");
                        out.write("권한: " + person.getAuth() + "\n");
                        out.write("캐시: " + person.getCash() + "\n");
                    } else {
                        out.write("사용자 정보를 찾을 수 없습니다.\n");
                    }
                    out.write("END\n");
                    out.flush();
                }

                if (clientMessage.startsWith("ADDBOOK")) {
                    BookObject newBookObject = getBookObject(clientMessage);
                    System.out.println(newBookObject);
                    bookObjectVector.add(newBookObject);
                    Book.saveBookToFile(newBookObject, "src/Data/book.txt");

                    out.write("책 등록이 완료되었습니다.\n");
                    out.flush();
                }
                if (clientMessage.startsWith("UPDATESTOCK")) {
                    String[] tokens = clientMessage.substring(12).split(",");
                    String bookId = tokens[0].trim();
                    int newStock = Integer.parseInt(tokens[1].trim());

                    boolean found = false;
                    for (BookObject book : bookObjectVector) {
                        if (book.getBookId().equals(bookId)) {
                            book.setStock(newStock);
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        saveAllBooksToFile(bookObjectVector, "src/Data/book.txt");
                        out.write("재고 수정이 완료되었습니다.\n");
                    } else {
                        out.write("책을 찾을 수 없습니다.\n");
                    }
                    out.flush();

                }
                if (clientMessage.startsWith("GETBOOKS")) {
                    for (BookObject book : bookObjectVector) {
                        String bookData = book.toString();
                        System.out.println("Sending book data: " + bookData); // 디버깅 출력 추가
                        out.write(bookData + "\n");
                    }
                    out.write("END\n"); // 책 목록의 끝을 알리는 마커
                    out.flush();
                }
                if (clientMessage.startsWith("BUYUPDATESTOCK")) {
                    String[] tokens = clientMessage.split(",");
                    String bookId = tokens[1].trim();
                    int newStock = Integer.parseInt(tokens[2].trim());

                    boolean found = false;
                    for (BookObject book : bookObjectVector) {
                        if (book.getBookId().equals(bookId)) {
                            book.setStock(newStock);
                            found = true;
                            break;

                        }
                    }
                    System.out.println(found);

                    if (found) {
                        saveAllBooksToFile(bookObjectVector, "src/Data/book.txt");
                        out.write("재고 수정이 완료되었습니다~~~~.\n");
                    } else {
                        out.write("책을 찾을 수 없습니다.\n");
                    }
                    out.flush();
                }

                if (clientMessage.startsWith("ADDTOCART")) {
                    String[] tokens = clientMessage.split(",");
                    String userId = tokens[1].trim();
                    String bookId = tokens[2].trim();
                    String bookName = tokens[3].trim();
                    int price = Integer.parseInt(tokens[4].trim());
                    int quantity = Integer.parseInt(tokens[5].trim());
                    int totalPrice = price * quantity;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Data/cart.txt", true))) {
                        writer.write(String.format("USERID=%s, BOOKID=%s, BOOKNAME=%s, PRICE=%d, QUANTITY=%d, TOTALPRICE=%d\n", userId, bookId, bookName, price, quantity, totalPrice));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (clientMessage.startsWith("CARTINFO")) {
                    String userId = clientMessage.substring(9).trim();
                    try (BufferedReader reader = new BufferedReader(new FileReader("src/Data/cart.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] tokens = line.split(", ");
                            String fileUserId = tokens[0].split("=")[1];
                            String bookId = tokens[1].split("=")[1];
                            String bookName = tokens[2].split("=")[1];
                            int price = Integer.parseInt(tokens[3].split("=")[1]);
                            int quantity = Integer.parseInt(tokens[4].split("=")[1]);
                            int totalPrice = Integer.parseInt(tokens[5].split("=")[1]);

                            if (fileUserId.equals(userId)) {
                                out.write(String.format("BOOKID=%s, BOOKNAME=%s, PRICE=%d, QUANTITY=%d, TOTALPRICE=%d\n", bookId, bookName, price, quantity, totalPrice));
                            }
                        }
                        out.write("END\n"); // 응답 종료 마커
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (clientMessage.startsWith("CHARGECASH")) {
                    String[] tokens = clientMessage.split(",");
                    String userId = tokens[1].trim();
                    int chargeAmount = Integer.parseInt(tokens[2].trim());

                    boolean found = false;
                    for (Person person : personVector) {
                        if (person.getId().equals(userId)) {
                            person.setCash(person.getCash() + chargeAmount);
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        saveAllPersonsToFile(personVector, "src/Data/person.txt");
                        out.write("캐시 충전이 완료되었습니다.\n");
                    } else {
                        out.write("사용자를 찾을 수 없습니다.\n");
                    }
                    out.flush();
                }
                if (clientMessage.startsWith("PURCHASE")) {
                    String[] tokens = clientMessage.split(",");
                    String userId = tokens[1].trim();
                    int totalCost = Integer.parseInt(tokens[2].trim());

                    boolean found = false;
                    for (Person person : personVector) {
                        if (person.getId().equals(userId)) {
                            if (person.getCash() >= totalCost) {
                                person.setCash(person.getCash() - totalCost);
                                found = true;
                            } else {
                                out.write("캐시가 부족합니다.\n");
                                out.flush();
                                return;
                            }
                            break;
                        }
                    }

                    if (found) {
                        saveAllPersonsToFile(personVector, "src/Data/person.txt");
                        clearUserCart(userId);
                        out.write("구매가 완료되었습니다.\n");
                    } else {
                        out.write("사용자를 찾을 수 없습니다.\n");
                    }
                    out.flush();
                }

                if (clientMessage.startsWith("DELETEBOOK")) {
                    String[] tokens = clientMessage.split(",");
                    String bookId = tokens[1].trim();

                    boolean found = false;
                    for (BookObject book : bookObjectVector) {
                        if (book.getBookId().equals(bookId)) {
                            bookObjectVector.remove(book);
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        saveAllBooksToFile(bookObjectVector, "src/Data/book.txt");
                        out.write("책 삭제가 완료되었습니다.\n");
                    } else {
                        out.write("책을 찾을 수 없습니다.\n");
                    }
                    out.flush();
                }

                if (clientMessage.startsWith("GETUSERS")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("src/Data/person.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] tokens = line.split(", ");
                            String auth = tokens[3].split("=")[1];
                            if (auth.equals("user")) {
                                out.write(line + "\n");
                            }
                        }
                        out.write("END\n"); // 응답 종료 마커
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                if (listener != null) listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}