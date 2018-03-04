package PresentationTier;

import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@EnableAutoConfiguration
public class RestController {

    AppController controller;

    public RestController() {
        controller = AppControllerImpl.getAppController();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String ping() {
        return "OK";
    }

    @RequestMapping(value = "/get/{path}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getItem(@PathVariable("path") String path) {

        try {
            File result = controller.getFile(path);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(result));
            return ResponseEntity.ok()
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .contentLength(result.length())
                    .body(resource);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
            return ResponseEntity.badRequest().body(null);
        }
    }


    @RequestMapping(value = "/list/{path}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getList(@PathVariable("path") String path) {
        String result = null;
        try {
            result = controller.getFolder(path);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }
        catch (FileNotFoundException e) {
            System.out.println("Folder not found:" + path);
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getBaseList() {
        try {
            String result = controller.getFolder(".");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        }
        catch (FileNotFoundException e) {
            System.out.println("Base folder not found:");
            return ResponseEntity.notFound().build();
        }

    }


    @RequestMapping(value = "/{path}", method = RequestMethod.POST)
    @ResponseBody
    public String addFile(@RequestParam("file") MultipartFile file, @PathVariable("path") String path) {
        if (controller.addFile(file, path)) {
            return "OK";
        }
        else {
            return "NOT OK";
        }
    }

    @RequestMapping(value = "/folder/{path}", method = RequestMethod.POST)
    @ResponseBody
    public String addFolder(@PathVariable("path") String path) {
        if (controller.addDirectory(path)) {
            return "OK";
        }
        else {
            return "NOT OK";
        }
    }

    @RequestMapping(value = "/{path}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteElement(@PathVariable("path") String path) {
        if (controller.delete(path)) {
            return ResponseEntity.ok("OK");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}