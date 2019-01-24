package ro.ionutmarin.iehs.dao;


import org.hibernate.Criteria;
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

}
