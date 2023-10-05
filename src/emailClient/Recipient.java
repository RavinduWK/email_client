package emailClient;

public abstract class Recipient {
    private final String name;
    private final String email;
    public static int recipientCounter = 0;

    public Recipient(String name, String email){
        this.name = name;
        this.email = email;
        recipientCounter++;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
