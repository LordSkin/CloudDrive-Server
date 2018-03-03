package PresentationTier;

import BuisnessTier.AppController;
import Dagger.AppComponent;
import Dagger.ControllerModule;
import Dagger.DaggerAppComponent;
import dagger.internal.DaggerCollections;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

@Controller
@EnableAutoConfiguration
public class RestController {

    @Inject
    AppController controller;

    public RestController() {

    }

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
