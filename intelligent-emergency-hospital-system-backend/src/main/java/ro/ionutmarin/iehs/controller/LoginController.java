package ro.ionutmarin.iehs.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.UserDao;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.request.LoginRequest;
import ro.ionutmarin.iehs.response.LoginResponse;
import ro.ionutmarin.iehs.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/authenticate")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, @RequestHeader HttpHeaders headers) {
        LoginResponse loginResponse = authService.login(request);
        return  ResponseEntity.ok(loginResponse);
    }

    @RequestMapping("/memberinfo")
    public ResponseEntity<LoginResponse> login(@RequestHeader HttpHeaders headers) {
        LoginResponse loginResponse = new LoginResponse();

        String token = headers.get("Authorization").get(0).split(" ")[1];
        LoginRequest loginRequest = authService.verify(token);


        if (loginRequest == null) {
            loginResponse.setSuccess(Boolean.FALSE);
            loginResponse.setMsg("Nu exista token!");
            return ResponseEntity.status(403).body(loginResponse);
        }

        List<UserEntity> users = userDao.findByUsername(loginRequest.getUsername());
        if (users.get(0).getParola().equals(loginRequest.getPassword())) {
            loginResponse.setSuccess(Boolean.TRUE);
            loginResponse.setMsg(new Gson().toJson(users.get(0)));

            return  ResponseEntity.ok(loginResponse);

        } else {
            loginResponse.setSuccess(Boolean.FALSE);
            loginResponse.setMsg("Nume utilizator inexistent!");
            return  ResponseEntity.status(403).body(loginResponse);
        }

    }
}
