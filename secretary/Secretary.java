package secretary;

import server.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Secretary implements Runnable {
    private Socket socket;

    private ObjectInputStream objectSocketInput;
    private ObjectOutputStream objectSocketOutput;


    public Secretary(Socket socket) {
        this.socket = socket;
        try {
            objectSocketOutput = new ObjectOutputStream(socket.getOutputStream());
            objectSocketInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            objectSocketOutput.writeUTF("secretary");
            objectSocketOutput.flush();//задължително след писане, без него не работи

            //---------------------- започва secretaryWork()

            //  - чете документ от нишката на сървъра
            //  - ако е null връща null към сървъра (в купчината необработени документи е нямало документ)
            //  - ако не е null на случаен принцип определя дали документът е валиден (50/50 шанс)
            //  и го връща на сървъра за разпределяне
            while (true) {
                Document docToManage = (Document) objectSocketInput.readObject();

                if (docToManage != null) {
                    if (Math.random() < 0.5)
                        docToManage.setValid();
                }

                objectSocketOutput.writeObject(docToManage);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
