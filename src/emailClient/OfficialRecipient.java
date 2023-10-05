package emailClient;

public class OfficialRecipient extends Recipient {
    private final String designation;

    public OfficialRecipient(String name, String email, String designation) {
        super(name, email);
        this.designation = designation;
    }

}