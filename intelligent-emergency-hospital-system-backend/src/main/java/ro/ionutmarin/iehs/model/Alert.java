package ro.ionutmarin.iehs.model;


import org.joda.time.DateTime;

public class Alert {
    private int roomNumber;
    private String message;
    private String roomName;
    private int bedNumber;
    private int appointmentId;
    private String username;
    private long timestamp;
    private String toPhoneNumber;

    public Alert() {
    }

    public int getRoom() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getToPhoneNumber() {
        return toPhoneNumber;
    }

    public void setToPhoneNumber(String toPhoneNumber) {
        this.toPhoneNumber = toPhoneNumber;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "roomNumber=" + roomNumber +
                ", message='" + message + '\'' +
                ", roomName='" + roomName + '\'' +
                ", bedNumber=" + bedNumber +
                ", appointmentId=" + appointmentId +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", toPhoneNumber='" + toPhoneNumber + '\'' +
                '}';
    }
}
