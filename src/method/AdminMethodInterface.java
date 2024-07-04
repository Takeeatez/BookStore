package method;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public interface AdminMethodInterface {

    void addBook(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException;

    void updateBookStock(Scanner scanner, BufferedWriter out, BufferedReader in) throws IOException;


}
