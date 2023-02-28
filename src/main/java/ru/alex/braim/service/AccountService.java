package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AccountWithPasswordDto;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.utils.decoder.AuthData;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(@Id Long id);

    List<AccountDto> getAccountsByParameters(AccountDto accountDto, @Valid FromSizeParams fromSizeParams);

    AccountDto createAccount(@Valid AccountWithPasswordDto accountDto);

    AccountDto updateAccount(@Valid AccountWithPasswordDto accountDto, @Id Long id, AuthData authData);

    void deleteAccount(@Id Long id, AuthData authData);
}
