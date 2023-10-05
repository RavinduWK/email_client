package emailClient;

public class PersonalRecipient extends Recipient implements HavetoWish {
    private final String bday;
    private final String nickname;

    public PersonalRecipient(String name, String nickname, String email, String bday) {
        super(name, email);
        this.bday = bday;
        this.nickname = nickname;
    }

    @Override
    public String bdaywishing() {
        return "hugs and love on your birthday";
    }

}