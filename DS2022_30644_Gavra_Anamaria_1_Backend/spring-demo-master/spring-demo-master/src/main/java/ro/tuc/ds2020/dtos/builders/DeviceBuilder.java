package ro.tuc.ds2020.dtos.builders;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import ro.tuc.ds2020.controllers.DeviceController;
import ro.tuc.ds2020.controllers.UserController;
import ro.tuc.ds2020.dtos.detailsDTO.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.dto.DeviceDTO;
import ro.tuc.ds2020.dtos.dto.UserDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.UserService;

import java.util.UUID;

@NoArgsConstructor
public class DeviceBuilder {

    public static DeviceDTO toDeviceDTO(Device d) {
        UUID userId;
        if(d.getUser()==null)
            userId = null;
        else
            userId = d.getUser().getId();
        return new DeviceDTO(d.getId(), d.getDescription(), d.getAddress(), d.getMaxEnergy(), userId);
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device d) {
        return new DeviceDetailsDTO(null, d.getId(), d.getDescription(), d.getAddress(), d.getMaxEnergy(), d.getUser().getId());
    }

    public static Device toEntity(DeviceDetailsDTO d, User u) {
        return new Device(d.getDescription(), d.getAddress(), d.getMaxEnergy(), u);
    }
}
