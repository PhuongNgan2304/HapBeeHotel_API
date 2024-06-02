package com.hotel.hotelapi.controller;

<<<<<<< HEAD
=======
import com.hotel.hotelapi.model.PageResponse;
>>>>>>> 2c31b00 (update commit)
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.service.IServiceService;
import com.hotel.hotelapi.service.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
>>>>>>> 2c31b00 (update commit)
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/service")
=======
@RequestMapping("/api/v1/service")
>>>>>>> 2c31b00 (update commit)
public class ServiceController {

    @Autowired
    IServiceService service = new ServiceServiceImpl();
    @GetMapping("/{id}")
    public Response getServiceById(@PathVariable int id) {
<<<<<<< HEAD
        ServiceModel serviceModel = service.findById(id);
=======
        ServiceModel serviceModel = service.findActiveById(id);
>>>>>>> 2c31b00 (update commit)
        if (serviceModel != null) {
            return new Response(true, "Service found successfully!", serviceModel);
        }
        return new Response(false, "Service not found!", null);
    }

    @GetMapping("/all")
<<<<<<< HEAD
    public Response getAllServices() {
        List<ServiceModel> serviceModels = service.findAll();
        return new Response(true, "Services found successfully!", serviceModels);
    }

    @PostMapping("/create")
    public Response createService(@RequestBody ServiceModel serviceModel) {
        ServiceModel createdService = service.create(serviceModel);
        return new Response(true, "Service created successfully", createdService);
    }

    @PutMapping("/{id}")
    public Response updateService(@PathVariable int id, @RequestBody ServiceModel serviceModel) {
        ServiceModel updatedService = service.update(id, serviceModel);
        if (updatedService != null) {
            return new Response(true, "Service updated successfully", updatedService);
        }
        return new Response(false, "Service not found", null);
    }

    @DeleteMapping("/{id}")
    public Response deleteService(@PathVariable int id) {
        service.delete(id);
        return new Response(true, "Service deleted successfully", null);
    }
=======
    public Response getAllActiveServices(
            @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageResponse<ServiceModel> pageResponse = service.findAllActiveAndSearch(searchName, pageNo, pageSize, sortBy, sortDir);
        if (pageResponse.getContent().isEmpty()) {
            return new Response(false, "Services not found!", null);
        }
        return new Response(true, "Service found successfully!", pageResponse);
    }


>>>>>>> 2c31b00 (update commit)
}
