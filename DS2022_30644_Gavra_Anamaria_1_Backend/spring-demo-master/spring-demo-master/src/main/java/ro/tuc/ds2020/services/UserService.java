package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.detailsDTO.LoginDTO;
import ro.tuc.ds2020.dtos.dto.UserDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.enums.Role;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public UserService(UserRepository userRepository,DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("user with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }


    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("user with id {} was inserted in db", user.getId());
        return user.getId();
    }
    public void deleteByName(String name) {
        User user=userRepository.findByName(name.substring(1, name.length()-1)).orElseThrow(()->new ResourceNotFoundException("kdsmk"));
            for(Device d: deviceRepository.findByUser(user).orElse(new ArrayList<>()))
                deviceRepository.updateUser(null,d.getDescription());
        userRepository.delete(user);
    }


    public UUID update(UserDetailsDTO personDTO) {
        User u = userRepository.findByName(personDTO.getUser()).orElseThrow(() ->  new  ResourceNotFoundException(User.class.getSimpleName() + " with id: " + personDTO.getUser()));
        u.setName(personDTO.getName());
        u.setPassword(personDTO.getPassword());
        u.setRole(personDTO.getRole());
        userRepository.save(u);
        return u.getId();
    }

    public Role login(LoginDTO loginDTO) {
        User u = userRepository.findByNameAndPassword(loginDTO.getUname(), loginDTO.getPassword()).orElseThrow(() ->  new  ResourceNotFoundException(User.class.getSimpleName() + " with id: " + loginDTO.getUname()));
        return u.getRole();
    }
}
