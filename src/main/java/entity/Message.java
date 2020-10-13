package entity;

import javax.xml.bind.annotation.XmlAttribute;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private String username;
    private String content;
    private LocalDateTime timestamp;

    public Message(String username, String content) {
        this.username = username;
        this.content = content;
        timestamp = LocalDateTime.now();
    }

    public Message(String username, String content, LocalDateTime timestamp) {
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    @XmlAttribute(name = "username")
    public String getUsername() {
        return username;
    }

    @XmlAttribute(name = "content")
    public String getContent() {
        return content;
    }

    @XmlAttribute(name = "timestamp")
    public String getTimestampString() {
        return timestamp.toString();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                username, content);
    }
}
