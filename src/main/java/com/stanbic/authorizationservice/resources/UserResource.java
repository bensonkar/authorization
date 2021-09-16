package com.stanbic.authorizationservice.resources;

import com.stanbic.authorizationservice.entities.Authentication;
import com.stanbic.authorizationservice.entities.SystemUser;
import com.stanbic.authorizationservice.impl.UserServiceImpl;
import com.stanbic.authorizationservice.repositories.AuthenticationRepository;
import com.stanbic.authorizationservice.repositories.UserRepository;
import com.stanbic.authorizationservice.wrappers.ChangePassword;
import com.stanbic.authorizationservice.wrappers.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author bkariuki
 */
@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    public UserResource(UserServiceImpl userService, UserRepository userRepository,
                        AuthenticationRepository authenticationRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        ResponseWrapper response = new ResponseWrapper().data(userService.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ResponseWrapper response = new ResponseWrapper();
        SystemUser user = userService.findById(id);
        if (Objects.isNull(user)) {
            response.code(404).message("user with provided id does not exist");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        response.data(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SystemUser user) {
        ResponseWrapper response = new ResponseWrapper();
        SystemUser exist = userRepository.findByEmail(user.getEmail());
        if (Objects.nonNull(exist)) {
            response.code(409).message("user with provided email already exist");
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }
        user = userService.create(user);
        response.code(201).data(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody SystemUser user, @PathVariable Long id) {
        ResponseWrapper response = new ResponseWrapper();
        SystemUser data = userService.findById(id);
        if (Objects.isNull(user)) {
            response.code(404).message("user with provided id does not exist");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        userService.update(user, id);
        response.data(data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody ChangePassword changePassword) {
        ResponseWrapper response = new ResponseWrapper();
        Authentication user = authenticationRepository.findByUserId(id);
        if (Objects.isNull(user)) {
            response.code(404).message("user with provided id does not exist");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        user = userService.changePassword(changePassword, id);
        response.data(user);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseWrapper response = new ResponseWrapper();
        SystemUser user = userService.findById(id);
        if (Objects.isNull(user)) {
            response.code(404).message("user with provided id does not exist");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data")
    public ResponseEntity loggedInData() {
        Object data = userService.loggedInData();
        ResponseWrapper response = new ResponseWrapper().data(data);
        return ResponseEntity.ok(response);
    }

}
