package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.AppointmentEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository("appointmentDao")
@Transactional
public class AppointmentDaoImpl  extends AbstractDao implements AppointmentDao {

    @Override
    public void save(AppointmentEntity appointmentEntity) {
        appointmentEntity.setTimestamp(new Timestamp(DateTime.now().getMillis()));
        persist(appointmentEntity);
    }

    @Override
    public List<AppointmentEntity> findAll() {
        Criteria criteria = getSession().createCriteria(AppointmentEntity.class);
        return (List<AppointmentEntity>) criteria.list();
    }

    @Override
    public void delete(int id) {
        getSession().delete(findById(id));
    }

    @Override
    public AppointmentEntity findById(int id) {
        AppointmentEntity appointmentEntity = (AppointmentEntity) getSession().createCriteria(AppointmentEntity.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return appointmentEntity;
    }

}