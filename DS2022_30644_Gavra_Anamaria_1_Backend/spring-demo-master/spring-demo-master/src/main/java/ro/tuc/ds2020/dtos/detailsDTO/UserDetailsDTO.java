package ro.tuc.ds2020.dtos.detailsDTO;

import ro.tuc.ds2020.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsDTO {

    private UUID id;
    private String user;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private Role role;

    public UserDetailsDTO(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsDTO that = (UserDetailsDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
