package ro.tuc.ds2020.dtos.dto;

import ro.tuc.ds2020.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {
    private UUID id;
    private String name;
    private String password;
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(password, userDTO.password) &&
                Objects.equals(name, userDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
