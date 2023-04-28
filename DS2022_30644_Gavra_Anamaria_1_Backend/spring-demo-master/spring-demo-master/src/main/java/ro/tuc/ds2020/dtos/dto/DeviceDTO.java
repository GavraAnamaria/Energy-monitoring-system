package ro.tuc.ds2020.dtos.dto;

import ro.tuc.ds2020.entities.User;
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
public class DeviceDTO extends RepresentationModel<ro.tuc.ds2020.dtos.dto.UserDTO> {
    private UUID id;
    private String description;
    private String address;
    private float max_energy;
    private UUID user_id;

    @Override
    public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ro.tuc.ds2020.dtos.dto.DeviceDTO deviceDTO = (ro.tuc.ds2020.dtos.dto.DeviceDTO) o;
            return Objects.equals(description, deviceDTO.description) &&
                    Objects.equals(address, deviceDTO.address)&&
                    Objects.equals(max_energy, deviceDTO.max_energy);
        }
        @Override
        public int hashCode() {
            return Objects.hash(description, address, max_energy);
        }
    }