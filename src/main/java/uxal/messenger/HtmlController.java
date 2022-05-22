package uxal.messenger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/messenger")
    public String messenger(){
        return "messenger.html";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/styles.css")
    public String styles(){
        return "styles.css";
    }

    @GetMapping("/api.js") public String api_js(){return "api.js";}
}
