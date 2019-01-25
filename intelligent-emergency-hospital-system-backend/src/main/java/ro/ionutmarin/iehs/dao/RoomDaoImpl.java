package ro.ionutmarin.iehs.dao;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("roomDao")
@Transactional
public class RoomDaoImpl extends AbstractDao implements RoomDao {

    @Override
    public void save(RoomEntity roomEntity) {
        persist(roomEntity);
    }

    @Override
    public List<RoomEntity> findAll() {
        Criteria criteria = getSession().createCriteria(RoomEntity.class);
        return (List<RoomEntity>) criteria.list();
    }

    @Override
    public void delete(int id) {
        getSession().delete(findById(id));
    }

    @Override
    public RoomEntity findById(int id) {
        RoomEntity roomEntity = (RoomEntity) getSession().createCriteria(RoomEntity.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return roomEntity;
    }
}
