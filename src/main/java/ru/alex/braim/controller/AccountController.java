package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.braim.dto.AccountDto;
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
}
