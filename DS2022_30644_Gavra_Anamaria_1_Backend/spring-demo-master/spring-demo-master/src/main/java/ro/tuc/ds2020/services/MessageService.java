package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.detailsDTO.MessageDetailsDTO;

@Service
public class MessageService {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    public MessageService() {
    }

    public void sendMessage(MessageDetailsDTO message) {
        template.convertAndSend("/topic2/message", message.getReceptor()+"text:"+message.getText());
        }
}