package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.MessageDetailsDTO;
import ro.tuc.ds2020.services.MessageService;
import javax.validation.Valid;
@RestController
@CrossOrigin
@RequestMapping(value = "/msg")
public class MessageController {
        private final MessageService messageService;

        @Autowired
        public MessageController(MessageService cService) {
            this.messageService = cService;
        }
//
//        @PutMapping()
//        public ResponseEntity<List<ConsumptionDTO>> getConsByDay(@RequestBody String day) {
//            System.out.println(day+"1234\n\n\n\n");
//            System.out.println(day.substring(1,5)+"\n"+day.substring(5,7)+"\n"+day.substring(7,9)+"\n"+day.substring(9,day.length()-1)+"\n");
//            LocalDate d = LocalDate.of(Integer.parseInt(day.substring(1,5)), Integer.parseInt(day.substring(5,7)), Integer.parseInt(day.substring(7,9)));
//            List<ConsumptionDTO> cons =consumptionService.getConsByDay(day.substring(9,day.length()-1), d);
//            return new ResponseEntity<>(cons, HttpStatus.OK);
//        }

        @PostMapping()
        public String insertCons(@Valid @RequestBody MessageDetailsDTO messageDetailsDTO) {
            System.out.println("!!!!!!!!!!!!!!!!!!!"+messageDetailsDTO+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n\n\n");
          //  UUID consID = consumptionService.insertCons(consumptionDetailsDTO);
            this.messageService.sendMessage(messageDetailsDTO);
            return "sdnvsdv";
           // return new ResponseEntity<>(consID, HttpStatus.CREATED);
        }
}