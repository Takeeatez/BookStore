package Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import Object.Person;


public class Register {

    public static void savePersonToFile(Person person, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // append 모드로 열기
            writer.write("id=" + person.getId() + ", password=" + person.getPassword() + ", name=" + person.getName() + ", auth=" + person.getAuth() + ", cash=" + person.getCash() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isIdDuplicated(String id, Vector<Person> personVector) {
        for (Person person : personVector) {
            if (person.getId().trim().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }
}
