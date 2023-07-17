package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final AccountsInputPort accountsInputPort;

    public AccountsController(AccountsInputPort accountsInputPort){
        this.accountsInputPort = accountsInputPort;
    }

    @PostMapping("/{uuid}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String uuid, @RequestParam BigDecimal amount) {
        AccountResponse accountResponse = accountsInputPort.modifyBalanceConcurrent(uuid, amount, MovementType.DEPOSIT);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{uuid}/transfer/{toUuid}")
    public ResponseEntity<AccountResponse> transferToAccount (@PathVariable String uuid, @PathVariable String toUuid,
                                                              @RequestParam BigDecimal amount){
        AccountResponse accountResponse = accountsInputPort.transferToAccount(uuid, toUuid, amount);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{uuid}/movements")
    public AccountDisplay getMovements (@PathVariable String uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable paging = PageRequest.of(page, size);
        return accountsInputPort.getAccountDisplay(uuid, paging);
    }

}