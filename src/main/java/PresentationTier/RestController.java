package PresentationTier;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

@Controller
@EnableAutoConfiguration
public class RestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String ping() {
        return "OK";
    }

    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    @ResponseBody
    public String getItem(@PathVariable("path") String path) {
        return "OK";
    }



}
