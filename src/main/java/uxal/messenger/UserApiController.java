package uxal.messenger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/users")
public class UserApiController {
    @Autowired
    private UserRepository userRepository;

    @Autowired AuthorizationManager authorizationManager;

    @Autowired
    AvatarRepository avatarRepository;

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


    // TODO: image storage service
    @PostMapping("/avatars/set")
    public ResponseEntity<String> setAvatar(@RequestParam String session_id, @RequestParam MultipartFile image) throws IOException{
        Optional<Integer> authUserId = authorizationManager.validateSessionId(session_id);
        if(authUserId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            Avatar n = new Avatar();
            n.setUserId(authUserId.get());
            n.setImage(image.getBytes());
            avatarRepository.save(n);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping("avatars/get")
    public ResponseEntity<byte[]> getAvatar(@RequestParam Integer id){
        Optional<Avatar> foundAvatar = avatarRepository.findById(id);
        if(foundAvatar.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(foundAvatar.get().getImage());
    }
}
