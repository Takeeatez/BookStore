package method;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public interface UserMethodInterface {

    void requestUserInfo(BufferedWriter out, BufferedReader in) throws IOException;

    void viewBooks(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException;

}
