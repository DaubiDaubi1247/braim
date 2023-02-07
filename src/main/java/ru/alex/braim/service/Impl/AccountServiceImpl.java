package ru.alex.braim.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.service.AccountService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Override
    public AccountDto getAccountById(Long id) {
        return accountMapper.toDto(getAccountEntityById(id));
    }

    private Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }
}
