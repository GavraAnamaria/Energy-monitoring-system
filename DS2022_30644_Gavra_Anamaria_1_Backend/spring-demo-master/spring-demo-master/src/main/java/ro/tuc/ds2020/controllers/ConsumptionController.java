package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.services.ConsumptionService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/consumption")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService cService) {
        this.consumptionService = cService;
    }

    @PutMapping()
    public ResponseEntity<List<ConsumptionDTO>> getConsByDay(@RequestBody String day) {
        System.out.println(day+"1234\n\n\n\n");
        System.out.println(day.substring(1,5)+"\n"+day.substring(5,7)+"\n"+day.substring(7,9)+"\n"+day.substring(9,day.length()-1)+"\n");
        LocalDate d = LocalDate.of(Integer.parseInt(day.substring(1,5)), Integer.parseInt(day.substring(5,6)), Integer.parseInt(day.substring(6,8)));
        List<ConsumptionDTO> cons =consumptionService.getConsByDay(day.substring(9,day.length()-1), d);
        return new ResponseEntity<>(cons, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertCons(@Valid @RequestBody ConsumptionDetailsDTO consumptionDetailsDTO) {
        System.out.println("!!!!!!!!!!!!!!!!!!!"+consumptionDetailsDTO+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n\n\n");
        UUID consID = consumptionService.insertCons(consumptionDetailsDTO);
        return new ResponseEntity<>(consID, HttpStatus.CREATED);
    }

//    @PostMapping()
//    public void insertCons() {
//        consumptionService.insertCons(consumptionDetailsDTO);
//    }
}
