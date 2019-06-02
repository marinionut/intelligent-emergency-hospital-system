package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.AppointmentEntity;

import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Repository("alertDao")
@Transactional
public class AlertDaoImpl extends AbstractDao implements AlertDao {

    @Override
    public void save(AlertEntity alertEntity) {
        persist(alertEntity);
    }

    @Override
    public List<AlertEntity> findAll() {
        Criteria criteria = getSession().createCriteria(AlertEntity.class);
        return (List<AlertEntity>) criteria.list();
    }

    @Override
    public AlertEntity findAlertByUid(String uid) {
        AlertEntity alertEntity = (AlertEntity) getSession().createCriteria(AlertEntity.class)
                .add(Restrictions.eq("uid", uid))
                .uniqueResult();
        return alertEntity;
    }
}