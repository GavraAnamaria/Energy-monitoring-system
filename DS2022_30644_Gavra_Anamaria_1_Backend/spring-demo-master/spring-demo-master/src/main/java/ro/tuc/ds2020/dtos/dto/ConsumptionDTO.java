package ro.tuc.ds2020.dtos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConsumptionDTO extends RepresentationModel<ro.tuc.ds2020.dtos.dto.UserDTO> {
    private UUID id;
    private LocalDateTime time;
    private Float value;
    private UUID device_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ro.tuc.ds2020.dtos.dto.ConsumptionDTO cDTO = (ro.tuc.ds2020.dtos.dto.ConsumptionDTO) o;
        return Objects.equals(time, cDTO.time) &&
                Objects.equals(device_id, cDTO.device_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, device_id);
    }
}