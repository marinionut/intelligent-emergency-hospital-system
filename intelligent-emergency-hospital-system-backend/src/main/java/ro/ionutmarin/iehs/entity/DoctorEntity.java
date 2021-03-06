package ro.ionutmarin.iehs.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name="doctor")
@Data
@NamedQueries({
        @NamedQuery(
                name="DoctorEntity.findAll",
                query="SELECT d FROM DoctorEntity d"
        )
})
@Getter
@Setter
public class DoctorEntity {
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "email")
        private String email;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "specialization")
        private String specialization;

        @Column(name = "user_id")
        private int  userId;

        @NotFound(action=NotFoundAction.IGNORE)
        @JoinColumn(name = "user_id", updatable=false, insertable=false)
        @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
        private UserEntity userEntity;

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

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getSpecialization() {
                return specialization;
        }

        public void setSpecialization(String specialization) {
                this.specialization = specialization;
        }

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public UserEntity getUserEntity() {
                return userEntity;
        }

        public void setUserEntity(UserEntity userEntity) {
                this.userEntity = userEntity;
        }
}
