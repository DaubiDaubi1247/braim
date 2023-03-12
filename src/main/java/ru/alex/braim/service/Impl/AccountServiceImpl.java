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
import ru.alex.braim.exception.*;
import ru.alex.braim.mapper.AccountMapper;
import ru.alex.braim.repository.AccountRepository;
import ru.alex.braim.requestParam.FromSizeParams;
import ru.alex.braim.service.AccountService;
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

        List<Account> accountPage = accountRepository.getAccountPageByParams(accountDto.getFirstName(),
                accountDto.getLastName(),
                accountDto.getEmail(),
                fromSizeParams.getFrom(),
                fromSizeParams.getSize());
//        Page<Account> accountPage = accountRepository.findAll(AccountSpecification
//                .getAccountSpecificationByParameters(accountDto), pageable);

//        List<Account> accountPage = accountRepository.findAll(AccountSpecification
//                .getAccountSpecificationByParameters(accountDto));

        return accountMapper.toDtoList(accountPage);
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
    public AccountDto updateAccount(@Valid AccountWithPasswordDto accountDto, @Id Long id, AuthData authData) {
        Account account = getAccountEntityWithThrow(id);

        if (accountEmailNotEqualWithEmailFromHeader(authData, account)) {
            throw new NotEqualsAccounts("not equals emails in updated account and transferred");
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

    private boolean accountEmailNotEqualWithEmailFromHeader(AuthData authData, Account account) {
        return !account.getEmail().equals(authData.getEmail());
    }

    @Override
    @Transactional
    public void deleteAccount(@Id Long id) {
        Account account = getAccountEntityWithThrow(id);

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
    private Account getAccountEntityWithThrow(@Id Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("аккаунт с id = " + id + " не найден"));
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
