package ro.tuc.ds2020.dtos.builders;

import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.dto.UserDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;
import ro.tuc.ds2020.entities.User;
@NoArgsConstructor
public class UserBuilder {

    public static UserDTO toUserDTO(User person) {
        return new UserDTO(person.getId(), person.getName(), person.getPassword(), person.getRole());
    }

    public static UserDetailsDTO toUserDetailsDTO(User person) {
        return new UserDetailsDTO(person.getId(),null, person.getName(), person.getPassword(), person.getRole());
    }

    public static User toEntity(UserDetailsDTO personDetailsDTO) {
        return User.builder().name(personDetailsDTO.getName())
                .password(personDetailsDTO.getPassword()).role(personDetailsDTO.getRole()).build();
    }
}
