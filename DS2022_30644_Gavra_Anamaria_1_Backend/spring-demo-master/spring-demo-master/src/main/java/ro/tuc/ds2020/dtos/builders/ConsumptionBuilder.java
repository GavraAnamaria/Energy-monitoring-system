package ro.tuc.ds2020.dtos.builders;
import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
@NoArgsConstructor
public class ConsumptionBuilder {
    public static ConsumptionDTO toConsumptionDTO(Consumption d) {
        return new ConsumptionDTO(d.getId(), d.getTime(), d.getValue(), d.getDevice().getId());
    }
}