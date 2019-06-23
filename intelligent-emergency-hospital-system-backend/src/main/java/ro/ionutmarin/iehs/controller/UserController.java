package ro.ionutmarin.iehs.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ionutmarin.iehs.dao.DoctorDao;
import ro.ionutmarin.iehs.dao.UserDao;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.request.UserRequest;
import ro.ionutmarin.iehs.service.AuthService;
import ro.ionutmarin.iehs.util.PasswordUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DoctorDao doctorDao;

    @RequestMapping("/user/add")
    public ResponseEntity addUser(@RequestBody UserRequest request, @RequestHeader HttpHeaders headers) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setParola(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(request.getRole());

        try {
            if (!userDao.findByUsername(request.getUsername()).isEmpty()) {
                String errorMessage = "Acest nume de utilizator exista deja!";
                return ResponseEntity.status(500).body(new Gson().toJson(errorMessage));
            }
            userDao.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping("/user/delete")
    public ResponseEntity deleteUser(@RequestParam("id") Integer id, @RequestHeader HttpHeaders headers) {
        Integer userDeleted = userDao.deleteUser(id);

        doctorDao.updateByUserId(id);

        if (userDeleted >= 1)
            return ResponseEntity.ok(userDeleted);
        else return ResponseEntity.notFound().build();
    }

//    @RequestMapping("/user/edit")
//    public ResponseEntity editUserRole(@RequestBody UserRequest request, @RequestHeader HttpHeaders headers) {
//        Integer userEdited = userDao.(request.getUsername());
//        if (userEdited >= 1)
//            return ResponseEntity.ok(userEdited);
//        else return ResponseEntity.notFound().build();
//    }

    @RequestMapping("/user/all")
    public ResponseEntity findAll(@RequestHeader HttpHeaders headers) {
        List<UserEntity> usersList = userDao.findAllUsers();
        return ResponseEntity.ok().body(usersList);
    }

    @RequestMapping("/user/role")
    public List<UserEntity> findAll(@RequestParam("role") String role) {
        List<UserEntity> usersList = userDao.finByRole(role);
        usersList.stream().forEach(u -> u.setParola(""));
        return usersList;
    }

    @RequestMapping("/user")
    public UserEntity findAll(@RequestParam("id") int id) {
        UserEntity user = userDao.findById(id);
        return user;
    }
}

