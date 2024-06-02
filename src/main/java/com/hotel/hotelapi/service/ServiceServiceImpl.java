package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.ServiceEntity;
<<<<<<< HEAD
=======
import com.hotel.hotelapi.entity.ServiceImageEntity;
import com.hotel.hotelapi.model.PageResponse;
>>>>>>> 2c31b00 (update commit)
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
>>>>>>> 2c31b00 (update commit)
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements IServiceService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServiceRepository serviceRepository;

<<<<<<< HEAD
=======
    @Autowired
    private FirebaseServiceImpl firebaseServiceImpl;

>>>>>>> 2c31b00 (update commit)
    @Override
    public ServiceModel findById(int id) {
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findById(id);
        if (serviceEntityOptional.isPresent()) {
            ServiceEntity serviceEntity = serviceEntityOptional.get();
<<<<<<< HEAD
            return modelMapper.map(serviceEntity, ServiceModel.class);
=======
            ServiceModel serviceModel = modelMapper.map(serviceEntity, ServiceModel.class);


            List<String> imageUrls = new ArrayList<>();
            for(ServiceImageEntity serviceImageEntity : serviceEntity.getImages()){
                imageUrls.add(serviceImageEntity.getImageURL());
            }
            serviceModel.setImageURLs(imageUrls);

            return serviceModel;
        } else {
            return null;
        }
    }


    @Override
    public ServiceModel findActiveById(int id) {
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findByIdAndIsDeletedFalse(id);
        if (serviceEntityOptional.isPresent()) {
            ServiceEntity serviceEntity = serviceEntityOptional.get();
            ServiceModel serviceModel = modelMapper.map(serviceEntity, ServiceModel.class);
            List<String> imageUrls = new ArrayList<>();
            for(ServiceImageEntity serviceImageEntity : serviceEntity.getImages()){
                imageUrls.add(serviceImageEntity.getImageURL());
            }
            serviceModel.setImageURLs(imageUrls);
            return serviceModel;
>>>>>>> 2c31b00 (update commit)
        } else {
            return null;
        }
    }

    @Override
    public List<ServiceModel> findAll() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        return serviceEntities.stream()
