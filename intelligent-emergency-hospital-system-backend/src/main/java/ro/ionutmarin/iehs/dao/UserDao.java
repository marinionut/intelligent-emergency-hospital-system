package ro.ionutmarin.iehs.dao;

import ro.ionutmarin.iehs.entity.UserEntity;

import java.util.List;

public interface UserDao {
    void save(UserEntity userEntity);
    List<UserEntity> findAllUsers();
    List<UserEntity> findByUsername(String username);
    int deleteUser(int id);
    UserEntity findById(int id);
//    int editUserRole(String username, int role);
    List<UserEntity> finByRole(String role);
}
