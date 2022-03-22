package com.asebas.api.services.User;

import java.util.List;
import java.util.regex.Pattern;

import com.asebas.api.domain.User;
import com.asebas.api.exceptions.CAuthException;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String username, String password) throws CAuthException {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User registerUser(String username, String firstName, String lastName, String password, String email,
            Integer dni, String address, String phone) throws CAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null)
            email = email.toLowerCase();

        if (!pattern.matcher(email).matches())
            throw new CAuthException("Invalid email format");

        Integer emailCount = userRepository.getCountByEmail(email);

        if (emailCount > 0)
            throw new CAuthException("Email already in use");

        Integer usernameCount = userRepository.getCountByUsername(username);

        if (usernameCount > 0)
            throw new CAuthException("Username already in use");

        Integer userId = userRepository.create(username, firstName, lastName, password, email, dni, address, phone);
        return userRepository.findById(userId);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User fetchUserById(Integer userId) throws CResourceNotFoundException {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(Integer userId, User user) throws CBadRequestException {
        userRepository.update(userId, user);
    }

    // Unused for now
    // @Override
    // public void deleteUser(Integer userId) throws CResourceNotFoundException {}

}
