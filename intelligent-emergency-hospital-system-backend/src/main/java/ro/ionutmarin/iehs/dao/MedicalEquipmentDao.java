package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.MedicalEquipmentEntity;

import java.util.List;

public interface MedicalEquipmentDao {
    void save(MedicalEquipmentEntity medicalEquipmentEntity);
    List<MedicalEquipmentEntity> findAll();
    void delete(int id);
    MedicalEquipmentEntity findById(int id);
}
