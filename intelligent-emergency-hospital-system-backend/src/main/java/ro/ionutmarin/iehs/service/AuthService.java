package ro.ionutmarin.iehs.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.dao.UserDao;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.request.LoginRequest;
import ro.ionutmarin.iehs.response.LoginResponse;
import ro.ionutmarin.iehs.util.PasswordUtil;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        Gson gson = new GsonBuilder().create();

        if (loginRequest.getUsername() == null) {
            loginResponse.setMsg("Nume utilizator necompletat!");
            loginResponse.setSuccess(Boolean.FALSE);
        }

        List<UserEntity> users = userDao.findByUsername(loginRequest.getUsername());

        if (users.isEmpty()) {
            loginResponse.setMsg("Nume utilizator gresit!");
            loginResponse.setSuccess(Boolean.FALSE);
        } else {
            if (loginRequest.getPassword() != null) {
//                PasswordUtil.matchingPassword(users.get(0).getParola(), loginRequest.getPassword())
                if (PasswordUtil.matchingPassword(users.get(0).getParola(), loginRequest.getPassword())) {
                    String jwt = JwtService.createJWT("ionut", "ionut", gson.toJson(loginRequest), 1000000000);

                    loginResponse.setMsg("Authentification ");
                    loginResponse.setSuccess(Boolean.TRUE);
                    loginResponse.setToken("JWT " + jwt);
                } else {
                    loginResponse.setMsg("Parola gresita!'");
                    loginResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                loginResponse.setMsg("Parola necompletata!'");
                loginResponse.setSuccess(Boolean.FALSE);
            }

        }

        return loginResponse;
    }

    public LoginRequest verify(String token) {
        Gson gson = new Gson();
        LoginResponse loginResponse = new LoginResponse();
        Claims claims = JwtService.parseJWT(token);
        LoginRequest loginRequest = gson.fromJson(claims.getSubject(), LoginRequest.class);

        return loginRequest;

    }
}
