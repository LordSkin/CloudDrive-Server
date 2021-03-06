package PresentationTier;

import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLConnection;

/**
 * Rest controller, automaticaly wired by Spring. each method respond to one request.
 */
@org.springframework.web.bind.annotation.RestController
@MultipartConfig(maxFileSize = 10737418240L, maxRequestSize = 10737418240L, fileSizeThreshold = 52428800)
public class RestController {

    @Autowired
    AppController controller;

    @Autowired
    MimetypesFileTypeMap mimeTypesMap;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String ping() {
        return "OK";
    }

    @RequestMapping(value = "/get/{path}/{token}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getItem(@PathVariable("path") String path, @PathVariable("token") String token) {

        try {
            File result = controller.getFile(path, token);
            String mime = mimeTypesMap.getContentType(result);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(result));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getName() + "\"")
                    .contentType(MediaType.parseMediaType(mime))
                    .contentLength(result.length())
                    .body(resource);
        } catch (FileNotFoundException e) {
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
        } catch (FileNotFoundException e) {
            System.out.println("Folder not found:" + path);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/token/{path}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> askForFileToken(@PathVariable("path")String path){
        String result = controller.getToken(path);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getBaseList() {
        try {
            String result = controller.getFolder("");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (FileNotFoundException e) {
            System.out.println("Base folder not found:");
            return ResponseEntity.notFound().build();
        }

    }


    @RequestMapping(value = "/{path}", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file, @PathVariable("path") String path) {
        if (controller.addFile(file, path)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/folder/{path}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addFolder(@PathVariable("path") String path) {
        if (controller.addDirectory(path)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{path}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteElement(@PathVariable("path") String path) {
        if (controller.delete(path)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{path}/{newName}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> rename(@PathVariable("path") String path, @PathVariable("newName") String newName) {
        if (controller.rename(path, newName)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/logs/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getLogs(){
        try {
            String result = controller.getLogs();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (HibernateException e) {
            System.out.println("error reading logs");
            return ResponseEntity.notFound().build();
        }
    }


}
