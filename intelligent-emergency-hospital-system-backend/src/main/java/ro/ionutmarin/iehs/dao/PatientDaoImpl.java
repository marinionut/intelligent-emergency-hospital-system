package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.PatientEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("patientDao")
@Transactional
public class PatientDaoImpl  extends AbstractDao implements PatientDao {

    @Override
    public void save(PatientEntity patientEntity) {
        persist(patientEntity);
    }

    @Override
    public List<PatientEntity> findAll() {
        Criteria criteria = getSession().createCriteria(PatientEntity.class);
        return (List<PatientEntity>) criteria.list();
    }

    @Override
    public void delete(int id) {
        getSession().delete(findById(id));
    }

    @Override
    public PatientEntity findById(int id) {
        PatientEntity patientEntity = (PatientEntity) getSession().createCriteria(PatientEntity.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return patientEntity;
    }
}