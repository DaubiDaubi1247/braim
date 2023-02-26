package ru.alex.braim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountInfo(@PathVariable Long accountId,
                                                     @RequestHeader Map<String, String> headers) {

//        byte[] a = Base64.getDecoder().decode(headers.get("authorization").split(" ")[1]);
//        String ab = new String(a);
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> getAccountsByParameters(
            AccountDto accountDto,
            FromSizeParams fromSizeParams) {

        return ResponseEntity.ok(accountService.getAccountsByParameters(accountDto, fromSizeParams));
    }
}
