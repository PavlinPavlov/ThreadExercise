package secretary;

import java.io.IOException;
import java.net.Socket;

public class SecretaryFactory {
    public static void main(String[] args) throws IOException {
        // -стартира две секретарки
        for (int i = 0; i < 2; i++) {
            Socket socket = new Socket("192.168.0.103", 3434);
            new Thread(new Secretary(socket)).start();
        }
    }
}
