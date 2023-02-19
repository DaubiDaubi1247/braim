package ru.alex.braim.service;

import jakarta.validation.Valid;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.requestParam.FromSizeParams;

import java.util.List;

public interface AccountService {
    AccountDto getAccountById(@Id Long id);

    List<AccountDto> getAccountsByParameters(@Valid AccountDto accountDto, @Valid FromSizeParams fromSizeParams);
}
