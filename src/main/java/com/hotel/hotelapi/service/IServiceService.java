package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.model.ServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceService {
    ServiceModel findById(int id);
    ServiceModel findActiveById(int id);
    List<ServiceModel> findAll();
    PageResponse<ServiceModel> findAllActiveAndSearch(String searchName, int pageNo, int pageSize, String sortBy, String sortDir);
    List<ServiceModel> findAllActive();

    List<ServiceModel> findAllDisable();

    ServiceModel create(ServiceModel serviceModel, List<MultipartFile> imageFiles);
    ServiceModel update(int id, ServiceModel serviceDTO, List<MultipartFile> imageFiles);
    void delete(int id);
}
