package onlineCart;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import handler.MenuHandler;
import member.Person;

public class exProject1 {
    static final int NUM_BOOK = 3;
    static final int NUM_ITEM = 7;
    static Person person; // 사용자 객체 변수 추가
    static BufferedReader in = null; // 입력 리더
    static BufferedWriter out = null; // 출력 리더
    static Socket socket = null;
    static Scanner scanner = new Scanner(System.in);

    public static MenuHandler menuHandler = new MenuHandler(); // MenuHandler 객체 초기화

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            scanner = new Scanner(System.in);

            while (true) {
                menuHandler.loginMenu(scanner,out,in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}