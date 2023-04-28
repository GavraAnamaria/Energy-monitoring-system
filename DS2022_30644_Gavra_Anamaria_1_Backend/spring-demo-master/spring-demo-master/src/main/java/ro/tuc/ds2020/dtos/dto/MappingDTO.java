package ro.tuc.ds2020.dtos.dto;

import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappingDTO {
    private UUID deviceId;
    private UUID userId;

    private String description;
    private String name;


}
