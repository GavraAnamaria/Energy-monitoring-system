package ro.tuc.ds2020.dtos.detailsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDetailsDTO {
    @NotNull
    private String emitator;
    @NotNull
    private String receptor;

    @NotNull
    private String text;



}
