package handler;

import method.AdminMethod;
import method.UserMethod;
import ui.Login;
import ui.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import static ui.Register.registerMember;

public class MenuHandler {

    public AdminMethod adminMethod = new AdminMethod();
    public UserMethod userMethod = new UserMethod();
    public Login login = new Login();

    public void loginMenu(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        Menu.loginMenu();
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("회원 로그인 선택됨.");
                login.login(scanner, out, in, "user",this); //순환참조 해결을 위해
                break;
            case 2:
                System.out.println("관리자 로그인 선택됨.");
                login.login(scanner, out, in, "admin",this); //순환참조 해결을 위해
                break;
            case 3:
                System.out.println("회원 가입 선택됨.");
                registerMember(scanner, out, in);
                break;
            case 4:
                System.out.println("종료 선택됨.");
                // 프로그램 종료
                System.exit(0);
            default:
                System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                break;
        }
    }

    public void mainMenu(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        while (true) { // 루프 추가
            Menu.mainMenu();
            int mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    System.out.println("내 정보 확인 선택됨.");
                    userMethod.requestUserInfo(out, in);
                    break;
                case 2:
                    System.out.println("책 목록 선택됨.");
                    userMethod.viewBooks(scanner, out, in); // 책 목록 로직
                    break;
                case 3:
                    System.out.println("장바구니 확인 선택됨.");
                    userMethod.viewCart(scanner,out, in);
                    break;
                case 4:
                    System.out.println("캐시 충전 선택됨.");
                    userMethod.chargeCash(scanner, out, in);
                    break;
                case 5:
                    System.out.println("로그아웃 선택됨.");
                    return; // 루프를 종료하고 로그인 메뉴로 돌아가기
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }

    public void adminMenu(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        while (true) {
            Menu.adminMenu();
            int adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    System.out.println("회원 정보 선택됨.");
                    adminMethod.viewUsers(out, in); // 회원 정보 확인 로직
                    break;
                case 2:
                    System.out.println("책 관리 선택됨.");
                    manageBooks(scanner, out, in);
                    break;
                case 3:
                    System.out.println("로그아웃 선택됨.");
                    Login.loggedInUserId = null; // 로그아웃 시 ID 초기화
                    loginMenu(scanner, out, in);
                    return; // 로그인 메뉴로 돌아간 후 메서드 종료
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }

    public void cartMenu(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        Menu.cartMenu();
        int cartChoice = scanner.nextInt();

        switch (cartChoice) {
            case 1:
                System.out.println("내 장바구니 상세 정보 선택됨.");
                // 내 장바구니 상세 정보 로직
                break;
            case 2:
                System.out.println("장바구니 물품 갯수 수정 선택됨.");
                // 장바구니 물품 갯수 수정 로직
                break;
            case 3:
                System.out.println("장바구니 물품 삭제 선택됨.");
                // 장바구니 물품 삭제 로직
                break;
            case 4:
                System.out.println("메인 메뉴로 돌아가기 선택됨.");
                // 메인 메뉴로 돌아가기
                mainMenu(scanner, out, in);
                break;
            default:
                System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                break;
        }
    }

    public void manageBooks(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        while (true) {
            Menu.bookManagementMenu();
            int bookChoice = scanner.nextInt();

            switch (bookChoice) {
                case 1:
                    adminMethod.addBook(scanner, out, in);
                    break;
                case 2:
                    adminMethod.updateBookStock(scanner, out, in);
                    break;
                case 3:
                    adminMethod.deleteBook(scanner, out, in); // 책 삭제 기능 추가
                    break;
                case 4:
                    return; // adminMenu로 돌아가기
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }

}
