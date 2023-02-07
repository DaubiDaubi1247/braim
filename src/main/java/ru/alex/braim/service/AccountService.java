package ru.alex.braim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.repository.AccountRepository;

@Service
@RequiredArgsConstructor
@Validated
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDto getAccountById(Long id) {

    }
}
