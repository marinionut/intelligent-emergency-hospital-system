package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.AppointmentEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;

import java.util.List;

public interface AppointmentDao {
    void save(AppointmentEntity appointmentEntity);
    List<AppointmentEntity> findAll();
    void delete(int id);
    AppointmentEntity findById(int id);
    AppointmentEntity findByRoomIdAndBedNumber(int roomId, int bedNumber);
}
