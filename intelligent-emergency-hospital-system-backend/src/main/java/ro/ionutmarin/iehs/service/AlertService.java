package ro.ionutmarin.iehs.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.ionutmarin.iehs.dao.*;
import ro.ionutmarin.iehs.entity.AppointmentEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.kafka.Sender;
import ro.ionutmarin.iehs.model.Alert;

import ro.ionutmarin.iehs.util.*;

import java.util.UUID;

@Service
public class AlertService {

    @Autowired
    private Sender sender;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private UserDao userDao;

    public Alert resolveAlert(int roomNumber, int bedNumber) throws Exception {
        System.out.println("trying to resolve who is responsible for alert with roomNumber:" + roomNumber +
                " bedNumber" + bedNumber + "...");

        RoomEntity roomEntity = roomDao.findByRoomNumber(roomNumber);
        AppointmentEntity appointmentEntity = null;
        try {
            //appointmentEntity = appointmentDao.findByRoomIdAndBedNumber(roomEntity.getId(), bedNumber);
            appointmentEntity = appointmentDao.findAll()
                    .stream()
                    .filter(a -> a.getBedNumber() == bedNumber)
                    .filter(a -> a.getRoomId() == roomEntity.getId())
                    .findFirst().get();
            if (appointmentEntity == null)
                throw new Exception();
        } catch (Exception e) {
            Exception myEx = new Exception(String.format("Nu a fost gasita perechea camera:%s - pat:%s", roomNumber, bedNumber));
            throw myEx;
        }

        Alert alert = new Alert();
        alert.setRoomNumber(roomNumber);
        alert.setBedNumber(bedNumber);
        alert.setRoomName(roomEntity.getDescription());
        alert.setAppointmentId(appointmentEntity.getId());
        alert.setTimestamp(DateTime.now().getMillis());
        alert.setToPhoneNumber(appointmentEntity.getDoctor().getPhoneNumber());
        alert.setUid(UUID.randomUUID().toString());

        int userId = appointmentEntity.getDoctor().getUserId();
        if (Constants.NO_USER_ID == userId) {
            alert.setUsername(Constants.NO_USER);
        } else {
            UserEntity userEntity = userDao.findById(userId);
            alert.setUsername(userEntity.getUsername());
        }
        String alertMessage = String.format("HospitAlert: Alerta! Domnule doctor %s rog sa va prezentati la %s cu numarul %s la patul %s pacient %s",
                appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName(),
                alert.getRoomName(), alert.getRoom(), alert.getBedNumber(),
                appointmentEntity.getPatient().getFirstName() + " " + appointmentEntity.getPatient().getLastName());
        alert.setMessage(alertMessage);

        System.out.println("alert was created " + alert.toString());

        this.sender.sendMessage(alert);

        return alert;
    }
}
