package uxal.messenger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/messages")
public class MessagesApiController {

    @Autowired
    AuthorizationManager authorizationManager;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;
    
    @GetMapping(path = "/get_before")
    ResponseEntity<List<Message>> getDialogBefore(@RequestParam String session_id, @RequestParam Integer companion,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date before, @RequestParam Integer max_num){
        Optional<Integer> authUserId = authorizationManager.validateSessionId(session_id);
        if(authUserId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(before==null){
            before = new Date(System.currentTimeMillis());
        }

        return new ResponseEntity<List<Message>>(messageRepository.getDialogBefore(authUserId.get(), companion, before, max_num), HttpStatus.OK);
    }
    
    @GetMapping(path = "/get_after")
    ResponseEntity<List<Message>> getDialogAfter(@RequestParam String session_id, @RequestParam Integer companion,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date after, @RequestParam Integer max_num){
        Optional<Integer> authUserId = authorizationManager.validateSessionId(session_id);
        if(authUserId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<List<Message>>(messageRepository.getDialogAfter(authUserId.get(), companion, after, max_num), HttpStatus.OK);
    }

    @PostMapping(path="/send")
    ResponseEntity<String> sendMessage(@RequestParam String session_id, @RequestParam Integer companion, @RequestParam String text){
        Optional<Integer> authUserId = authorizationManager.validateSessionId(session_id);
        if(authUserId.isEmpty()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(text.length() == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Message n = new Message();
        n.setSender(authUserId.get());
        n.setDestination(companion);
        n.setText(text);
        messageRepository.save(n);

        return new ResponseEntity<String>("Message sent", HttpStatus.OK);
    }
}
