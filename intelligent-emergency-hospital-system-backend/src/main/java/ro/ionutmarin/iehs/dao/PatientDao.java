package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.PatientEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import java.util.List;

public interface PatientDao {
    void save(PatientEntity patientEntity);
    List<PatientEntity> findAll();
    void delete(int id);
    PatientEntity findById(int id);
}
