package ru.alex.braim.service.Impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.alex.braim.annotation.Id;
import ru.alex.braim.config.security.AccountDetailtPrincImpl;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.dto.AccountWithPasswordDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.exception.AccountException;
import ru.alex.braim.exception.AlreadyExistException;
import ru.alex.braim.exception.ConnectionWithAnimal;
import ru.alex.braim.exception.NotFoundException;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.requestParam.RangeParams;
import ru.alex.braim.service.AccountService;

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
    public List<AccountDto> getAccountsByParameters(AccountDto accountDto, @Valid RangeParams fromSizeParams) {

        List<Account> accountList = accountRepository.getAccountPageByParams(accountDto.getFirstName(),
                accountDto.getLastName(),
                accountDto.getEmail(),
                fromSizeParams.getFrom(),
                fromSizeParams.getSize());

        return accountMapper.toDtoList(accountList);
    }

    @Override
    @Transactional
    public AccountDto createAccount(@Valid AccountWithPasswordDto accountDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            throw new AccountException("");
        }

        if (accountRepository.existsByEmail(accountDto.getEmail())) {
            throw new AlreadyExistException("account with email = " + accountDto.getEmail() + " already exist");
        }

        accountDto.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));

        Account accountEntity = accountMapper.toEntityWithPassword(accountDto);

        return accountMapper.toDto(accountRepository.save(accountEntity));
    }

    @Override
    @Transactional
    public AccountDto updateAccount(@Valid AccountWithPasswordDto accountDto, @Id Long id) {
        Account account = getAccountEntityElseThrowForbidden(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!account.getEmail().equals(authentication.getName())) {
            throw new AccountException("");
        }

        if (accountRepository.existsByEmailAndIdNot(accountDto.getEmail(), id)) {
            throw new AlreadyExistException("account with email = " + accountDto.getEmail() + " already exist");
        }

        account.setEmail(accountDto.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        account.setLastName(accountDto.getLastName());
        account.setFirstName(accountDto.getFirstName());

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void deleteAccount(@Id Long id) {
        Account account = getAccountEntityElseThrowForbidden(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!account.getEmail().equals(authentication.getName())) {
            throw new AccountException("");
        }

        if (accountRepository.accountHaveAnimals(id)) {
            throw new ConnectionWithAnimal("account with id = " + id + " have animal");
        }

        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public Account getAccountEntityById(@Id Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("аккаунт с id = " + id + " не найден"));
    }

    // i dont know why, but tests want throw 403
    private Account getAccountEntityElseThrowForbidden(@Id Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountException(""));
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
