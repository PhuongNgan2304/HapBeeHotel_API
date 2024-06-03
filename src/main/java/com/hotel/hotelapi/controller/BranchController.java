package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.PageResponse;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.BranchServiceImpl;
import com.hotel.hotelapi.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {
    @Autowired
    IBranchService service = new BranchServiceImpl();
    @GetMapping("/{id}")
    public Response getBranchById(@PathVariable int id) {
        BranchModel branchModel = service.findByIdActive(id);
        if (branchModel != null) {
            return new Response(true, "Branch found successfully!", branchModel);
        }
        return new Response(false, "Branch not found!", null);
    }

    @GetMapping("/all")
    public Response getAllActiveBranches(
            @RequestParam(value = "searchLocation", defaultValue = "", required = false) String searchLocation,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageResponse<BranchModel> pageResponse = service.findAllActiveAndSearch(searchLocation, pageNo, pageSize, sortBy, sortDir);
        if (pageResponse.getContent().isEmpty()) {
            return new Response(false, "Branches not found!", null);
        }
        return new Response(true, "Branch found successfully!", pageResponse);
    }
    @GetMapping("/findByRoomType/{roomTypeId}")
    public ResponseEntity<List<BranchModel>> findByRoomType(@PathVariable int roomTypeId) {
        List<BranchModel> branches = service.findBranchesByRoomType(roomTypeId);
        return ResponseEntity.ok(branches);
    }
}
