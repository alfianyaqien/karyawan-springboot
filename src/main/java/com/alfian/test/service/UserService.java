package com.alfian.test.service;

import com.alfian.test.config.Config;
import com.alfian.test.model.dao.LoginModel;
import com.alfian.test.model.dao.RegisterModel;
import com.alfian.test.model.oauth.Role;
import com.alfian.test.model.oauth.User;
import com.alfian.test.repository.oauth.RoleRepository;
import com.alfian.test.repository.oauth.UserRepository;
import com.alfian.test.util.GenericRs;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    Config config = new Config();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private Oauth2UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public GenericRs genericRs;

    @Value("${BASEURL}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    public Map register(RegisterModel registerModel) {
        Map map = new HashMap();

        try {

            if (userRepository.checkExistingEmail(registerModel.getEmail()) != null) {
                log.error("Email Already Exists");
                return genericRs.templateError("Email Already Exists", "400");
            }
            if (!genericRs.isValidEmail(registerModel.getEmail())) {
                log.error("Email Tidak Valid");
                return genericRs.templateError("Email Tidak Valid", "400");
            }
            if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) {
                log.error("Passwords must be the same");
                return genericRs.templateError("Passwords must be the same", "400");
            }
            if (registerModel.getPassword().equals("") || registerModel.getPassword().equals(null)) {
                log.error("password cannot be empty");
                return genericRs.templateError("password cannot be empty","400");
            }
            if (registerModel.getConfirmPassword().equals("") || registerModel.getConfirmPassword().equals(null)) {
                log.error("Confirm password cannot be empty");
                return genericRs.templateError("Confirm password cannot be empty","400");
            }
//            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            String[] roleNames = {"ROLE_USER"}; // user
            User user = new User();
            user.setUsername(registerModel.getEmail().toLowerCase());
            user.setFullname(registerModel.getFullname());

            //step 1 :
            user.setEnabled(false); // matikan user

            String password = passwordEncoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = roleRepository.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = userRepository.save(user);
            log.info("Register Success");
            return genericRs.templateSuccess(obj);
        } catch (Exception e) {
            logger.error("Eror registerManual :", e);
            return genericRs.templateError("Error : " + e, "500");
        }
    }

    public Map login(LoginModel loginModel) {

        try {
            Map<String, Object> map = new HashMap<>();
            User checkUser = userRepository.findOneByUsername(loginModel.getEmail());

            if (!genericRs.isValidEmail(loginModel.getEmail())) {
                log.error("Email Tidak Valid");
                return genericRs.templateError("Email Tidak Valid","400");
            }
            if (checkUser == null) {
                log.error("Email Not Found");
                return genericRs.templateError("Not Found","400");
            }
            if (loginModel.getPassword().equals("") || loginModel.getPassword().equals(null)) {
                log.error("password cannot be empty");
                return genericRs.templateError("password cannot be empty","400");
            }

//            if (checkUser.isBlocked()) {
//                return response.templateError("Your account is blocked, please contact Admin!");
//            }

            if ((checkUser != null) && (passwordEncoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return genericRs.templateError(map,"400");
                }
            }
            if (!(passwordEncoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return genericRs.templateError("Wrong Password","400");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getEmail() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });


            if (response.getStatusCode() == HttpStatus.OK) {
                User user = userRepository.findOneByUsername(loginModel.getEmail());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                userRepository.save(user);


                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));
                map.put("user", user);


                System.out.println(baseUrl);
                return genericRs.templateSuccess(map);
            } else {
                System.out.println(baseUrl);
                return null;
            }

        } catch (
                HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return genericRs.templateError("invalid login","400");
            }
            return genericRs.templateError(e,"400");
        } catch (
                Exception e) {
            e.printStackTrace();

            return genericRs.templateError(e,"400");
        }
    }
}


