package ro.ionutmarin.iehs.model;

public class WebAlert {
    private long alertTimestamp;
    private String message;

    public long getAlertTimestamp() {
        return alertTimestamp;
    }

    public void setAlertTimestamp(long alertTimestamp) {
        this.alertTimestamp = alertTimestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
