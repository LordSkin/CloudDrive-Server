package PresentationTier;

import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.net.URLConnection;

@org.springframework.web.bind.annotation.RestController
@MultipartConfig(maxFileSize = 10737418240L, maxRequestSize = 10737418240L, fileSizeThreshold = 52428800)
public class RestController {

    @Autowired
    AppController controller;

    @Autowired
    MimetypesFileTypeMap mimeTypesMap;

    public RestController(){
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
            String mime = mimeTypesMap.getContentType(result);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(result));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mime))
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


    @RequestMapping(value = "/{path}", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String addFile(@RequestParam("file") MultipartFile file, @PathVariable("path") String path) {
        if (controller.addFile(file, path)) {
            return "OK";
        }
        else {
            return "NOT OK";
        }
    }

    @RequestMapping(value = "/folder/{path}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/rename/{path}/{newName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> rename(@PathVariable("path") String path, @PathVariable("newName") String newName){
        if (controller.rename(path, newName)){
            return ResponseEntity.ok("OK");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}
