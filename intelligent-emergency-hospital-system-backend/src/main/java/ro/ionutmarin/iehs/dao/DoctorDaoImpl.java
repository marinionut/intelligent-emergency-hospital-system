package ro.ionutmarin.iehs.dao;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public void delete(int id) {
        getSession().delete(findById(id));
    }

    @Override
    public DoctorEntity findById(int id) {
        DoctorEntity doctorEntity = (DoctorEntity) getSession().createCriteria(DoctorEntity.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return doctorEntity;
    }

    @Override
    public int updateByUserId(int userId) {
        return getSession()
                .createQuery("update DoctorEntity d set d.userId=:value where d.userId=:userId")
                .setInteger("userId", userId)
                .setInteger("value", -1)
                .executeUpdate();
    }
}
