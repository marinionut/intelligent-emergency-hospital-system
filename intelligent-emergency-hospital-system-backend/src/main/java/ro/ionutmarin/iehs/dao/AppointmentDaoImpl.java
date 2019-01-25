package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.AppointmentEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("appointmentDao")
@Transactional
public class AppointmentDaoImpl  extends AbstractDao implements AppointmentDao {

    @Override
    public void save(AppointmentEntity appointmentEntity) {
        persist(appointmentEntity);
    }

    @Override
    public List<AppointmentEntity> findAll() {
        Criteria criteria = getSession().createCriteria(AppointmentEntity.class);
        return (List<AppointmentEntity>) criteria.list();
    }

}