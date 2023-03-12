package ru.alex.braim.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.alex.braim.dto.AccountDto;
import ru.alex.braim.entity.Account;
import ru.alex.braim.entity.Account_;
import ru.alex.braim.utils.StringUtils;

public class AccountSpecification {

    private static Specification<Account> equalsLikeFirstName(String firstName) {
        if (firstName == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get(Account_.firstName)), StringUtils.toLowerWithPercent(firstName));
    }

    private static Specification<Account> equalsLikeSecondName(String secondName) {
        if (secondName == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get(Account_.lastName)), StringUtils.toLowerWithPercent(secondName));
    }

    private static Specification<Account> equalsLikeEmail(String email) {
        if (email == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get(Account_.email)), StringUtils.toLowerWithPercent(email));
    }

    public static Specification<Account> getAccountSpecificationByParameters(AccountDto accountDto) {
        return Specification.where(equalsLikeFirstName(accountDto.getFirstName()))
                .and(equalsLikeSecondName(accountDto.getLastName()))
                .and(equalsLikeEmail(accountDto.getEmail()));
    }
}