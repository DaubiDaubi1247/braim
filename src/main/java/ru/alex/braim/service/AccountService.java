package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(Long id);

    List<AccountDto> getAccountsByParameters(@Valid AccountDto accountDto, Integer from, Integer size);
}
