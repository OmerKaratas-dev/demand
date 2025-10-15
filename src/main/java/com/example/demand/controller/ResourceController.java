package com.example.demand.controller;

import com.example.demand.model.Resource;
import com.example.demand.services.ResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/resource")
public class ResourceController {
    private final ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Resource> listBeers(){
        return resourceService.getResources();
    }

    @RequestMapping(value="{resourceId}" , method = RequestMethod.GET)
    public Resource getResourceById(@PathVariable("resourceId") Long resourceId){
        log.debug("Get Resource by Id3 - in controller");
        return resourceService.getResource(resourceId);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Resource resource){
        log.debug("Post Resource - in controller");
        Resource savedResource = resourceService.createNewResource(resource);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/resource/"
                + savedResource.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{resourceId}"})
    public ResponseEntity updateResourceById(@PathVariable("resourceId") Long resourceId, @RequestBody Resource resource){
        log.debug("Update Resource by Id - in controller");
        resourceService.updateResource(resourceId, resource);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{resourceId}"})
    public ResponseEntity deleteResourceById(@PathVariable("resourceId") Long resourceId){
        log.debug("Delete Resource by Id - in controller");
        resourceService.deleteResourceBy(resourceId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{resourceId}")
    public ResponseEntity updateResourcePatchById(@PathVariable("resourceId") Long resourceId,
                                                  @RequestBody Resource resource){

        resourceService.patchResourceById(resourceId, resource);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
