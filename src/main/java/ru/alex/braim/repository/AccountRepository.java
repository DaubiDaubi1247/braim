package ru.alex.braim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alex.braim.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account>{
    boolean existsByEmail(String email);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM account " +
                    "WHERE (:fName is null or LOWER(first_name) LIKE CONCAT ('%',  LOWER(cast(:fName as text)) ,'%')) " +
                    "AND (:lName is null or LOWER(last_name) LIKE CONCAT ('%', LOWER(cast(:lName as text)),'%' )) " +
                    "AND (:email is null or LOWER(email) LIKE  concat('%', LOWER(cast(:email as text)), '%')) " +
                    " order by(id) limit :limit offset :offset")
    List<Account> getAccountPageByParams(@Param("fName") String firstName,
                                  @Param("lName") String lastName,
                                  @Param("email") String email,
                                  @Param("offset") int offset,
                                  @Param("limit") int limit);

    @Query(" SELECT EXISTS (SELECT ci.animal " +
            "FROM ChippingInfo ci " +
            "JOIN ci.chipper c " +
            "WHERE c.id = :id and ci.animal is not null )")
    boolean accountHaveAnimals(@Param("id") Long id);

    Optional<Account> findByEmail(String email);

}
