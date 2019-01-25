package ro.ionutmarin.iehs.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="appointment")
@Data
@NamedQueries({
        @NamedQuery(
                name="AppointmentEntity.findAll",
                query="SELECT d FROM AppointmentEntity d"
        )
})
@Getter
@Setter
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "doctor_id")
    private int doctorId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