<<<<<<< HEAD
=======
                .map(serviceEntity -> {
                    ServiceModel serviceModel = modelMapper.map(serviceEntity, ServiceModel.class);
                    List<String> imageUrls = serviceEntity.getImages().stream()
                            .map(ServiceImageEntity::getImageURL)
                            .collect(Collectors.toList());
                    serviceModel.setImageURLs(imageUrls);
                    return serviceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ServiceModel> findAllActiveAndSearch(String searchName, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        pageNo = (pageNo < 1) ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ServiceEntity> activeServices;

        if (!searchName.isEmpty()) {
            activeServices = serviceRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(searchName, pageable);
        } else {
            activeServices = serviceRepository.findAllByIsDeletedFalse(pageable);
        }

        List<ServiceModel> content = activeServices.stream()
                .map(serviceEntity -> {
                    ServiceModel serviceModel = modelMapper.map(serviceEntity, ServiceModel.class);
                    List<String> imageUrls = serviceEntity.getImages().stream()
                            .map(ServiceImageEntity::getImageURL)
                            .collect(Collectors.toList());
                    serviceModel.setImageURLs(imageUrls);
                    return serviceModel;
                })
                .collect(Collectors.toList());

        int currentPage = activeServices.getNumber() + 1;
        int totalPages = activeServices.getTotalPages();

        return new PageResponse<>(content, currentPage, totalPages);
    }


    @Override
    public List<ServiceModel> findAllActive() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAllByIsDeletedFalse();
        return serviceEntities.stream()
>>>>>>> 2c31b00 (update commit)
                .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
<<<<<<< HEAD
    public ServiceModel create(ServiceModel serviceModel) {
        ServiceEntity serviceEntity = modelMapper.map(serviceModel, ServiceEntity.class);
        ServiceEntity savedServiceEntity = serviceRepository.save(serviceEntity);
        return modelMapper.map(savedServiceEntity, ServiceModel.class);
    }

    @Override
    public ServiceModel update(int id, ServiceModel serviceModel) {
=======
    public List<ServiceModel> findAllDisable() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAllByIsDeletedTrue();
        return serviceEntities.stream()
                .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceModel.class))
                .collect(Collectors.toList());
    }
    @Override
    public ServiceModel create(ServiceModel serviceModel, List<MultipartFile> imageFiles) {
        ServiceEntity serviceEntity = modelMapper.map(serviceModel, ServiceEntity.class);
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<String> imageURLs = new ArrayList<>();
            try {
                for (MultipartFile imageFile : imageFiles) {
                    String imageURL = firebaseServiceImpl.uploadImagesToFirebase(imageFile); // Upload img to firebase storage
                    imageURLs.add(imageURL);
                }

                //Store imageURLs into table: service_image
                List<ServiceImageEntity> serviceImages = imageURLs.stream()
                        .map(imageURL -> {
                            ServiceImageEntity serviceImage = new ServiceImageEntity();
                            serviceImage.setImageURL(imageURL);
                            serviceImage.setService(serviceEntity);
                            return serviceImage;
                        })
                        .toList();
                serviceEntity.setImages(serviceImages);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Store new service into table: service
        serviceEntity.setDeleted(false);
        ServiceEntity savedServiceEntity = serviceRepository.save(serviceEntity);
        ServiceModel responseService = modelMapper.map(savedServiceEntity, ServiceModel.class);

        //map through savedServiceEntity to show imageURLs in response
        List<String> imagesURLs = savedServiceEntity.getImages().stream()
                .map(ServiceImageEntity::getImageURL)
                .toList();
        responseService.setImageURLs(imagesURLs);
        return responseService;
    }

    @Override
    public ServiceModel update(int id, ServiceModel serviceModel, List<MultipartFile> imageFiles) {
>>>>>>> 2c31b00 (update commit)
        Optional<ServiceEntity> serviceEntityOptional = serviceRepository.findById(id);
        if (serviceEntityOptional.isPresent()) {
            ServiceEntity serviceEntity = serviceEntityOptional.get();

<<<<<<< HEAD
            // Update serviceEntity with serviceModel fields
            serviceEntity.setName(serviceModel.getName());
            serviceEntity.setPrice(serviceModel.getPrice());
            serviceEntity.setSalePercent(serviceModel.getSalePercent());
            serviceEntity.setDescription(serviceModel.getDescription());
            serviceEntity.setImage(serviceModel.getImage());

            // Save serviceEntity
            ServiceEntity updatedServiceEntity = serviceRepository.save(serviceEntity);

            return modelMapper.map(updatedServiceEntity, ServiceModel.class);
=======
            // Update only non-null fields
            if (serviceModel.getName() != null) {
                serviceEntity.setName(serviceModel.getName());
            }
            if (serviceModel.getPrice() != 0) {
                serviceEntity.setPrice(serviceModel.getPrice());
            }
            if (serviceModel.getDescription() != null) {
                serviceEntity.setDescription(serviceModel.getDescription());
            }

            // Handle image upload
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<String> imageURLs = new ArrayList<>();
                try {
                    for (MultipartFile imageFile : imageFiles) {
                        String imageURL = firebaseServiceImpl.uploadImagesToFirebase(imageFile); // Upload img to Firebase storage
                        imageURLs.add(imageURL);
                    }

                    // Remove old images if necessary
                    if (serviceEntity.getImages() != null) {
                        serviceEntity.getImages().clear();
                    } else {
                        serviceEntity.setImages(new ArrayList<>());
                    }

                    // Store imageURLs into table: service_image
                    for (String imageURL : imageURLs) {
                        ServiceImageEntity serviceImage = new ServiceImageEntity();
                        serviceImage.setImageURL(imageURL);
                        serviceImage.setService(serviceEntity);
                        serviceEntity.getImages().add(serviceImage);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Save the updated entity
            try {
                ServiceEntity updatedServiceEntity = serviceRepository.save(serviceEntity);

                // Map the updated entity back to the model
                ServiceModel responseServiceModel = modelMapper.map(updatedServiceEntity, ServiceModel.class);

                // Set the image URLs for the response
                List<String> imagesURLs = updatedServiceEntity.getImages().stream()
                        .map(ServiceImageEntity::getImageURL)
                        .toList();
                responseServiceModel.setImageURLs(imagesURLs);

                return responseServiceModel;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
>>>>>>> 2c31b00 (update commit)
        } else {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        serviceRepository.deleteById(id);
    }
}
