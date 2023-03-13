package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.dto.AccountWithPasswordDto;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountInfo(@PathVariable Long accountId) {

        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> getAccountsByParameters(
            AccountDto accountDto,
            FromSizeParams fromSizeParams) {

        return ResponseEntity.ok(accountService.getAccountsByParameters(accountDto, fromSizeParams));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long accountId,
                                                    @RequestBody AccountWithPasswordDto account) {

        return ResponseEntity.ok(accountService.updateAccount(account, accountId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {

        accountService.deleteAccount(accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
