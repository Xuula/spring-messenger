package uxal.messenger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    AuthorizationManager authorizationManager;

    @PostMapping(path = "/auth")
    public ResponseEntity<Session> authorize(@RequestParam String nick, @RequestParam String password){
        List<User> usersFound = userRepository.findByNick(nick);
        for(User user: usersFound){
            if(user.getPassword().equals(password)){
                long timeToLive = 60 * 60 * 1000;
                long expirationTime = System.currentTimeMillis() + timeToLive;

                Session n = new Session();
                n.setUserId(user.getId());
                n.setExpiresAt(new Timestamp(expirationTime));
                sessionRepository.save(n);

                return new ResponseEntity<Session>(n, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/currentuser")
    public ResponseEntity<PublicUserData> getAuthenticatedUser(@RequestParam String session_id){
        Optional<Session> sessionSearchRes = sessionRepository.findById(session_id);
        if(sessionSearchRes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<PublicUserData> userSearchRes = userRepository.getPublicUserData(sessionSearchRes.get().getUserId());
        if(userSearchRes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PublicUserData>(userSearchRes.get(), HttpStatus.OK);
    }
}