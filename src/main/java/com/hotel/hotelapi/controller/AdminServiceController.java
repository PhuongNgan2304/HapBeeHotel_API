package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.entity.ServiceEntity;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.service.IServiceService;
import com.hotel.hotelapi.service.ServiceServiceImpl;
import com.hotel.hotelapi.utils.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/management/service")
public class AdminServiceController {
    @Autowired
    private IServiceService serviceService = new ServiceServiceImpl();
    @GetMapping("/all")
    public Response getAllService(@RequestParam(value = "showDisabled", required = false, defaultValue = "false") boolean showDisabled,
                                  @RequestParam(value = "showActive", required = false, defaultValue = "false") boolean showActive)
    {
        Response response = new Response();
        List<ServiceModel> serviceEntities;
        if(showActive){
            serviceEntities = serviceService.findAllActive();
            response = new Response(true,"Fetch data success!",serviceEntities);
            return response;
        }
        if(showDisabled){
            serviceEntities = serviceService.findAllDisable();
            response = new Response(true,"Fetch data success!",serviceEntities);
            return response;
        }
        else
            serviceEntities = serviceService.findAll();
        response = new Response(true,"Fetch data success!",serviceEntities);
        return response;
    }

    @GetMapping("/{id}")
    public Response getServiceById(@PathVariable int id) {
        ServiceModel serviceModel = serviceService.findById(id);
        if (serviceModel != null) {
            return new Response(true, "Service found successfully!", serviceModel);
        }
        return new Response(false, "Service not found!", null);
    }

    @PostMapping("/create")
    public Response createService(@ModelAttribute ServiceModel serviceModel, @RequestParam(value = "imageFiles", required = false)List<MultipartFile> imageFiles) {
        List<MultipartFile> filesToUpload;
        if (imageFiles == null || imageFiles.isEmpty()) {
            MultipartFile defaultImage = MultipartFileUtils.createDefaultImage();
            filesToUpload = Collections.singletonList(defaultImage);
        } else {
            filesToUpload = imageFiles;
        }
        ServiceModel createdService = serviceService.create(serviceModel, filesToUpload);
        return new Response(true, "Service created successfully", createdService);
    }

    @PutMapping("/update/{id}")
    public Response updateService(@PathVariable int id,
                                  @ModelAttribute ServiceModel serviceModel,
                                  @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles) {
        ServiceModel updatedService = serviceService.update(id, serviceModel, imageFiles);
        if (updatedService != null) {
            return new Response(true, "Service updated successfully", updatedService);
        }
        return new Response(false, "Service not found", null);
    }

    @DeleteMapping("/{id}")
    public Response deleteService(@PathVariable int id) {
        serviceService.delete(id);
        return new Response(true, "Service deleted successfully", null);
    }
}
