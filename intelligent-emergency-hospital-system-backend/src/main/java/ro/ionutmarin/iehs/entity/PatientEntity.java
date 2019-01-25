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
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "address")
        private String address;

        @Column(name = "age")
        private int age;

        @Column(name = "gender")
        private String gender;

        @Column(name = "cnp")
        private long cnp;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
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

        public String getGender() {
                return gender;
        }

        public void setGender(String gender) {
                this.gender = gender;
        }

        public long getCnp() {
                return cnp;
        }

        public void setCnp(long cnp) {
                this.cnp = cnp;
        }
}
