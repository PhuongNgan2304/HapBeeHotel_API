package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.CartRoomEntity;
import com.hotel.hotelapi.entity.RoomTypeEntity;
import com.hotel.hotelapi.entity.UserEntity;
import com.hotel.hotelapi.model.CartRoomModel;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.repository.CartRoomRepository;
import com.hotel.hotelapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartRoomServiceImpl implements ICartRoomService{
    @Autowired
    private CartRoomRepository cartRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<RoomTypeModel> getCartRoomByToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartRoomEntity> cartRoomEntities = cartRoomRepository.findAllByUserEmail(user.getEmail());

        List<RoomTypeModel> roomTypeModels = new ArrayList<>();
        for (CartRoomEntity cartRoomEntity : cartRoomEntities
             ) {
            RoomTypeModel roomTypeModel = modelMapper.map(cartRoomEntity.getRoomType(), RoomTypeModel.class);
            roomTypeModels.add(roomTypeModel);
        }
        return roomTypeModels;
    }


}
