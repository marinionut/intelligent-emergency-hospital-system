package ro.ionutmarin.iehs.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="patient")
@Data
@NamedQueries({
        @NamedQuery(
                name="PatientEntity.findAll",
                query="SELECT r FROM PatientEntity r"
        )
})
@Getter
@Setter
public class PatientEntity {
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "first_name")
        private String first_name;

        @Column(name = "last_name")
        private String last_name;

        @Column(name = "phone_number")
        private int phone_number;

        @Column(name = "address")
        private String address;

        @Column(name = "age")
        private int age;

        @Column(name = "gender")
        private int gender;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "doctor_id")
        private DoctorEntity doctorEntity;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "room_id")
        private RoomEntity roomEntity;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "medication_id")
        private MedicationEntity medicationEntity;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getFirst_name() {
                return first_name;
        }

        public void setFirst_name(String first_name) {
                this.first_name = first_name;
        }

        public String getLast_name() {
                return last_name;
        }

        public void setLast_name(String last_name) {
                this.last_name = last_name;
        }

        public int getPhone_number() {
                return phone_number;
        }

        public void setPhone_number(int phone_number) {
                this.phone_number = phone_number;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public int getGender() {
                return gender;
        }

        public void setGender(int gender) {
                this.gender = gender;
        }

        public DoctorEntity getDoctorEntity() {
                return doctorEntity;
        }

        public void setDoctorEntity(DoctorEntity doctorEntity) {
                this.doctorEntity = doctorEntity;
        }

        public RoomEntity getRoomEntity() {
                return roomEntity;
        }

        public void setRoomEntity(RoomEntity roomEntity) {
                this.roomEntity = roomEntity;
        }

        public MedicationEntity getMedicationEntity() {
                return medicationEntity;
        }

        public void setMedicationEntity(MedicationEntity medicationEntity) {
                this.medicationEntity = medicationEntity;
        }
}
