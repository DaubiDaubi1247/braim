package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.config.security.AccountDetailtPrincImpl;
import ru.alex.braim.dto.AccountCreateDto;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.NotEqualsAccounts;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;
import ru.alex.braim.specification.AccountSpecification;
import ru.alex.braim.utils.decoder.AuthData;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final PasswordEncoder bCryptPasswordEncoder;

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

        accountDto.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));

        Account accountEntity = accountMapper.toEntityWithPassword(accountDto);

        return accountMapper.toDto(accountRepository.save(accountEntity));
    }

    @Override
    @Transactional
    public AccountDto updateAccount(@Valid AccountCreateDto accountDto, @Id Long id, AuthData authData) {
        Account account = getAccountEntityById(id);

        if (!account.getEmail().equals(authData.getEmail())) {
            throw new NotEqualsAccounts("not equals emails in updated account and transferred");
        }

        return null;
    }

    private Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = getAccountEntityByEmail(email);

        return new AccountDetailtPrincImpl(account);
    }

    private Account getAccountEntityByEmail(String email) {
       return accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("account with email = " + email + " not found"));
    }
}
