package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.domain.ports.input.UsersInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Users management APIs")
public class UsersController {
    private final UsersInputPort usersInputPort;

    public UsersController(UsersInputPort usersInputPort) {
        this.usersInputPort = usersInputPort;
    }

    @Operation(summary = "Get a user by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/{uuid}")
    ResponseEntity<User> getUser(@PathVariable String uuid){
        return new ResponseEntity<>(usersInputPort.getById(uuid), HttpStatus.FOUND);
    }

    @Operation(summary = "Create user and related account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Created user and account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfo.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid or duplicated name supplied",
                    content = @Content) })
    @PostMapping(value = "")
    public ResponseEntity<UserInfo> createUser (@RequestBody UserRequest user) {
        return new ResponseEntity<>(usersInputPort.create(user), HttpStatus.CREATED);
    }

}