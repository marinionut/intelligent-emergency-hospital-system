package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.DoctorStuffEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("doctorStuffDao")
@Transactional
public class DoctorStuffDaoImpl extends AbstractDao implements DoctorStuffDao {

    @Override
    public void save(DoctorStuffEntity doctorStuffEntity) {
        persist(doctorStuffEntity);
    }

    @Override
    public List<DoctorStuffEntity> findAll() {
        Criteria criteria = getSession().createCriteria(DoctorStuffEntity.class);
        return (List<DoctorStuffEntity>) criteria.list();
    }

}
