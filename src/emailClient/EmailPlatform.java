package emailClient;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EmailPlatform {
    private final String username = "------------";

    // declare arraylist to add all the recipient objects
    ArrayList<Recipient> recipientList;
    // declare a hashmap to put haveto wish objects according to given bday date.
    HashMap<String, ArrayList<HavetoWish>> wishRecipients_hashmap;
    // declare a hash map to put mail details according to a given date.
    HashMap<String, ArrayList<String>> mailDetails_hashmap;

    public EmailPlatform() {
        // initializing given attributes
        wishRecipients_hashmap = new HashMap<String, ArrayList<HavetoWish>>();
        recipientList = new ArrayList<Recipient>();
        File file = new File("mailSerialized.ser");
        if (file.exists()) {
            deserialize();
        } else {
            mailDetails_hashmap = new HashMap<String, ArrayList<String>>();
        }
        createobject();
        sendgreetings();
    }

    public void createobject() {
        // operation to create specific objects for the given recipients
        try {
            // reading the clientlist file
            File myObj = new File("clientList.txt");
            if (!myObj.exists()) {
                myObj.createNewFile();
            }
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] detail_array = data.strip().split("[,: ]+");

                // Official Recipient
                if (detail_array[0].equals("Official")) {
                    Recipient r = new OfficialRecipient(detail_array[1], detail_array[2], detail_array[3]);// create a
                                                                                                           // official
                                                                                                           // recipient
                                                                                                           // object and
                                                                                                           // upcast it
                    recipientList.add(r);// update the recipient list

                    // Office Friend
                } else if (detail_array[0].equals("Office_friend")) {
                    String[] birthDate = detail_array[4].substring(5).split("/");
                    String birthday_wanted = birthDate[0] + "-" + birthDate[1];
                    Recipient r = new OfficeFriend(detail_array[1], detail_array[2], detail_array[3], detail_array[4]);// reate
                                                                                                                       // a
                                                                                                                       // official
                                                                                                                       // object
                                                                                                                       // and
                                                                                                                       // upcast
                                                                                                                       // it
                    recipientList.add(r);// update the recipient list

                    if (!wishRecipients_hashmap.containsKey(birthday_wanted)) {
                        wishRecipients_hashmap.put(birthday_wanted, new ArrayList<HavetoWish>());
                    }
                    (wishRecipients_hashmap.get(birthday_wanted)).add((HavetoWish) r);

                    // Personal
                } else if (detail_array[0].equals("Personal")) {
                    String[] birthDate = detail_array[4].substring(5).split("/");
                    String birthdayWanted = birthDate[0] + "-" + birthDate[1];
                    Recipient r = new PersonalRecipient(detail_array[1], detail_array[2], detail_array[3],
                            detail_array[4]);// create a personal object and upcast it
                    recipientList.add(r);// update the recipient list
                    if (!wishRecipients_hashmap.containsKey(birthdayWanted)) {// checking if there a key of given date
                                                                              // to add or create a new key
                        wishRecipients_hashmap.put(birthdayWanted, new ArrayList<HavetoWish>());
                    }
                    (wishRecipients_hashmap.get(birthdayWanted)).add((HavetoWish) r);
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            ;
        }
    }

    public void writingtext(String recipient_details) {
        try {
            // Specify the file name and path here
            File file = new File("clientList.txt");

            /*
             * This logic is to create the file if the
             * file is not already present
             */
            if (!file.exists()) {
                file.createNewFile();
            }

            // Here true is to append the content to file
            FileWriter fwo = new FileWriter(file, true);
            // BufferedWriter writer give better performance
            BufferedWriter bwo = new BufferedWriter(fwo);
            bwo.write(recipient_details + "\n");
            // Closing BufferedWriter Stream
            bwo.close();

            System.out.println("recipient  successfully added");

        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }

    public void sendgreetings() {
        // operation to send the greeting mails for specific persons
        LocalDate date = LocalDate.now();
        System.out.println("start to send greetings");
        String today = date.toString().substring(5);
        String greetingmessage;
        if (wishRecipients_hashmap.containsKey(today)) {
            // checking the hashmap if there is a key to represent the given date
            for (HavetoWish hw : wishRecipients_hashmap.get(today)) {
                if (hw instanceof OfficeFriend) {
                    greetingmessage = hw.bdaywishing() + " " + username;
                    mailsender(((OfficeFriend) hw).getEmail(), "Bday wish for u ", greetingmessage);
                }
                if (hw instanceof PersonalRecipient) {
                    greetingmessage = hw.bdaywishing() + " " + username;
                    mailsender(((PersonalRecipient) hw).getEmail(), "Bday wish for u ", greetingmessage);
                }
            }
        }
    }

    public void getWishingList(String birthday) {
        // getting the wishing list of a given date
        if (wishRecipients_hashmap.containsKey(birthday)) {
            for (HavetoWish hwl : wishRecipients_hashmap.get(birthday)) {
                System.out.println(((Recipient) hwl).getName());// casting the interafce object for a super class object
                                                                // and getting the name using encapsulation methods
            }
        } else
            System.out.println("no birthdays in " + birthday);// if there is no greetable persons in tha given date show
                                                              // that message.
    }

    // getting the current date
    public String getting_thedate() {
        LocalDate date = LocalDate.now();
        return date.toString().replace("-", "/");
    }

    public void serialize(HashMap<String, ArrayList<String>> saving_hashmap) {
        // converts the state of hash map to a byte stream so it can be again reverted
        // back in to the copy of the hashmap object.this part is doing in the
        // deserialization part
        try {
            FileOutputStream fos = new FileOutputStream("mailSerialized.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saving_hashmap);
            oos.close();
            fos.close();
        } catch (EOFException c) {
            System.out.println("Class not found hjh");
            c.printStackTrace();
            return;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void deserialize() {
        try {
            FileInputStream fis = new FileInputStream("mailSerialized.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            mailDetails_hashmap = (HashMap<String, ArrayList<String>>) ois.readObject();// passing the deseriolized
                                                                                        // hasgmap to
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }

    public void getDetailsForGivenDate(String givendate) {
        // operation to get all the details up to now for a given date
        try {
            for (String i : mailDetails_hashmap.get(givendate)) {
                System.out.println(i);
            }
        } catch (NullPointerException e) {
            System.out.println("no emails sent on " + givendate);// if there is no bday on that date raising the given
                                                                 // exception
        }
    }

    // operation to send the mails
    public void mailsender(String recievermail, String subject, String content) {
        // checking the saving hash map has a key of given date or not
        if (!mailDetails_hashmap.containsKey(getting_thedate()))
            mailDetails_hashmap.put(getting_thedate(), new ArrayList<String>());
        mailDetails_hashmap.get(getting_thedate())
                .add("Email : " + recievermail + " Subject : " + subject + " Content :" + content);

        serialize(mailDetails_hashmap);
        JavaMail.SendMail(recievermail, subject, content);
    }

}
