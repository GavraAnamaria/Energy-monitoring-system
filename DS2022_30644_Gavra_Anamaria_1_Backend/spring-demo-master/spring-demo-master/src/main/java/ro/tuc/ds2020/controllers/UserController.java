package ro.tuc.ds2020.controllers;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.LoginDTO;
import ro.tuc.ds2020.dtos.dto.UserDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.enums.Role;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService personService) {
        this.userService = personService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link personLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody UserDetailsDTO personDTO) {
        UUID personID = userService.insert(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UUID> updateUser( @RequestBody UserDetailsDTO personDTO) {
        UUID personID = userService.update(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }
    @PostMapping(path="/login")
    public ResponseEntity<Role> login(@RequestBody LoginDTO loginDTO) {
        Role role = userService.login(loginDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserByName(@Valid @RequestBody String name) {
        userService.deleteByName(name);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable("id") UUID personId) {
        UserDetailsDTO dto = userService.findUserById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

}
