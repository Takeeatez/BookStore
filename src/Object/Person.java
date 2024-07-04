package Object;

public class Person {
    private String id;
    private String password;
    private String name;
    private String auth;
    private int cash;

    public Person(String id, String password, String name, String auth,int cash) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.auth = auth;
        this.cash = cash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                ", cash=" + cash +
                '}';
    }
}
