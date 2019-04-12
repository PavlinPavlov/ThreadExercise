package server;

import java.util.LinkedList;
import java.util.Queue;

public class DocumentPile {
    //три списъка за всеки вид документи необранотени, валидни, невалидни
    private static Queue<Document> allDocuments = new LinkedList<>();
    private static Queue<Document> validDocuments = new LinkedList<>();
    private static Queue<Document> invalidDocuments = new LinkedList<>();

    //обекти-ключове
    // -ползват се за да може по тях да се синхронизират операциите по опашките (добавяне/вземане)
    // -за всяка опашка има различен ключ, защото няма логика докато се работи в едната опашка,
    //  другите 2 да са заклзчени
    private static final Object keyAll = new Object();
    private static final Object keyAddValid = new Object();
    private static final Object keyAddInvalid = new Object();

    //взема документ от необработени (премахва го от опашката)
    public static Document getDocument() {
        synchronized (keyAll) {
            // -ако опашката  празна (size() == 0) връща null вместо документ
            if (allDocuments.size() != 0) {
                return allDocuments.remove();
            }
        }
        return null;
    }

    //добавяне на необработен документ
    public static void addForHandle(Document doc) {
        synchronized (keyAll) {
            allDocuments.add(doc);
        }
    }

    public static void addValid(Document doc) {
        synchronized (keyAddValid) {
            validDocuments.add(doc);
            System.out.println("Valid docs: " + validDocuments.size());
        }
    }

    public static void addInvalid(Document doc) {
        synchronized (keyAddInvalid) {
            invalidDocuments.add(doc);
            System.out.println("Invalid docs: " + invalidDocuments.size());
        }
    }
}
