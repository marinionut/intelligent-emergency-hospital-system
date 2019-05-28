package ro.ionutmarin.iehs.model;


public class Alert {
    private int room;
    private String message;

    public Alert() {

    }

    public Alert(int room, String message) {
        this.room = room;
        this.message = message;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "room=" + room +
                ", message='" + message + '\'' +
                '}';
    }
}
