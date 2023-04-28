package ro.tuc.ds2020.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.dtos.detailsDTO.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.dtos.dto.DeviceDTO;
import ro.tuc.ds2020.dtos.dto.MappingDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository, ConsumptionRepository consRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.consumptionRepository=consRepository;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    public UserDetailsDTO findUserById(UUID id) {
        if(id==null)
            return null;
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("user with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(DeviceDetailsDTO deviceDTO) {
        User u;
        if(deviceDTO.getUser_id()==null)
             u = null;
        else u = userRepository.findById(deviceDTO.getUser_id()).get();
        Device device = DeviceBuilder.toEntity(deviceDTO, u);
        device = deviceRepository.save(device);
        LOGGER.debug("device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public List<MappingDTO> findMappingDU(){
        List<MappingDTO> l = new ArrayList<>();
        for(DeviceDTO d: findDevices()){
            if(d.getUser_id()!=null)
                l.add(new MappingDTO(d.getId(),d.getUser_id(), d.getDescription(), findUserById(d.getUser_id()).getName()));}
        return l;
    }

    public void insertMapping(MappingDTO m){
        User u = userRepository.findById(m.getUserId()).orElseThrow(() ->  new  ResourceNotFoundException(User.class.getSimpleName() + " with id: " + m.getUserId()));

        Device device = deviceRepository.findById(m.getDeviceId()).orElseThrow(() ->  new  ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + m.getDeviceId()));
        device.setUser(u);
        deviceRepository.save(device);
    }

    public UUID update(DeviceDetailsDTO deviceDetailsDTO) {
        Device u = deviceRepository.findByDescription(deviceDetailsDTO.getDevice()).orElseThrow(() ->  new  ResourceNotFoundException(User.class.getSimpleName() + " with id: " + deviceDetailsDTO.getDevice()));
        u.setDescription(deviceDetailsDTO.getDescription());
        u.setAddress(deviceDetailsDTO.getAddress());
        u.setMaxEnergy(deviceDetailsDTO.getMaxEnergy());
        deviceRepository.save(u);
        return u.getId();
    }

    public List<DeviceDTO> getUserDevices(String user) {
        User user1 = userRepository.findByName(user).orElseThrow(() ->  new  ResourceNotFoundException(User.class.getSimpleName() + " with id: "+user));
        List<Device> devices = deviceRepository.findByUser(user1).orElse(null);
        System.out.println(devices.get(0).getDescription()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?????????");
        System.out.println(devices.get(0).getId()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?????????");
        System.out.println(devices);
        if(devices==null)
            return null;
        else
            return devices.stream()
                    .map(DeviceBuilder::toDeviceDTO)
                    .collect(Collectors.toList());
    }

    public void deleteByDescription(String description) {
        Device d=deviceRepository.findByDescription(description.substring(1, description.length()-1)).orElseThrow(()->new ResourceNotFoundException("kdsmk"));
        consumptionRepository.deleteAll(consumptionRepository.findByDevice(d));
        deviceRepository.delete(d);
    }
}
