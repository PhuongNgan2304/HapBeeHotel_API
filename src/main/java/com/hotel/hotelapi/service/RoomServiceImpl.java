package com.hotel.hotelapi.service;

<<<<<<< HEAD
import com.hotel.hotelapi.entity.BranchEntity;
import com.hotel.hotelapi.entity.RoomEntity;
import com.hotel.hotelapi.model.BranchModel;
import com.hotel.hotelapi.model.RoomModel;
import com.hotel.hotelapi.repository.BranchRepository;
import com.hotel.hotelapi.repository.RoomRepository;
import com.hotel.hotelapi.repository.ServiceRepository;
=======
import com.hotel.hotelapi.entity.BookingEntity;
import com.hotel.hotelapi.entity.BranchEntity;
import com.hotel.hotelapi.entity.RoomEntity;
import com.hotel.hotelapi.entity.RoomTypeEntity;
import com.hotel.hotelapi.model.CheckInOutModel;
import com.hotel.hotelapi.model.RoomModel;
import com.hotel.hotelapi.model.RoomRequestModel;
import com.hotel.hotelapi.repository.*;
>>>>>>> 2c31b00 (update commit)
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 2c31b00 (update commit)
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
<<<<<<< HEAD
    private BranchRepository branchRepository;

    @Override
    public RoomModel findById(int id) {
        RoomEntity roomEntity = roomRepository.findById(id).orElse(null);
        return roomEntity != null ? modelMapper.map(roomEntity, RoomModel.class) : null;
=======
    private BookingRepository bookingRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Override
    public List<RoomModel> findAllByRoomTypeAndBranch(int roomTypeId, int branchId) {
//        List<RoomEntity> roomEntities = roomRepository.findRoomEntitiesByRoomTypeIdAndBranchIdAndIsDeletedIsFalse(roomTypeId,branchId);
//        return roomEntities.stream()
//                .map(roomEntity -> modelMapper.map(roomEntity, RoomModel.class))
//                .collect(Collectors.toList());
        List<RoomEntity> roomEntities = roomRepository.findRoomEntitiesByRoomTypeIdAndBranchIdAndIsDeletedIsFalse(roomTypeId, branchId);
        return roomEntities.stream()
                .map(roomEntity -> {
                    RoomModel roomModel = modelMapper.map(roomEntity, RoomModel.class);
                    // Kiểm tra trạng thái đặt phòng hiện tại
                    List<BookingEntity> currentBookings = bookingRepository.findCurrentBookingsByRoomId(roomEntity.getId());
                    if (!currentBookings.isEmpty())
                        roomModel.setStatus("isBooking");
                    else
                        roomModel.setStatus("isEmpty");
                    return roomModel;
                })
                .collect(Collectors.toList());
>>>>>>> 2c31b00 (update commit)
    }

    @Override
    public List<RoomModel> findAll() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        return roomEntities.stream()
                .map(roomEntity -> modelMapper.map(roomEntity, RoomModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomModel create(RoomModel roomModel) {
        RoomEntity roomEntity = modelMapper.map(roomModel, RoomEntity.class);
        RoomEntity savedRoomEntity = roomRepository.save(roomEntity);
        return modelMapper.map(savedRoomEntity, RoomModel.class);
    }

    @Override
    public RoomModel update(int id, RoomModel RoomDTO) {
<<<<<<< HEAD
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(id);
        if (roomEntityOptional.isPresent()) {
            RoomEntity roomEntity = roomEntityOptional.get();

            //Update branchEntity with branchModel's fields
            roomEntity.setAceage(RoomDTO.getAceage());
            roomEntity.setLine(RoomDTO.getLine());
            roomEntity.setBranch(RoomDTO.getBranch());

// BỎ         BranchEntity branchEntity = roomEntity.getBranch();
//            branchEntity.setId(RoomDTO.getBranch().getId());
//
//            roomEntity.setBranch(branchEntity);


//            int branchid = RoomDTO.getBranch().getId();
//            BranchEntity branchEntity = branchRepository.findById(branchid)
//                    .orElseThrow(() -> new EntityNotFoundException("Branch with ID " + branchid + "is not found"));
//
//            //Map BranchEntity to BranchModel and set it in the RoomModel
//            BranchModel branchModel = modelMapper.map(branchEntity, BranchModel.class);
//            RoomDTO.setBranch(branchModel);

//            //Save RoomEntity and map to RoomModel
                RoomEntity updatedRoomEntity = roomRepository.save(roomEntity);
                return modelMapper.map(updatedRoomEntity, RoomModel.class);
=======
        Optional<RoomEntity> roomEntityOptional = roomRepository.findByIdAndIsDeletedFalse(id);
        if (roomEntityOptional.isPresent()) {
            RoomEntity roomEntity = roomEntityOptional.get();

            RoomEntity updatedRoomEntity = roomRepository.save(roomEntity);
            return modelMapper.map(updatedRoomEntity, RoomModel.class);
>>>>>>> 2c31b00 (update commit)

        } else {
            throw new EntityNotFoundException("Room with ID" + id + "not found");
        }
    }

    @Override
<<<<<<< HEAD
    public void delete(int id) {
        roomRepository.deleteById(id);
=======
    public boolean softDelete(int id) {
        Optional<RoomEntity> roomEntityOptional = roomRepository.findByIdAndIsDeletedFalse(id);
        if(roomEntityOptional.isPresent()){
            RoomEntity roomEntity = roomEntityOptional.get();
            roomEntity.setDeleted(true);
            roomRepository.save(roomEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRoomEmpty(CheckInOutModel checkInOutModel){
//        Optional<RoomEntity> room = orderRepository.findOrderByDateRange(checkInOutModel.getCheckIn(),checkInOutModel.getCheckOut());
//        return room.isPresent();
        return false;
    }
    @Override
    public List<RoomModel> findAvailableRoomModels(int roomId, int branchId, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<RoomEntity> availableRooms = roomRepository.findAvailableRooms(roomId,branchId,checkIn,checkOut);
        return availableRooms.stream()
                .map(availableRoom -> modelMapper.map(availableRoom, RoomModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomModel> saveRoomList(List<RoomRequestModel> requestModels) {
        List<RoomEntity> roomEntities = requestModels.stream().map(requestModel -> {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setNumber(requestModel.getNumber());

            // Fetch the branch and room type entities
            BranchEntity branchEntity = branchRepository.findById(requestModel.getBranchid())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(requestModel.getRoomtypeid())
                    .orElseThrow(() -> new RuntimeException("Room Type not found"));

            roomEntity.setBranch(branchEntity);
            roomEntity.setRoomType(roomTypeEntity);
            roomEntity.setDeleted(false);  // Assuming new rooms are not deleted by default

            return roomEntity;
        }).collect(Collectors.toList());

        List<RoomEntity> savedRoomEntities = roomRepository.saveAll(roomEntities);

        return savedRoomEntities.stream()
                .map(savedRoomEntity -> modelMapper.map(savedRoomEntity, RoomModel.class))
                .collect(Collectors.toList());
>>>>>>> 2c31b00 (update commit)
    }
}
