package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alex.braim.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    boolean existsByEmail(String email);

    @Query(" SELECT EXISTS (SELECT ci.animal " +
            "FROM ChippingInfo ci " +
            "JOIN ci.chipper c " +
            "WHERE c.id = :id and ci.animal is not null )")
    boolean accountHaveAnimals(@Param("id") Long id);

    Optional<Account> findByEmail(String email);

}
