package ro.tuc.ds2020.dtos.detailsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.tuc.ds2020.entities.Device;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsumptionDetailsDTO {
    private UUID id;
    @NotNull
    private String time;

    private Float value;
    @NotNull
    private String device;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumptionDetailsDTO cDTO = (ConsumptionDetailsDTO) o;
        return  Objects.equals(time, cDTO.time) &&
                Objects.equals(device, cDTO.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, device);
    }
}
