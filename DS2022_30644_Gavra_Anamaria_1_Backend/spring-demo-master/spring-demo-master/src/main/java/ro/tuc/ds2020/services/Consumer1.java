package ro.tuc.ds2020.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class Consumer1 {
    private final DeviceRepository deviceRepository;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    public Consumer1(DeviceRepository deviceRepository, ConsumptionRepository consumptionRepository) {
        this.deviceRepository = deviceRepository;
        this.consumptionRepository = consumptionRepository;
    }

    @RabbitListener(queues = "coada2")
    public void receiveMessage(Message message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = mapper.readValue(message.getBody(), Map.class);

        Device device = deviceRepository.findById(UUID.fromString(json.get("device_id").toString())).orElse(null);
        if (device == null) return;
        List<Consumption> consumptions = consumptionRepository.findByDeviceOrderByTimeDesc(device);
        Consumption newConsumption;
        LocalDateTime dateTime = LocalDateTime.parse(json.get("timestamp").toString());

        newConsumption = Consumption.builder()
                .device(device)
                .time(dateTime)
                .value(Float.parseFloat((String) json.get("measurement_value")))
                .build();
        float diferenta = 0.0f;
        if(consumptions.isEmpty())
            diferenta = 0;
        else
            diferenta = newConsumption.getValue()-consumptions.get(0).getValue();
        if (diferenta > device.getMaxEnergy()) {
            /*if (consumptions.isEmpty()) {
                System.out.println("Energia maxima depasita:" + newConsumption.getValue() + ">" + device.getMaxEnergy());
                template.convertAndSend("/topic/message", "Energia maxima depasita:"
                        + (newConsumption.getValue()) + ">" + device.getMaxEnergy()
                        + "device: "+ device.getDescription());
            }
            else{*/
                System.out.println("Energia maxima depasita:" + (newConsumption.getValue() - consumptions.get(0).getValue()) + ">" + device.getMaxEnergy());
                template.convertAndSend("/topic/message", "Energia maxima depasita:" + (newConsumption.getValue() - consumptions.get(0).getValue()) + ">" + device.getMaxEnergy() + "device: "+ device.getDescription());
           // }
        }
        //saving the consumption in db
        consumptionRepository.save(newConsumption);
    }

}