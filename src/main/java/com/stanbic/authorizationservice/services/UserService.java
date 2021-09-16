package com.stanbic.authorizationservice.services;

import com.stanbic.authorizationservice.entities.Authentication;
import com.stanbic.authorizationservice.entities.SystemUser;
import com.stanbic.authorizationservice.impl.UserServiceImpl;
import com.stanbic.authorizationservice.repositories.AuthenticationRepository;
import com.stanbic.authorizationservice.repositories.UserRepository;
import com.stanbic.authorizationservice.wrappers.ChangePassword;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author bkariuki
 */
@Component
@Transactional
public class UserService implements UserServiceImpl {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationRepository authenticationRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * return all users from the database
     */
    @Override
    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * find user by id
     *
     * @param id
     */
    @Override
    public SystemUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provided id does not exists " + id));
    }


    /**
     * creating new authorization user with authentication credential
     */
    @Override
    public SystemUser create(SystemUser user) {
        try {
            String password = passwordEncoder.encode(user.getPassword());
            user = userRepository.save(user);
            Authentication authentication = new Authentication()
                    .userId(user.getId())
                    .username(user.getEmail())
                    .password(password);
            authenticationRepository.save(authentication);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("unable to save user  " + e.getMessage());
        }
    }

    /**
     * updating authorization user and the authentication data will also change
     */
    @Override
    public SystemUser update(SystemUser user, Long id) {
        SystemUser data = userRepository.findById(id).get();
        Authentication authentication = authenticationRepository.findByUserId(id);
        if (user.getEmail() != null) {
            data.setEmail(user.getEmail());
            authentication.setUsername(user.getEmail());
        }
        if (user.getFirstName() != null) {
            data.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            data.setLastName(user.getLastName());
        }
        if (user.getPhone() != null) {
            data.setPhone(user.getPhone());
        }
        //save user and the authentication data
        userRepository.save(data);
        authenticationRepository.save(authentication);
        return data;
    }

    /**
     * change password for authentication.
     *
     * @param changePassword,userId
     */
    @Override
    public Authentication changePassword(ChangePassword changePassword, Long userId) {
        try {
            Authentication data = authenticationRepository.findByUserId(userId);
            if (Objects.nonNull(changePassword.getPassword())) {
                data.setPassword(passwordEncoder.encode(changePassword.getPassword()));
            }
            data = authenticationRepository.save(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * delete user from database together with authentication credentials.
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        try {
            authenticationRepository.deleteAllByUserId(id);
            userRepository.deleteAllById(id);
            System.out.println("data deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * will return the data of the logged-in user by passing only the token
     */
    @Override
    public Object loggedInData() {
        Object data = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.nonNull(data)) {
            return data;
        }
        return null;
    }

}
