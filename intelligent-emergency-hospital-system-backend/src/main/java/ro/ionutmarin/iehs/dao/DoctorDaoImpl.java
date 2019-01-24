package ro.ionutmarin.iehs.dao;


import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.DoctorEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("doctorDao")
@Transactional
public class DoctorDaoImpl  extends AbstractDao implements DoctorDao {

    @Override
    public void save(DoctorEntity doctorEntity) {
        persist(doctorEntity);
    }

    @Override
    public List<DoctorEntity> findAll() {
        Criteria criteria = getSession().createCriteria(DoctorEntity.class);
        return (List<DoctorEntity>) criteria.list();
    }

}
