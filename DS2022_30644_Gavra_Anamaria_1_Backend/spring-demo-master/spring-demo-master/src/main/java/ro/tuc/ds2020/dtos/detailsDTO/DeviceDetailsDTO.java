package ro.tuc.ds2020.dtos.detailsDTO;

import ro.tuc.ds2020.entities.User;
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
public class DeviceDetailsDTO {

    private String device;
    private UUID id;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private float maxEnergy;

    private UUID user_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO deviceDTO = (DeviceDetailsDTO) o;
        return Objects.equals(description, deviceDTO.description) &&
                Objects.equals(address, deviceDTO.address)&&
                Objects.equals(maxEnergy, deviceDTO.maxEnergy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, maxEnergy);
    }
}
