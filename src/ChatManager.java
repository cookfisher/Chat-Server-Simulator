import entity.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private final static List<Message> messageList = new ArrayList<>();

    public synchronized static Message postMessage(String user, String message) {
        Message newMessage = new Message(user, message);
        messageList.add(newMessage);
        return newMessage;
    }

    public synchronized static List<Message> listMessage(LocalDateTime start, LocalDateTime end) {
        int startIndex = findLeftIndex(start);
        if (startIndex == -1) {
            return new ArrayList<>();
        }
        int endIndex = findRightIndex(end);
        if (endIndex == -1) {
            return new ArrayList<>();
        }
        return messageList.subList(startIndex, endIndex + 1);
    }

    public synchronized static void clearChat(LocalDateTime start, LocalDateTime end) {
        messageList.removeAll(listMessage(start, end));
    }

    private static int findLeftIndex(LocalDateTime start) {
        if (start == null) {
            return 0;
        }
        for (int i = 0; i < messageList.size(); i++) {
            if (!messageList.get(i).getTimestamp().isBefore(start)) {
                return i;
            }
        }
        return -1;
    }

    private static int findRightIndex(LocalDateTime end) {
        if (end == null) {
            return messageList.size() - 1;
        }
        for (int i = messageList.size() - 1; i >= 0; i--) {
            if (!messageList.get(i).getTimestamp().isAfter(end)) {
                return i;
            }
        }
        return -1;
    }


}

