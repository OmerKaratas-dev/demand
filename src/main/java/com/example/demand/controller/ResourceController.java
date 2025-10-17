package com.example.demand.controller;

import com.example.demand.model.ResourceDTO;
import com.example.demand.services.ResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ResourceController {

    public static final String RESOURCE_PATH = "/api/v1/resource";
    public static final String RESOURCE_PATH_ID = RESOURCE_PATH + "/{resourceId}";

    private final ResourceService resourceService;

    @GetMapping(RESOURCE_PATH)
    public List<ResourceDTO> listResources(){
        return resourceService.getResources();
    }

    @GetMapping(value=RESOURCE_PATH_ID)
    public ResourceDTO getResourceById(@PathVariable("resourceId") Long resourceId){
        log.debug("Get Resource by Id3 - in controller");
        return resourceService.getResource(resourceId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value=RESOURCE_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody ResourceDTO resourceDTO){
        log.debug("Post Resource - in controller");
        ResourceDTO savedResourceDTO = resourceService.createNewResource(resourceDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", RESOURCE_PATH + "/"
                + savedResourceDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(RESOURCE_PATH_ID)
    public ResponseEntity updateResourceById(@PathVariable("resourceId") Long resourceId, @RequestBody ResourceDTO resourceDTO){
        log.debug("Update Resource by Id - in controller");
        if(resourceService.updateResource(resourceId, resourceDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(RESOURCE_PATH_ID)
    public ResponseEntity deleteResourceById(@PathVariable("resourceId") Long resourceId){
        log.debug("Delete Resource by Id - in controller");
        if(!resourceService.deleteResourceBy(resourceId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(RESOURCE_PATH_ID)
    public ResponseEntity updateResourcePatchById(@PathVariable("resourceId") Long resourceId,
                                                  @Validated @RequestBody ResourceDTO resourceDTO){

        resourceService.patchResourceById(resourceId, resourceDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
