package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ionutmarin.iehs.dao.MedicalEquipmentDao;
import ro.ionutmarin.iehs.entity.MedicalEquipmentEntity;

import java.util.List;

@RestController
@RequestMapping("/api/medicalEquipment")
public class MedicalEquipmentController {

    @Autowired
    private MedicalEquipmentDao medicalEquipmentDao;

    @RequestMapping("/all")
    public List<MedicalEquipmentEntity> getMedicalEquipment() {
        return medicalEquipmentDao.findAll();
    }

    @RequestMapping
    public MedicalEquipmentEntity getMedicalEquipmentById(@RequestParam("id") int id) {
        return medicalEquipmentDao.findById(id);
    }

    @RequestMapping("/add")
    public ResponseEntity addMedicalEquipment(@RequestBody MedicalEquipmentEntity medicalEquipmentEntity) {
        try {
            medicalEquipmentDao.save(medicalEquipmentEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteMedicalEquipment(@RequestParam("id") int id) {
        try {
            medicalEquipmentDao.delete(id);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity updateMedicalEquipmentStatus(@RequestParam("id") int id,
                                                       @RequestParam("status") boolean status) {
        try {
            MedicalEquipmentEntity medicalEquipmentEntity = medicalEquipmentDao.findById(id);
            medicalEquipmentEntity.setStatus(status);
            medicalEquipmentDao.save(medicalEquipmentEntity);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
