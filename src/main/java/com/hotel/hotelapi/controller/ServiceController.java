package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.service.IServiceService;
import com.hotel.hotelapi.service.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceController {

    @Autowired
    IServiceService service = new ServiceServiceImpl();
    @GetMapping("/{id}")
    public Response getServiceById(@PathVariable int id) {
        ServiceModel serviceModel = service.findActiveById(id);
        if (serviceModel != null) {
            return new Response(true, "Service found successfully!", serviceModel);
        }
        return new Response(false, "Service not found!", null);
    }

    @GetMapping("/all")
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


}
