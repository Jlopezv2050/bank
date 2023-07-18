package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Accounts management APIs")
public class AccountsController {
    private final AccountsInputPort accountsInputPort;

    public AccountsController(AccountsInputPort accountsInputPort){
        this.accountsInputPort = accountsInputPort;
    }

    @Operation(summary = "Deposit an amount into account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Created deposit into account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found account uuid",
                    content = @Content) })
    @PostMapping("/{uuid}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String uuid, @RequestParam BigDecimal amount) {
        AccountResponse accountResponse = accountsInputPort.modifyBalanceConcurrent(uuid, amount, MovementType.DEPOSIT);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Transfer amount from an account to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Created transfer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found account",
                    content = @Content) })
    @PostMapping("/{uuid}/transfer/{toUuid}")
    public ResponseEntity<AccountResponse> transferToAccount (@PathVariable String uuid, @PathVariable String toUuid,
                                                              @RequestParam BigDecimal amount){
        AccountResponse accountResponse = accountsInputPort.transferToAccount(uuid, toUuid, amount);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get general balance an movements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Created user and account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDisplay.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found account",
                    content = @Content) })
    @GetMapping(value = "/{uuid}/movements")
    public ResponseEntity<AccountDisplay> getMovements (@PathVariable String uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable paging = PageRequest.of(page, size);
        return new ResponseEntity<>(accountsInputPort.getAccountDisplay(uuid, paging), HttpStatus.OK);
    }

}