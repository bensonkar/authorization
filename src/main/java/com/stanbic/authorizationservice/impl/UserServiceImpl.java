package com.stanbic.authorizationservice.impl;

import com.stanbic.authorizationservice.entities.Authentication;
import com.stanbic.authorizationservice.entities.SystemUser;
import com.stanbic.authorizationservice.wrappers.ChangePassword;

import java.util.List;

/**
 * @author bkariuki
 */
public interface UserServiceImpl {

    List<SystemUser> findAll();

    SystemUser findById(Long id);

    SystemUser create(SystemUser user);

    SystemUser update(SystemUser user, Long id);

    Authentication changePassword(ChangePassword changePassword, Long id);

    void delete(Long id);

    Object loggedInData();
}
