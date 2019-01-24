package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.MedicationEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("medicationDao")
@Transactional
public class MedicationDaoImpl extends AbstractDao implements MedicationDao {

    @Override
    public void save(MedicationEntity medicationEntity) {
        persist(medicationEntity);
    }

    @Override
    public List<MedicationEntity> findAll() {
        Criteria criteria = getSession().createCriteria(MedicationEntity.class);
        return (List<MedicationEntity>) criteria.list();
    }

}
