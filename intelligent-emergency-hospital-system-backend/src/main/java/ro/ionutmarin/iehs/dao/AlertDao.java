package ro.ionutmarin.iehs.dao;

import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.AppointmentEntity;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface AlertDao {
    void save(AlertEntity alertEntity);
    List<AlertEntity> findAll();
    AlertEntity findAlertByUid(String uid);
    List<AlertEntity> findAlertByUsernameAndStatus(String username, int status);
}