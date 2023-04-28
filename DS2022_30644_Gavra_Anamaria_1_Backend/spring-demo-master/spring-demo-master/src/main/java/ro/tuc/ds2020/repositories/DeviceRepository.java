package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {


    /**
     * Example: JPA generate Query by Field
     */
    Optional<Device> findByDescription(String description);
    @Modifying
    @Transactional
    Optional<List<Device>> findByUser(User user);


    @Modifying
    @Transactional
    @Query(value="UPDATE Device d SET d.user = :u WHERE d.description = :description")
    void updateUser(User u, @Param("description") String description);

    /**
     * Example: Write Custom Query
     */
    @Query(value = "SELECT p " +
            "FROM Device p " +
            "WHERE p.description = :description ")
    Optional<User> findSeniorsByDescription(@Param("description") String description);
}
