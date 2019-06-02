package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.UserEntity;

import java.util.List;

public interface DoctorDao {
    void save(DoctorEntity doctorEntity);
    List<DoctorEntity> findAll();
    void delete(int id);
    DoctorEntity findById(int id);
    int updateByUserId(int userId);
    DoctorEntity findByPhoneNumber(String phoneNumber);
}
