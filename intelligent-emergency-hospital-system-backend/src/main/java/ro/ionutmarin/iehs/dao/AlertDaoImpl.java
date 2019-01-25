package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.AppointmentEntity;

import javax.transaction.Transactional;
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

}