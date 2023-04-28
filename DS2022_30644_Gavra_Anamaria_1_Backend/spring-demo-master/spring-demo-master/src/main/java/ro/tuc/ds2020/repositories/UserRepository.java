package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Example: JPA generate Query by Field
     */
    Optional<User> findByName(String name);

    /**
     * Example: Write Custom Query
     */

    @Modifying
    @Transactional
    @Query(value="delete from User b where b.name= :name")
    int deleteByName(@Param("name") String name);

    @Query(value = "SELECT p " +
            "FROM User p " +
            "WHERE p.name = :name " +
            "AND p.role = 'CLIENT'  ")
    Optional<User> findSeniorsByName(@Param("name") String name);

    Optional<User> findByNameAndPassword(String uname, String password);
}
