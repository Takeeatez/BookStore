package Service;

import Object.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Login {

    public static Vector<Person> loadPersonsFromFile(String filePath) {
        Vector<Person> personVector = new Vector<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                String id = tokens[0].split("=")[1];
                String password = tokens[1].split("=")[1];
                String name = tokens[2].split("=")[1];
                String auth = tokens[3].split("=")[1];
                int cash = Integer.parseInt(tokens[4].split("=")[1]);

                Person person = new Person(id, password, name, auth,cash);
                personVector.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personVector;
    }

    public static boolean checkLogin(String id, String password, String authType, Vector<Person> personVector) {
        for (Person person : personVector) {
            if (person.getId().equals(id) && person.getPassword().equals(password) && person.getAuth().equals(authType)) {
                return true;
            }
        }
        return false;
    }
}
