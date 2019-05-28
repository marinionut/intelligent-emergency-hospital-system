package ro.ionutmarin.iehs.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="alert")
@Data
@NamedQueries({
        @NamedQuery(
                name="AlertEntity.findAll",
                query="SELECT d FROM AlertEntity d"
        )
})
@Getter
@Setter
public class AlertEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "type")
    private String type;

    @Column(name = "roomNumber")
    private int roomNumber;

    public AlertEntity() {
    }

    public AlertEntity(String username, String message, Timestamp timestamp, String type, int roomNumber) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
