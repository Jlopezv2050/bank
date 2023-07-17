package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.domain.ports.input.UsersInputPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersInputPort usersInputPort;

    public UsersController(UsersInputPort usersInputPort) {
        this.usersInputPort = usersInputPort;
    }

    @GetMapping("/{uuid}")
    ResponseEntity<User> getUser(@PathVariable String uuid){
        return new ResponseEntity<>(usersInputPort.getById(uuid), HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<UserInfo> createUser (@RequestBody UserRequest user) {
        return new ResponseEntity<>(usersInputPort.create(user), HttpStatus.CREATED);
    }

}