package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.BranchEntity;
<<<<<<< HEAD
import com.hotel.hotelapi.entity.ServiceEntity;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.repository.BranchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import com.hotel.hotelapi.entity.BranchImageEntity;
import com.hotel.hotelapi.entity.ServiceImageEntity;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.repository.BranchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BranchServiceImpl implements IBranchService{
    @Autowired
    private ModelMapper modelMapper;
<<<<<<< HEAD

    @Autowired
    private BranchRepository branchRepository;
    @Override
    public BranchModel findById(int id) {
        BranchEntity branchEntity = branchRepository.findById(id).orElse(null);
=======
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private FirebaseServiceImpl firebaseServiceImpl;

//    @Autowired
//    private RoomRepository roomRepository;
    @Override
    public BranchModel findByIdActive(int id) {
        BranchEntity branchEntity = branchRepository.findByIdAndIsDeletedFalse(id).orElse(null);
>>>>>>> 2c31b00 (update commit)
        return branchEntity != null ? modelMapper.map(branchEntity, BranchModel.class) : null;
    }

    @Override
<<<<<<< HEAD
    public BranchModel findByLocation(String location) {
        BranchEntity branchEntity = branchRepository.findByLocation(location).orElse(null);
=======
    public BranchModel findByIdInactive(int id) {
        BranchEntity branchEntity = branchRepository.findByIdAndIsDeletedTrue(id).orElse(null);
        return branchEntity != null ? modelMapper.map(branchEntity, BranchModel.class) : null;
    }

    @Override
    public List<BranchModel> findAllActive() {
        List<BranchEntity> branchEntities = branchRepository.findAllByIsDeletedFalse();
        return branchEntities.stream()
                .map(branchEntity -> modelMapper.map(branchEntity, BranchModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BranchModel> findAllInactive() {
        List<BranchEntity> branchEntities = branchRepository.findAllByIsDeletedTrue();
        return branchEntities.stream()
                .map(branchEntity -> modelMapper.map(branchEntity, BranchModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BranchModel findById(int id) {
        BranchEntity branchEntity = branchRepository.findById(id).orElse(null);
>>>>>>> 2c31b00 (update commit)
        return branchEntity != null ? modelMapper.map(branchEntity, BranchModel.class) : null;
    }

    @Override
    public List<BranchModel> findAll() {
        List<BranchEntity> branchEntities = branchRepository.findAll();
        return branchEntities.stream()
                .map(branchEntity -> modelMapper.map(branchEntity, BranchModel.class))
                .collect(Collectors.toList());

    }

    @Override
<<<<<<< HEAD
    public BranchModel create(BranchModel branchModel) {
        BranchEntity branchEntity = modelMapper.map(branchModel, BranchEntity.class);
        BranchEntity savedBranchEntity = branchRepository.save(branchEntity);
        return modelMapper.map(savedBranchEntity, BranchModel.class);
    }

    @Override
    public BranchModel update(int id, BranchModel branchDTO) {
        Optional<BranchEntity> branchEntityOptional = branchRepository.findById(id);
        if (branchEntityOptional.isPresent()){
            BranchEntity branchEntity = branchEntityOptional.get();

            //Update branchEntity with branchModel's fields
            branchEntity.setImage(branchDTO.getImage());
            branchEntity.setLocation(branchDTO.getLocation());

=======
    public BranchModel create(BranchModel branchModel, List<MultipartFile> imageFiles) {
        BranchEntity branchEntity = modelMapper.map(branchModel, BranchEntity.class);
        if(imageFiles!=null && !imageFiles.isEmpty()){
            List<String> imageURLs = new ArrayList<>();
            try{
                for (MultipartFile imageFile : imageFiles){
                    String imageURL = firebaseServiceImpl.uploadImagesToFirebase(imageFile);
                    imageURLs.add(imageURL);
                }

                //Store imageURLs into table: branch_image
                List<BranchImageEntity> branchImages = imageURLs.stream()
                        .map(imageURL -> {
                            BranchImageEntity branchImage = new BranchImageEntity();
                            branchImage.setImageURL(imageURL);
                            branchImage.setBranch(branchEntity);
                            return branchImage;
                        })
                        .toList();
                branchEntity.setImages(branchImages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Store new branch into table: branch
        branchEntity.setDeleted(false);
        BranchEntity savedBranchEntity = branchRepository.save(branchEntity);
        BranchModel responseBranch = modelMapper.map(savedBranchEntity, BranchModel.class);

        //map through savedBranchEntity to show imageURLs in response
        List<String> imagesURLs = savedBranchEntity.getImages().stream()
                .map(BranchImageEntity::getImageURL)
                .toList();
//        responseBranch.setImageURLs(imagesURLs);
        return responseBranch;
    }

    @Override
    public BranchModel update(int id, BranchModel branchDTO, List<MultipartFile> imageFiles) {
        Optional<BranchEntity> branchEntityOptional = branchRepository.findByIdAndIsDeletedFalse(id);
        if (branchEntityOptional.isPresent()){
            BranchEntity branchEntity = branchEntityOptional.get();

            if (branchDTO.getLocation() != null) {
                branchEntity.setLocation(branchDTO.getLocation());
            }
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<String> imageURLs = new ArrayList<>();
                try {
                    for (MultipartFile imageFile : imageFiles) {
                        String imageURL = firebaseServiceImpl.uploadImagesToFirebase(imageFile); // Upload img to Firebase storage
                        imageURLs.add(imageURL);
                    }

                    // Remove old images if necessary
                    if (branchEntity.getImages() != null) {
                        branchEntity.getImages().clear();
                    } else {
                        branchEntity.setImages(new ArrayList<>());
                    }

                    // Store imageURLs into table: service_image
                    for (String imageURL : imageURLs) {
                        BranchImageEntity branchImage = new BranchImageEntity();
                        branchImage.setImageURL(imageURL);
                        branchImage.setBranch(branchEntity);
                        branchEntity.getImages().add(branchImage);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
>>>>>>> 2c31b00 (update commit)
            //Save branchEntity
            BranchEntity updatedBranchEntity = branchRepository.save(branchEntity);

            return modelMapper.map(updatedBranchEntity, BranchModel.class);

        }else {
            return null;
        }
    }

    @Override
<<<<<<< HEAD
    public void delete(int id) {
        branchRepository.deleteById(id);
=======
    public boolean hide(int id) {
        Optional<BranchEntity> branchEntityOptional = branchRepository.findByIdAndIsDeletedFalse(id);
        if(branchEntityOptional.isPresent()){
            BranchEntity branchEntity = branchEntityOptional.get();
            branchEntity.setDeleted(true);
            branchRepository.save(branchEntity);

            //Khi Branch nào có softDele=true thì Room thuộc Branch đó cũng sẽ có softDele=true
//            List<RoomEntity> rooms = roomRepository.findAllByBranchIdAndIsDeletedFalse(id);
//            for (RoomEntity room : rooms) {
//                room.setDeleted(true);
//            }
//            // Lưu lại các thay đổi cho tất cả các RoomEntity
//            roomRepository.saveAll(rooms);

            return true;
        }
        return false;
    }

    @Override
    public boolean show(int id) {
        Optional<BranchEntity> branchEntityOptional = branchRepository.findByIdAndIsDeletedTrue(id);
        if(branchEntityOptional.isPresent()){
            BranchEntity branchEntity = branchEntityOptional.get();
            branchEntity.setDeleted(false);
            branchRepository.save(branchEntity);

            //Khi Branch nào có softDele=true thì Room thuộc Branch đó cũng sẽ có softDele=true
//            List<RoomEntity> rooms = roomRepository.findAllByBranchIdAndIsDeletedFalse(id);
//            for (RoomEntity room : rooms) {
//                room.setDeleted(true);
//            }
//            // Lưu lại các thay đổi cho tất cả các RoomEntity
//            roomRepository.saveAll(rooms);

            return true;
        }
        return false;
    }

    @Override
    public PageResponse<BranchModel> findAllActiveAndSearch(String searchName, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        pageNo = (pageNo < 1) ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<BranchEntity> activeBranches = null;

        if(!searchName.isEmpty()){
            activeBranches = branchRepository.findByLocationContainingIgnoreCaseAndIsDeletedFalse(searchName, pageable);
        }
        else{
            activeBranches = branchRepository.findAllByIsDeletedFalse(pageable);
        }
        List<BranchModel> content = activeBranches.map(branchEntity ->
                modelMapper.map(branchEntity, BranchModel.class)).getContent();

        int currentPage = activeBranches.getNumber()+1;
        int totalPages = activeBranches.getTotalPages();

        return new PageResponse<>(content, currentPage, totalPages);
    }
    @Override
    public List<BranchModel> findAllByRoomType(int id){
            List<BranchEntity> branchEntities = branchRepository.findAllByAndRoomsRoomTypeIdAndIsDeletedIsFalse(id);
            return branchEntities.stream()
                    .map(branchEntity -> modelMapper.map(branchEntity, BranchModel.class))
                    .collect(Collectors.toList());

    }

    @Override
    public List<BranchModel> findBranchesByRoomType(int roomTypeId) {
        List<BranchEntity> branchEntities = branchRepository.findAllByAndRoomsRoomTypeIdAndIsDeletedIsFalse(roomTypeId);
        return branchEntities.stream()
                .map(branchEntity -> modelMapper.map(branchEntity, BranchModel.class))
                .collect(Collectors.toList());
>>>>>>> 2c31b00 (update commit)
    }
}
