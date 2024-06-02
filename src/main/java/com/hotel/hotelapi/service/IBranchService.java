package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.BranchModel;
<<<<<<< HEAD
import com.hotel.hotelapi.model.ServiceModel;
=======
import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.model.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
>>>>>>> 2c31b00 (update commit)

import java.util.List;

public interface IBranchService {
<<<<<<< HEAD
    BranchModel findById(int id);

    BranchModel findByLocation(String location);

    List<BranchModel> findAll();

    BranchModel create (BranchModel branchModel);

    BranchModel update (int id, BranchModel branchDTO);

    void delete(int id);
=======
    BranchModel findByIdActive(int id);
    BranchModel findByIdInactive(int id);
    List<BranchModel> findAllActive();
    List<BranchModel> findAllInactive();
    //BranchModel findByLocation(String location);
    BranchModel findById(int id);
    List<BranchModel> findAll();
    BranchModel create (BranchModel branchModel, List<MultipartFile> imageFiles);
    BranchModel update (int id, BranchModel branchDTO, List<MultipartFile> imageFiles);
    boolean hide(int id); //giống như softDelete (xóa tạm thời/ hay còn gọi là ẩn đi)
    boolean show(int id); //bật lên lại
    PageResponse<BranchModel> findAllActiveAndSearch(String searchName, int pageNo, int pageSize, String sortBy, String sortDir);

    List<BranchModel> findAllByRoomType(int id);

    List<BranchModel> findBranchesByRoomType(int roomTypeId);
>>>>>>> 2c31b00 (update commit)
}
