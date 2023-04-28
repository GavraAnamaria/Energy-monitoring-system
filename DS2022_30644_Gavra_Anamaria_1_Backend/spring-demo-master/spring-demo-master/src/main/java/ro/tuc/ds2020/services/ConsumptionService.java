package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.builders.ConsumptionBuilder;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionService.class);
    private final DeviceRepository deviceRepository;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository ConsumptionRepository, DeviceRepository deviceRepository) {
        this.consumptionRepository = ConsumptionRepository;
        this.deviceRepository = deviceRepository;
    }


    public List<ConsumptionDTO> getConsByDay(String deviceDescription, LocalDate date) {
        Device device=deviceRepository.findByDescription(deviceDescription).orElse(null);
        List<Consumption> cons = consumptionRepository.findByDeviceAndTimeBetween(device, LocalDateTime.of(date, LocalTime.of(0, 0, 0)), LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
        return  cons.stream()
                .map(ConsumptionBuilder::toConsumptionDTO)
                .collect(Collectors.toList());
    }

    public UUID insertCons( ConsumptionDetailsDTO consumption) {
        Consumption c = new Consumption();
        Device d = deviceRepository.findByDescription(consumption.getDevice()).orElse(null);
        String day = consumption.getTime();
        System.out.println(day.substring(0, 4) + "\n" + day.substring(4, 6) + "\n" + day.substring(6, 8) + "\n" + day.substring(8, day.length() - 1) + "\n");
        LocalDate date = LocalDate.of(Integer.parseInt(day.substring(0, 4)), Integer.parseInt(day.substring(4, 6)), Integer.parseInt(day.substring(6, 8)));
        LocalDateTime time = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
        System.out.println(("=========="+consumptionRepository.findByDeviceAndTimeBetween(d,time, LocalDateTime.of(date, LocalTime.of(23, 59,59)))));
        if(consumptionRepository.findByDeviceAndTimeBetween(d,time, LocalDateTime.of(date, LocalTime.of(23, 59,59))).isEmpty()) {
            for (int j = 0; j < 24; j++) {
                c = consumptionRepository.save(new Consumption(time.plusHours(j), (float) Math.random() * 30, d));
                }
        }
        return new UUID(0,0);
    }
}
