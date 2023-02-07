package ru.alex.braim.service;

import ru.alex.braim.dto.AccountDto;

public interface AccountService {
    AccountDto getAccountById(Long id);
}
