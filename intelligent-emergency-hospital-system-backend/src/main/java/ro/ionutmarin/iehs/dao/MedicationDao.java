package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.MedicationEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import java.util.List;

public interface MedicationDao {
    void save(MedicationEntity medicationEntity);
    List<MedicationEntity> findAll();
}
