package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, UUID> {


    /**
     * Example: JPA generate Query by Field
     */
    List<User> findByValue(float value);
    List<Consumption> findByDeviceAndTimeBetween(Device device, LocalDateTime time, LocalDateTime time2);

   List<Consumption> findByDevice(Device device);

    void deleteByDevice(Device device);

    /**
     * Example: Write Custom Query
     */
    List<Consumption> findByDeviceOrderByTimeDesc(Device device);
}
