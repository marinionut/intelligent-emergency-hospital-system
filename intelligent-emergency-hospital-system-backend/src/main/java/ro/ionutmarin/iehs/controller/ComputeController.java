package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.DoctorDao;
import ro.ionutmarin.iehs.dao.PatientDao;
import ro.ionutmarin.iehs.entity.PatientEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ComputeController {

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    PatientDao patientDao;

    @RequestMapping("/patient")
    public PatientEntity print() throws IOException {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirst_name("ionut");
        patientEntity.setLast_name("marin");


        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setName("camera");
        roomEntity.setNumber(20);
        patientEntity.setRoomEntity(roomEntity);

        patientDao.save(patientEntity);

        return patientEntity;
    }

}
