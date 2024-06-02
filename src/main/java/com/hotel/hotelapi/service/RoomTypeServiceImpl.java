package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.*;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.repository.RoomTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements IRoomTypeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private FirebaseServiceImpl firebaseService;

    @Override
    public RoomTypeModel findByIdActive(int id) {
        return roomTypeRepository.findByIdAndIsDeletedFalse(id)
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity, RoomTypeModel.class))
                .orElse(null);
    }

    @Override
    public RoomTypeModel findByIdInactive(int id) {
        return roomTypeRepository.findByIdAndIsDeletedTrue(id)
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity, RoomTypeModel.class))
                .orElse(null);
    }

    @Override
    public RoomTypeModel findById(int id) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id).orElse(null);

        if (roomTypeEntity == null) {
            return null;
        }

        RoomTypeModel roomTypeModel = modelMapper.map(roomTypeEntity, RoomTypeModel.class);
        List<String> imageURLs = roomTypeEntity.getRoomTypeImages().stream()
                .map(RoomTypeImageEntity::getImageURL)
                .collect(Collectors.toList());
        roomTypeModel.setImageURLs(imageURLs);

        return roomTypeModel;
    }

    @Override
    public List<RoomTypeModel> findAllActive() {
        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAllByIsDeletedFalse();
        return roomTypeEntities.stream()
                .map(roomTypeEntity -> {
                    RoomTypeModel roomTypeModel = modelMapper.map(roomTypeEntity, RoomTypeModel.class);
                    List<String> imageURLs = roomTypeEntity.getRoomTypeImages().stream()
                            .map(RoomTypeImageEntity::getImageURL)
                            .collect(Collectors.toList());
                    roomTypeModel.setImageURLs(imageURLs);
                    return roomTypeModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomTypeModel> findAllInactive() {
        return roomTypeRepository.findAllByIsDeletedTrue().stream()
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity, RoomTypeModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomTypeModel> findAll() {
        return roomTypeRepository.findAll().stream()
                .map(roomTypeEntity -> modelMapper.map(roomTypeEntity, RoomTypeModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomTypeModel saveRoom(RoomTypeModel roomTypeModel) {
        RoomTypeEntity roomTypeEntity = modelMapper.map(roomTypeModel, RoomTypeEntity.class);
        RoomTypeEntity savedRoomTypeEntity = roomTypeRepository.save(roomTypeEntity);
        return modelMapper.map(savedRoomTypeEntity, RoomTypeModel.class);
    }

    @Override
    public RoomTypeModel updateRoomType(int id, RoomTypeModel roomTypeModel, List<MultipartFile> imageFiles) {
        Optional<RoomTypeEntity> roomTypeEntityOptional = roomTypeRepository.findByIdAndIsDeletedFalse(id);
        if (roomTypeEntityOptional.isPresent()){
            RoomTypeEntity roomTypeEntity = roomTypeEntityOptional.get();

            if (roomTypeModel.getName() != null) {
                roomTypeEntity.setName(roomTypeModel.getName());
            }

            if (roomTypeModel.getAcreage() != 0) {
                roomTypeEntity.setAcreage(roomTypeModel.getAcreage());
            }

            if (roomTypeModel.getPriceEachRoom() != 0) {
                roomTypeEntity.setPriceEachRoom(roomTypeModel.getPriceEachRoom());
            }

            if (roomTypeModel.getDescription() != null) {
                roomTypeEntity.setDescription(roomTypeModel.getDescription());
            }

            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<String> imageURLs = new ArrayList<>();
                try {
                    for (MultipartFile imageFile : imageFiles) {
                        String imageURL = firebaseService.uploadImagesToFirebase(imageFile); // Upload img to Firebase storage
                        imageURLs.add(imageURL);
                    }

                    // Remove old images if necessary
                    if (roomTypeEntity.getRoomTypeImages() != null) {
                        roomTypeEntity.getRoomTypeImages().clear();
                    } else {
                        roomTypeEntity.setRoomTypeImages(new ArrayList<>());
                    }

                    // Store imageURLs into table: service_image
                    for (String imageURL : imageURLs) {
                        RoomTypeImageEntity roomTypeImageEntity = new RoomTypeImageEntity();
                        roomTypeImageEntity.setImageURL(imageURL);
                        roomTypeImageEntity.setRoomType(roomTypeEntity);
                        roomTypeEntity.getRoomTypeImages().add(roomTypeImageEntity);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Save the updated entity
            try {
                RoomTypeEntity updatedRoomTypeEntity = roomTypeRepository.save(roomTypeEntity);

                // Map the updated entity back to the model
                RoomTypeModel responseRoomTypeModel = modelMapper.map(updatedRoomTypeEntity, RoomTypeModel.class);

                // Set the image URLs for the response
                List<String> imagesURLs = updatedRoomTypeEntity.getRoomTypeImages().stream()
                        .map(RoomTypeImageEntity::getImageURL)
                        .toList();
                responseRoomTypeModel.setImageURLs(imagesURLs);

                return responseRoomTypeModel;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }else {
            return null;
        }
    }
}
