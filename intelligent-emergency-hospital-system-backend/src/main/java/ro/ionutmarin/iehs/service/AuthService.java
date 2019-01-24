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
                if (users.get(0).getParola().equals(loginRequest.getPassword())) {
                    String jwt = JwtService.createJWT("ionut", "ionut", gson.toJson(loginRequest), 1000000000);

                    //  var token = jwt.encode(results[0], dbConfig.database);
                    //  res.json({success: true, token: 'JWT ' + token, msg: 'Authentification '});
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


//router.get('/api/memberinfo', passport.authenticate('jwt', {session: false}), function(req, res) {
//        var getToken = function(headers) {
//        if(headers && headers.authorization) {
//        var parted = headers.authorization.split(' ');
//        if(parted.length == 2) {
//        return parted[1];
//        } else {
//        return null;
//        }
//        } else {
//        return null;
//        }
//        }
//
//        var token = getToken(req.headers);
//        if(token) {
//        var decoded = jwt.decode(token, dbConfig.database);
//        var select_sql = "SELECT * FROM users WHERE username = ?";
//        select_sql = mysql.format(select_sql, decoded.username);
//        dbConnection.query(select_sql, function(error, results, fields) {
//        if(results.length == 1) {
//        var info = {id: results[0].id, username: results[0].username, familie: results[0].family};
//        res.json({success: true, msg: info});
//        } else {
//        res.status(403).send({success: false, msg: 'Nume utilizator inexistent!'});
//        }
//        });
//        } else {
//        res.status(403).send({success: false, msg: 'Nu exista token!'});
//        }
//        });







//
//    router.post('/api/authenticate', function(req, res) {
//        var select_sql = "SELECT * FROM users WHERE username = ?";
//        if(req.body.username) {
//            select_sql = mysql.format(select_sql, req.body.username);
//            dbConnection.query(select_sql,  function(error, results, fields) {
//                if(results.length == 0) {
//                    res.send({success: false, msg:'Nume utilizator gresit!'});
//                } else {
//                    if(req.body.password) {
//                        if(results[0].password === req.body.password) {
//                            gutil.log("Utilizator autentificat: "+results[0].username);
//                            var token = jwt.encode(results[0], dbConfig.database);
//                            res.json({success: true, token: 'JWT ' + token, msg: 'Authentification '});
//                        } else {
//                            res.send({success: false, msg:'Parola gresita!'});
//                        }
//                    } else {
//                        res.send({success: false, msg:'Parola necompletata!'});
//                    }
//                }
//            });
//        } else {
//            res.send({success: false, msg:'Nume utilizator necompletat!'});
//        }
//    });

