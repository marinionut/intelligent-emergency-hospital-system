package ro.ionutmarin.iehs.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="medical_equipment")
@Data
@NamedQueries({
        @NamedQuery(
                name="MedicalEquipmentEntity.findAll",
                query="SELECT me FROM MedicalEquipmentEntity me"
        )
})
@Getter
@Setter
public class MedicalEquipmentEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "roomNumber")
    private String roomNumber;

    @Column(name = "floor")
    private String floor;

    @Column(name = "status")
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
