package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.entity.Account_;

public class AccountSpecification {

    private static Specification<Account> equalsLikeFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Account_.firstName), firstName);
    }

    private static Specification<Account> equalsLikeSecondName(String secondName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Account_.lastName), secondName);
    }

    private static Specification<Account> equalsLikeEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Account_.email), email);
    }
    public static Specification<Account> getAccountSpecificationByParameters(AccountDto accountDto) {
        return Specification.where(equalsLikeFirstName(accountDto.getFirstName()))
                .and(equalsLikeSecondName(accountDto.getLastName()))
                .and(equalsLikeEmail(accountDto.getEmail()));
    }
}