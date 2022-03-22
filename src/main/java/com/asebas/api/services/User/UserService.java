package com.asebas.api.services.User;

import java.util.List;

import com.asebas.api.domain.User;
import com.asebas.api.exceptions.CAuthException;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface UserService {

    User validateUser(String username, String password) throws CAuthException;

    User registerUser(String username, String firstName, String lastName, String password, String email, Integer dni,
            String address, String phone) throws CAuthException;

    List<User> fetchAllUsers();

    User fetchUserById(Integer userId) throws CResourceNotFoundException;

    void updateUser(Integer userId, User user) throws CBadRequestException;

    // void deleteUser(Integer userId) throws CResourceNotFoundException;
}
