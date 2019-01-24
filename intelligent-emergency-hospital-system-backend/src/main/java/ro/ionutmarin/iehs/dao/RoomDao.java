package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import java.util.List;

public interface RoomDao {
    void save(RoomEntity roomEntity);
    List<RoomEntity> findAll();
}
