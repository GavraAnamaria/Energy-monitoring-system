package ro.tuc.ds2020.dtos.detailsDTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {
    @NotNull
    private String uname;
    @NotNull
    private String password;
}
