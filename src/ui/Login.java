package ui;

import handler.MenuHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


public class Login {

    public static String loggedInUserId = null;

    public void login(Scanner scanner, BufferedWriter out, BufferedReader in, String authType, MenuHandler menuHandler) throws IOException {
        System.out.println("아이디를 입력하세요: ");
        String id = scanner.next();
        System.out.println("비밀번호를 입력하세요: ");
        String password = scanner.next();

        String loginInfo = id + "," + password + "," + authType; // 로그인 유형 추가
        out.write("LOGIN " + loginInfo + "\n");
        out.flush();

        String serverResponse = in.readLine();
        System.out.println(serverResponse);

        if ("로그인 성공".equals(serverResponse)) {
            loggedInUserId = id; // 로그인 성공 시 사용자 ID 저장
            if ("user".equalsIgnoreCase(authType)) {
                menuHandler.mainMenu(scanner, out, in);
            } else if ("admin".equalsIgnoreCase(authType)) {
                menuHandler.adminMenu(scanner, out, in);
            }
        } else {
            System.out.println("로그인 실패. 다시 시도해주세요.");
        }
    }
}
