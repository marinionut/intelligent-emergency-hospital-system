package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.MedicalEquipmentEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("MedicalEquipmentDao")
@Transactional
public class MedicalEquipmentDaoImpl extends AbstractDao implements MedicalEquipmentDao {
    @Override
    public void save(MedicalEquipmentEntity medicalEquipmentEntity) {
        persist(medicalEquipmentEntity);
    }

    @Override
    public List<MedicalEquipmentEntity> findAll() {
        Criteria criteria = getSession().createCriteria(MedicalEquipmentEntity.class);
        return (List<MedicalEquipmentEntity>) criteria.list();
    }

    @Override
    public void delete(int id) {
        getSession().delete(findById(id));
    }

    @Override
    public MedicalEquipmentEntity findById(int id) {
        MedicalEquipmentEntity doctorEntity = (MedicalEquipmentEntity) getSession().createCriteria(MedicalEquipmentEntity.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return doctorEntity;
    }
}
