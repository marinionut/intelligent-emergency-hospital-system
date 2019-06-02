package ro.ionutmarin.iehs.model;

public class AlertAck {
    private String alertUid;
    private String username;

    public AlertAck() {
    }

    public String getAlertUid() {
        return alertUid;
    }

    public void setAlertUid(String alertUid) {
        this.alertUid = alertUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
