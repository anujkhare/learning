package in.jaaga.learning.cli;

import java.util.Scanner;

import in.jaaga.learning.*;

/**
 * Created by freeman on 4/2/16.
 */
public class CLI implements InteractionInterface {
    Learning learning;

    public static void main(String[] argv) {
        new CLI();
    }

    public CLI() {
        Session.setDevice("CLI");
        Session session = new Session();
        learning = new Learning(this, session, new ChatBot(session), new DB());
        learning.start();
    }

    public void send(ChatItem item) {
        System.out.println("srinivas: "+item.getMessage());
        if (item.getResponseType() != Learning.NO_RESPONSE) {
            if (!item.getMessage().contains("Congratulations")) {
                System.out.print("you: ");
                Scanner sc = new Scanner(System.in);
                learning.onResponse(new Scanner(System.in).nextLine());
            }
        }
    }
}
