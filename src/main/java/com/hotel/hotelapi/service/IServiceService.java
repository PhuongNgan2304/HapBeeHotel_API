package com.hotel.hotelapi.service;

<<<<<<< HEAD
import com.hotel.hotelapi.model.ServiceModel;
=======
import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.model.ServiceModel;
import org.springframework.web.multipart.MultipartFile;

>>>>>>> 2c31b00 (update commit)
import java.util.List;

public interface IServiceService {
    ServiceModel findById(int id);
<<<<<<< HEAD
    List<ServiceModel> findAll();
    ServiceModel create(ServiceModel serviceModel);
    ServiceModel update(int id, ServiceModel serviceDTO);
=======
    ServiceModel findActiveById(int id);
    List<ServiceModel> findAll();
    PageResponse<ServiceModel> findAllActiveAndSearch(String searchName, int pageNo, int pageSize, String sortBy, String sortDir);
    List<ServiceModel> findAllActive();

    List<ServiceModel> findAllDisable();

    ServiceModel create(ServiceModel serviceModel, List<MultipartFile> imageFiles);
    ServiceModel update(int id, ServiceModel serviceDTO, List<MultipartFile> imageFiles);
>>>>>>> 2c31b00 (update commit)
    void delete(int id);
}
