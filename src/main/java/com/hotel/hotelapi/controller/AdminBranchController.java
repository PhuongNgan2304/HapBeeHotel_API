package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.model.ServiceModel;
import com.hotel.hotelapi.service.BranchServiceImpl;
import com.hotel.hotelapi.service.IBranchService;
import com.hotel.hotelapi.utils.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/management/branch")
public class AdminBranchController {
    @Autowired
    IBranchService branchService = new BranchServiceImpl();

    @GetMapping("/{id}")
    public Response getBranchById(
            @PathVariable int id,
            @RequestParam(value = "showDisabled", required = false, defaultValue = "false") boolean showDisabled,
            @RequestParam(value = "showInActive", required = false, defaultValue = "false") boolean showInActive)
    {
        Optional<BranchModel> optionalBranchModel;
        if(showDisabled){
            optionalBranchModel = Optional.ofNullable(branchService.findById(id));
        } else if (showInActive) {
            optionalBranchModel = Optional.ofNullable(branchService.findByIdInactive(id));
        } else {
            optionalBranchModel = Optional.ofNullable(branchService.findByIdActive(id));
        }
        return optionalBranchModel
                .map(branchModel -> new Response(true, "Branch found successfully!", branchModel))
                .orElse(new Response(false, "Branch not found!", null));

//        Optional<BranchModel> optionalBranchModel = Optional.ofNullable(branchService.findById(id));
//
//        return optionalBranchModel
//                .map(branchModel -> new Response(true, "Branch found successfully!", branchModel))
//                .orElse(new Response(false, "Branch not found!", null));
    }

    @GetMapping("/all")
    public Response getAllBranches(
            @RequestParam(value = "showAll", required = false, defaultValue = "false") boolean showAll,
            @RequestParam(value = "showInActive", required = false, defaultValue = "false") boolean showInActive)
    {
        Response response = new Response();
        List<BranchModel> branchModels;
        if (showInActive) {
            branchModels = branchService.findAllInactive();
            response = new Response(true,"Fetch data success!",branchModels);
            return response;
        }
        if (showAll){
            branchModels = branchService.findAll();
            response = new Response(true,"Fetch data success!",branchModels);
            return response;
        }
        else {
            branchModels = branchService.findAllActive();
            response = new Response(true,"Fetch data success!",branchModels);
            return response;
        }
    }

    @PostMapping("/create")
    public Response createBranch(@ModelAttribute BranchModel branchModel, @RequestParam(value = "imageFiles", required = false)List<MultipartFile> imageFiles){
        List<MultipartFile> filesToUpload;
        if(imageFiles == null || imageFiles.isEmpty()) {
            MultipartFile defaultImage = MultipartFileUtils.createDefaultImage();
            filesToUpload = Collections.singletonList(defaultImage);
        } else {
            filesToUpload = imageFiles;
        }
        BranchModel createdBranch = branchService.create(branchModel, filesToUpload);
        return new Response(true, "Branch created successfully", createdBranch);
    }

    @PutMapping("/{id}/update")
    public Response updateBranch(@PathVariable int id, @ModelAttribute BranchModel branchModel,
                                 @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles){
        BranchModel updateBranch = branchService.update(id, branchModel, imageFiles);
        if(updateBranch!=null){
            return new Response(true, "Branch is updated successfully!", updateBranch);
        }
        return new Response(false, "Branch is not found", null);
    }
//    @DeleteMapping("/{id}/delete") // đổi thành PutMapping
//    public Response deleteBranch(@PathVariable int id) {
//        boolean isDeleted = branchService.softDelete(id);
//        if (isDeleted) {
//            return new Response(true, "Branch is deleted successfully", null);
//        }
//        return new Response(false, "Branch is not found", null);
//    }
    @DeleteMapping("/{id}") //tương tự như delete, nhưng nó chỉ ẩn đi chứ không xóa trong database// set cái isDelete = true
    public Response hideBranch(@PathVariable int id){
        boolean hide = branchService.hide(id);
        if (hide) {
            return new Response(true, "Branch is hide successfully", null);
        }
        return new Response(false, "Branch is not found", null);
    }

    public Response showBranch(@PathVariable int id){
        boolean show = branchService.show(id);
        if (show) {
            return new Response(true, "Branch is show successfully", null);
        }
        return new Response(false, "Branch is not found", null);
    }

}
