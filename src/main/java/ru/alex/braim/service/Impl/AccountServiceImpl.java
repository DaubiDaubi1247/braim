package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.specification.AccountSpecification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Override
    @Transactional
    public AccountDto getAccountById(Long id) {
        return accountMapper.toDto(getAccountEntityById(id));
    }

    @Override
    @Transactional
    public List<AccountDto> getAccountsByParameters(AccountDto accountDto, Integer from, Integer size) {
        List<Account> accountList = accountRepository.findAll(AccountSpecification.
                getAccountSpecificationByParameters(accountDto));

        return accountList.stream()
                .skip(from)
                .limit(size)
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    private Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }
}
