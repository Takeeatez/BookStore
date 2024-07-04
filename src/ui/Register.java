package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Register {

    public static void registerMember(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException {
        while (true) {
            System.out.println("아이디를 입력하세요: ");
            String id = scanner.next();

            out.write("CHECKID " + id + "\n"); // CHECKID 명령어로 아이디 중복 확인
            out.flush();

            String serverResponse = in.readLine();
            if ("아이디가 중복됩니다.".equals(serverResponse)) {
                System.out.println("아이디가 중복됩니다. 다시 입력해주세요.");
            } else {
                System.out.println("사용 가능한 아이디입니다.");
                System.out.println("비밀번호를 입력하세요: ");
                String password = scanner.next();
                System.out.println("이름을 입력하세요: ");
                String name = scanner.next();
                System.out.println("권한을 입력하세요 (user/admin): ");
                String auth = scanner.next();

                String memberInfo = id + "," + password + "," + name + "," + auth;
                out.write("REGISTER " + memberInfo + "\n");
                out.flush();

                serverResponse = in.readLine(); // 서버 응답 받기
                System.out.println(serverResponse);
                break;
            }
        }
    }
}
