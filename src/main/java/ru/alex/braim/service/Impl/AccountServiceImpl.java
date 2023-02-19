package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.specification.AccountSpecification;
import ru.alex.braim.utils.ListUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDto getAccountById(@Id Long id) {
        return accountMapper.toDto(getAccountEntityById(id));
    }

    @Override
    @Transactional
    public List<AccountDto> getAccountsByParameters(@Valid AccountDto accountDto, @Valid FromSizeParams fromSizeParams) {
        List<Account> accountList = accountRepository.findAll(AccountSpecification.
                getAccountSpecificationByParameters(accountDto));

        return accountMapper.toDtoList(ListUtils.skipAndGetElements(accountList, fromSizeParams.getFrom(), fromSizeParams.getSize()));
    }

    private Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }
}
