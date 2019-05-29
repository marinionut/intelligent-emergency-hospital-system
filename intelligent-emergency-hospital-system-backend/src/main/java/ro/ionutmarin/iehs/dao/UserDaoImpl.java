package ro.ionutmarin.iehs.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ro.ionutmarin.iehs.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    public void save(UserEntity userEntity) {
         persist(userEntity);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        return (List<UserEntity>) criteria.list();
    }

    @Override
    public List<UserEntity> findByUsername(String username) {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("username", username));
        return (List<UserEntity>) criteria.list();

    }

    @Override
    public UserEntity findById(int id) {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("id", id));
        return (UserEntity) criteria.uniqueResult();

    }

    @Override
    public int deleteUser(int id) {
        String hql = "delete from UserEntity u where u.id = :id";
        return getSession().createQuery(hql).setInteger("id", id).executeUpdate();
    }

//    @Override
//    public int editUserRole(String username, int role) {
//        return getSession().createQuery("update UserEntity u set u.role =:role where u.username =:username")
//                .setParameter("username", username)
//                .setParameter("role", role)
//                .executeUpdate();
//    }
}
