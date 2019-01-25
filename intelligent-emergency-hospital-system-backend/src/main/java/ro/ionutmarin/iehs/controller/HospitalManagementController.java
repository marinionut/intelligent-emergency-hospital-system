package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ionutmarin.iehs.dao.*;
import ro.ionutmarin.iehs.entity.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalManagementController {
    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private DoctorStuffDao doctorStuffDao;

    @Autowired
    private AlertDao alertDao;

    @RequestMapping("/patient/all")
    public List<PatientEntity> getPatient() {
        return patientDao.findAll();
    }

    @RequestMapping("/patient")
    public PatientEntity getPatientById(@RequestParam("id") int id) {
        return patientDao.findById(id);
    }

    @RequestMapping("/patient/add")
    public ResponseEntity addPatient(@RequestBody PatientEntity patientEntity) {
        try {
            patientDao.save(patientEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/patient/delete", method = RequestMethod.DELETE)
    public ResponseEntity deletePatient(@RequestParam("id") int id) {
        try {
            patientDao.delete(id);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/doctor/all")
    public List<DoctorEntity> getDoctor() {
        return doctorDao.findAll();
    }

    @RequestMapping("/doctor")
    public DoctorEntity getDoctorById(@RequestParam("id") int id) {
        return doctorDao.findById(id);
    }

    @RequestMapping("/doctor/add")
    public ResponseEntity addDoctor(@RequestBody DoctorEntity doctorEntity) {
        try {
            doctorDao.save(doctorEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/doctor/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteDoctor(@RequestParam("id") int id) {
        try {
            doctorDao.delete(id);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/doctorstuff/all")
    public List<DoctorStuffEntity> getDoctorStuff() {
        return doctorStuffDao.findAll();
    }

    @RequestMapping("/doctorstuff/add")
    public ResponseEntity addDoctorStuff(@RequestBody DoctorStuffEntity doctorStuffEntity) {
        try {
            doctorStuffDao.save(doctorStuffEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/room/all")
    public List<RoomEntity> getRoom() {
        return roomDao.findAll();
    }

    @RequestMapping("/room")
    public RoomEntity getRoomById(@RequestParam("id") int id) {
        return roomDao.findById(id);
    }

    @RequestMapping("/room/add")
    public ResponseEntity addRoom(@RequestBody RoomEntity roomEntity) {
        try {
            roomDao.save(roomEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoom(@RequestParam("id") int id) {
        try {
            roomDao.delete(id);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/appointment/all")
    public List<AppointmentEntity> getAppointment() {
        return appointmentDao.findAll();
    }

    @RequestMapping("/appointment/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentEntity appointmentEntity) {
        try{
            appointmentDao.save(appointmentEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/alert/all")
    public List<AlertEntity> getAlerts() {
        return alertDao.findAll();
    }

}
