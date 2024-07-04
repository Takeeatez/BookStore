package Service;

import Object.Person;

import java.io.*;
import java.util.Vector;

public class User {

    public static Person getPersonInfo(String id, Vector<Person> personVector) {
        for (Person person : personVector) {
            if (person.getId().trim().equalsIgnoreCase(id.trim())) {
                return person;
            }
        }
        return null;
    }

    public static void saveAllPersonsToFile(Vector<Person> personVector, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Person person : personVector) {
                writer.write(String.format("ID=%s, PASSWORD=%s, NAME=%s, AUTH=%s, CASH=%d\n", person.getId(), person.getPassword(), person.getName(), person.getAuth(), person.getCash()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearUserCart(String userId) {
        File inputFile = new File("src/Data/cart.txt");
        File tempFile = new File("src/Data/temp_cart.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                String fileUserId = tokens[0].split("=")[1];
                if (!fileUserId.equals(userId)) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("기존 장바구니 파일을 삭제하지 못했습니다.");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("임시 장바구니 파일을 원래 파일로 변경하지 못했습니다.");
        }
    }
}
