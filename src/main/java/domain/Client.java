package domain;

public class Client extends Entity<String> {

    private String name;

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
