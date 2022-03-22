package com.asebas.api.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.asebas.api.Constants;
import com.asebas.api.domain.User;
import com.asebas.api.exceptions.CAuthException;
import com.asebas.api.services.User.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(username, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String password = (String) userMap.get("password");
        String email = (String) userMap.get("email");
        Integer dni = (Integer) userMap.get("dni");
        String address = (String) userMap.get("address");
        String phone = (String) userMap.get("phone");

        User user = userService.registerUser(username, firstName, lastName, password, email, dni, address, phone);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.fetchAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/u/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.fetchUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/u/{userId}")
    public ResponseEntity<Map<String, Boolean>> updateUser(HttpServletRequest request,
            @PathVariable("userId") Integer userId, @RequestBody User user) throws CAuthException {
        if (userId != (Integer) request.getAttribute("userId")) {
            throw new CAuthException("Can only update your own data");
        }

        userService.updateUser(userId, user);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // Unused for now
    @DeleteMapping("/u/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser() {
        return null;
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp)).setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId()).claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName()).compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }

}
