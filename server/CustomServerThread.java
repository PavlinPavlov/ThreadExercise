package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CustomServerThread implements Runnable {
    private Socket socket;

    private ObjectInputStream objectSocketInput;
    private ObjectOutputStream objectSocketOutput;

    public CustomServerThread(Socket socket) {
        this.socket = socket;
        try {
            objectSocketInput = new ObjectInputStream(socket.getInputStream());
            objectSocketOutput = new ObjectOutputStream(socket.getOutputStream());

            // -горните 2 стрийма са направени в конструктора за да може да се ползват из целия обект
            //  run() и secretaryWork() методите
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String type = objectSocketInput.readUTF();

            // -прочита ред от клиента (студент или секретарка), който показва какъв е той
            // -ако е студент прочита един документ и до записва в необработените  документ
            // -ако е секретарка стартира метода secretaryWork();

            if (type.equals("student")) {
                Document doc = (Document) objectSocketInput.readObject();
                DocumentPile.addForHandle(doc);
            } else if (type.equals("secretary")) {
                secretaryWork();
            } else {
                System.out.println("ERROR");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void secretaryWork() throws IOException, ClassNotFoundException {
        while (true) {
            // -изпраща на секретарката необработен документ
            // -взема от секретарката обработен документ (дето се е пратил на предния ред)
            // -проверява какъв е и го разпределя

            objectSocketOutput.writeObject(DocumentPile.getDocument());
            Document managedDoc = (Document) objectSocketInput.readObject();

            if (managedDoc != null) {
                if (managedDoc.isValid()) {
                    DocumentPile.addValid(managedDoc);
                } else {
                    DocumentPile.addInvalid(managedDoc);
                }
            }
        }
    }
}
