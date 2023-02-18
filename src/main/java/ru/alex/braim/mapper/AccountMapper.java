package ru.alex.braim.mapper;

import org.mapstruct.Mapper;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDtoList(List<Account> accountList);
}
