package client;

import server.Document;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StudentFactory {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) {
            Socket socket = new Socket("192.168.0.103", 3434);

            ObjectOutputStream objectSocketOutput = new ObjectOutputStream(socket.getOutputStream());

            objectSocketOutput.writeUTF("student");
            objectSocketOutput.flush(); // без това работи
            objectSocketOutput.writeObject(createDoc());
        }
    }

    private static Document createDoc() {
        double grade = Math.random() * 4 + 2;
        double income = Math.random() * 600 + 150;
        int type = (int) Math.round(Math.random());

        return new Document("Kiro", grade, income, "FKST", false, type);
    }
}
