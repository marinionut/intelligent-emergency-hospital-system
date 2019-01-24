package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.DoctorStuffEntity;

import java.util.List;

public interface DoctorStuffDao {
    void save(DoctorStuffEntity doctorEntity);
    List<DoctorStuffEntity> findAll();
}
