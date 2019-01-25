package ro.ionutmarin.iehs.entity;

import com.twilio.rest.video.v1.Room;
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

    @JoinColumn(name = "patient_id", updatable=false, insertable=false)
    @OneToOne(targetEntity = PatientEntity.class, fetch = FetchType.EAGER)
    private PatientEntity patient;

    @JoinColumn(name = "room_id", updatable=false, insertable=false)
    @OneToOne(targetEntity = RoomEntity.class, fetch = FetchType.EAGER)
    private RoomEntity room;

    @JoinColumn(name = "doctor_id", updatable=false, insertable=false)
    @OneToOne(targetEntity = DoctorEntity.class, fetch = FetchType.EAGER)
    private DoctorEntity doctor;

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

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }
}
