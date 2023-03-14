package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.dto.AccountWithPasswordDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.requestParam.RangeParams;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(@Id Long id);

    Account getAccountEntityById(@Id Long id);

    List<AccountDto> getAccountsByParameters(AccountDto accountDto, @Valid RangeParams fromSizeParams);

    AccountDto createAccount(@Valid AccountWithPasswordDto accountDto);

    AccountDto updateAccount(@Valid AccountWithPasswordDto accountDto, @Id Long id);

    void deleteAccount(@Id Long id);
}
