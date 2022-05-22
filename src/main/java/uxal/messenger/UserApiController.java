package uxal.messenger;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserApiController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String nick, @RequestParam String password){
        User n = new User();
        n.setNick(nick);
        n.setPassword(password);
        userRepository.save(n);

        return "Saved";
    }

    @GetMapping(path = "/get")
    public ResponseEntity<PublicUserData> getUserInfo(@RequestParam Integer id){
        Optional<PublicUserData> searchRes = userRepository.getPublicUserData(id);
        if(searchRes.isPresent()){
            return new ResponseEntity<PublicUserData>(searchRes.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/search")
    public @ResponseBody List<PublicUserData> searchByNick(@RequestParam String partial_nick){
        return userRepository.searchByNick(partial_nick);
    }
}
