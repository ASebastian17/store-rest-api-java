package com.asebas.api.repositories.User;

import java.util.List;

import com.asebas.api.domain.User;
import com.asebas.api.exceptions.CAuthException;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface UserRepository {

    Integer create(String username, String firstName, String lastName, String password, String email, Integer dni,
            String address, String phone) throws CAuthException;

    User findByUsernameAndPassword(String username, String password) throws CAuthException;

    User findById(Integer userId) throws CResourceNotFoundException;

    Integer getCountByUsername(String username);

    Integer getCountByEmail(String email);

    List<User> findAll();

    void update(Integer userId, User user) throws CBadRequestException;

    // void removeById(Integer userId);
}
