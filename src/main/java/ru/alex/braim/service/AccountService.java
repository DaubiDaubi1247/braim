package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AccountCreateDto;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.requestParam.FromSizeParams;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(@Id Long id);

    List<AccountDto> getAccountsByParameters(AccountDto accountDto, @Valid FromSizeParams fromSizeParams);

    AccountDto createAccount(@Valid AccountCreateDto accountDto);

    AccountDto updateAccount(@Valid AccountCreateDto accountDto, @Id Long id);
}
