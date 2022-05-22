package uxal.messenger;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    public Optional<Integer> validateSessionId(String session_id){
        Optional<Session> sessionSearchRes = sessionRepository.findById(session_id);
        if(sessionSearchRes.isEmpty()){
            return Optional.empty();
        }
        Integer userId = sessionSearchRes.get().getUserId();
        if(!userRepository.existsById(userId)){
            return Optional.empty();
        }
        return Optional.of(userId);
    }
}
