

package com.program;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogRecord implements Serializable, Comparable{

    private LocalDate date;
    private LocalTime time;
    private long timestamp;
    private String IPAddress;
    private String username;
    private String role;
    private String URL;
    private String description;

    public LogRecord(LocalDate date, LocalTime time, long timestamp, String IPAddress, String username, String role, String URL, String description) {
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
        this.IPAddress = IPAddress;
        this.username = username;
        this.role = role;
        this.URL = URL;
        this.description = description;
    }

    public LogRecord(LocalDate date, LocalTime time, long timestamp, String IPAddress, String username, String role, String URL) {
        this(date, time, timestamp, IPAddress, username, role, URL, "");
    }


    @Override
    public int compareTo(Object o) {
        if (!(o instanceof LogRecord tempLogRecord)) {
            return 0;
        }
        return Long.compare(timestamp, tempLogRecord.timestamp);
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) +
                "-" + time +
                "-" + timestamp +
                "-" + IPAddress +
                "-" + username +
                "-" + role +
                "-" + URL +
                "-" + description;
    }


    //Getter

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getURL() {
        return URL;
    }

    public String getDescription() {
        return description;
    }
}
