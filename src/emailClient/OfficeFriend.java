package emailClient;

public class OfficeFriend extends OfficialRecipient implements HavetoWish {
    private final String bday;

    public OfficeFriend(String name, String email, String designation, String bday) {
        super(name, email, designation);
        this.bday = bday;
    }

    @Override
    public String bdaywishing() {
        return "Wish you a Happy Birthday";
    }

}