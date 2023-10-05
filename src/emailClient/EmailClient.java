package emailClient;

import java.util.Scanner;

public class EmailClient {
    public static void main(String[] args) {
        EmailPlatform platform = new EmailPlatform();//starting the client
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the application");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                // input format - Official: nimal,nimal@gmail.com,ceo
                // Use a single input to get all the details of a recipient
                // code to add a new recipient
                // store details in clientList.txt file
                // Hint: use methods for reading and writing files
                System.out.println("Enter recipient details separately in the following format.");
                System.out.println("Personal Recipient -> Personal: <name>,<nickname>,<email address>,<birthday>");
                System.out.println("Official Recipient -> Official: <name>,<email address>,<designation>");
                System.out.println("Office Friend -> Office_friend: <name>,<email address>,<designation>,<birthday>");

                boolean check = true;
                while (check) {
                    String s = scanner.nextLine();
                    platform.writingtext(s);

                    System.out.println("Do u want to add more recipients?\nIf you want,type yes");
                    String condition = scanner.nextLine();
                    if (!condition.equals("yes"))
                        check = false;
                    else {
                        System.out.println("Enter recipient details separately in the above format.");
                    }
                }
                break;

            case 2:
                // input format - email, subject, content
                // code to send an email

                System.out.println("enter the subject");
                String subject = scanner.nextLine();
                System.out.println("enter the reciever email");
                String email = scanner.nextLine();
                System.out.println("enter the content");
                String content = scanner.nextLine();
                JavaMail.SendMail(email, subject, content);
                break;

            case 3:
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                // code to print recipients who have birthdays on the given date

                System.out.println("Input birthday - <yyyy/mm/dd>");
                String birthDate = scanner.nextLine();// input format - yyyy/MM/dd (ex: 2018/09/17)
                String[] birthdayArray = birthDate.split("/");
                String birthday_wanted = birthdayArray[1] + "-" + birthdayArray[2];
                platform.getWishingList(birthday_wanted);// code to print recipients who have birthdays on the given date
                break;

            case 4:
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                // code to print the details of all the emails sent on the input date
                System.out.println("Input date to get all the mail details sent - <yyyy/mm/dd>");
                String givendate = scanner.nextLine();
                platform.getDetailsForGivenDate(givendate);
                break;

            case 5:
                System.out.println("Number of recipients in the application is " + Recipient.recipientCounter);
                // code to print the number of recipient objects in the application
                break;

        }
        System.out.println("\nProgram terminated.");



        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes

    }
}
