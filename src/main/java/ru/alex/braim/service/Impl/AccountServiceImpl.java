package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.config.AccountDetailtPrincImpl;
import ru.alex.braim.dto.AccountCreateDto;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.specification.AccountSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public AccountDto getAccountById(@Id Long id) {
        return accountMapper.toDto(getAccountEntityById(id));
    }

    @Override
    @Transactional
    public List<AccountDto> getAccountsByParameters(AccountDto accountDto, @Valid FromSizeParams fromSizeParams) {

        Pageable pageable = PageRequest.of(fromSizeParams.getFrom(), fromSizeParams.getSize());

        Page<Account> accountList = accountRepository.findAll(AccountSpecification.
                getAccountSpecificationByParameters(accountDto), pageable);

        return accountMapper.toDtoList(accountList.getContent());
    }

    @Override
    @Transactional
    public AccountDto createAccount(@Valid AccountCreateDto accountDto) {

        if (accountRepository.existsByEmail(accountDto.getEmail())) {
            throw new AlreadyExistException("account with email = " + accountDto.getEmail() + " already exist");
        }

        Account accountEntity = accountMapper.toEntityWithPassword(accountDto);

        return accountMapper.toDto(accountRepository.save(accountEntity));
    }

    private Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("\n\n\n\n-----------------" + username + "-------------------------\n\n\n\n\n");

        Account account = accountRepository.findByEmail(username);

        return new AccountDetailtPrincImpl(account);
    }
}
