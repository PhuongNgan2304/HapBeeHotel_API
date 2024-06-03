package com.hotel.hotelapi.service;

import com.hotel.hotelapi.model.*;
import com.hotel.hotelapi.entity.*;
import com.hotel.hotelapi.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private CartRoomRepository cartRoomRepository;
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        // Tính toán thời gian giữa check-in và check-out
        Duration duration = Duration.between(bookingRequestDTO.getCheckIn(), bookingRequestDTO.getCheckOut());
        if (duration.toHours() < 1) {
            throw new RuntimeException("Check-in and check-out times must be at least 1 hour apart.");
        }

        // Kiểm tra xung đột đặt phòng
        List<BookingEntity> conflictingBookings = bookingRepository.findConflictingBookings(
                bookingRequestDTO.getRoomId(), bookingRequestDTO.getCheckIn(), bookingRequestDTO.getCheckOut());

        if (!conflictingBookings.isEmpty()) {
            throw new RuntimeException("The room is not available for the selected time period.");
        }

        // Lấy thông tin người dùng từ context bảo mật
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoomEntity room = roomRepository.findById(bookingRequestDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Tạo booking mới
        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckIn(bookingRequestDTO.getCheckIn());
        booking.setCheckOut(bookingRequestDTO.getCheckOut());
//        booking.setPaymentStatus("waiting");

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Lưu các dịch vụ nếu có
        if (bookingRequestDTO.getServiceIds() != null && !bookingRequestDTO.getServiceIds().isEmpty()) {
            List<BookingServiceEntity> bookingServices = bookingRequestDTO.getServiceIds().stream().map(serviceId -> {
                ServiceEntity service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));
                BookingServiceEntity bookingService = new BookingServiceEntity();
                bookingService.setBooking(savedBooking);
                bookingService.setService(service);
                return bookingService;
            }).collect(Collectors.toList());

            bookingServiceRepository.saveAll(bookingServices);
        }

        // Tạo thanh toán
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentMethod(bookingRequestDTO.getPaymentMethod());
        paymentEntity.setPaymentStatus("waiting");
        paymentEntity.setTimestamp(LocalDateTime.now());
        paymentEntity.setTotalMoney(bookingRequestDTO.getTotal_money());
        paymentEntity.setBooking(savedBooking);
        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);

        user.getCarts().stream()
                .filter(cart -> cart.getRoomType().getId() == room.getRoomType().getId())
                .findFirst()
                .ifPresent(cartRoom -> {
                    user.getCarts().remove(cartRoom);
                    cartRoomRepository.delete(cartRoom);
                });

        // Trả về kết quả đặt phòng
        return new BookingResponseDTO(
                savedBooking.getId(),
                savedBooking.getUser().getId(),
                savedBooking.getRoom().getId(),
                savedBooking.getCheckIn(),
                savedBooking.getCheckOut(),
                savedPayment.getPaymentStatus(),
                bookingRequestDTO.getServiceIds(),
                savedPayment.getId()
        );
    }


    @Override
    public List<BookingBranchResponseDTO> getBookingsByBranch(int branchId) {
        List<BookingEntity> bookings = bookingRepository.findBookingsByBranch(branchId);
        List<BookingBranchResponseDTO> bookingBranchResponseDTOS = new ArrayList<>();

        for (BookingEntity booking : bookings) {
            BookingBranchResponseDTO bookingBranchResponseDTO = buildBookingBranchResponseDTO(booking);
            bookingBranchResponseDTOS.add(bookingBranchResponseDTO);
        }

        return bookingBranchResponseDTOS;
    }
    private UserModel getUserDetails(UserEntity user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setFullname(user.getFullname());
        userModel.setEmail(user.getEmail());
        userModel.setBirthday(user.getBirthday());
        userModel.setAddress(user.getAddress());
        userModel.setPhone(user.getPhone());
        userModel.setActive(user.isActive());
        userModel.setCreateDay(user.getCreateDay());
        return userModel;
    }
    private PaymentModel getPaymentDetails(PaymentEntity payment){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setId(payment.getId());
        paymentModel.setPaymentMethod(paymentModel.getPaymentMethod());
        paymentModel.setTotalMoney(paymentModel.getTotalMoney());
        paymentModel.setPaymentStatus(payment.getPaymentStatus());
        paymentModel.setTimestamp(payment.getTimestamp());
        return paymentModel;
    }
    private RoomModel getRoomDetails(RoomEntity room) {
        RoomModel roomModel = new RoomModel();
        roomModel.setId(room.getId());
        //roomModel.setStatus("isBooking?/isEmpty?");
        roomModel.setNumber(Integer.parseInt(room.getNumber()));
        roomModel.setRoomType(room.getRoomType().getName());
        roomModel.setDeleted(room.isDeleted()); //trường is_deleted thì cần false (chưa đặt) --> mới cho đặt
        return roomModel;
    }
    private List<BookingServiceModel> listServiceDetails(List<BookingServiceEntity> serviceEntityList){
        List<BookingServiceModel> serviceModels = new ArrayList<>();
        for (BookingServiceEntity service : serviceEntityList) {
            BookingServiceModel bookingServiceModel = new BookingServiceModel();
            bookingServiceModel.setId(service.getId());
            bookingServiceModel.setName(service.getService().getName());
            bookingServiceModel.setPrice(service.getService().getPrice());
            bookingServiceModel.setDeleted(service.getService().isDeleted()); //này cũng cần false nìii
            serviceModels.add(bookingServiceModel);
        }
        return serviceModels;
    }
    private BookingBranchResponseDTO buildBookingBranchResponseDTO(BookingEntity booking) {
        // Lấy thông tin room
        RoomEntity room = booking.getRoom();
        RoomModel roomDetails = getRoomDetails(room);

        // Lấy thông tin user
        UserEntity user = booking.getUser();
        UserModel userDetails = getUserDetails(user);

        //Lấy thông tin List BookingService
        List<BookingServiceModel> serviceModels = listServiceDetails(booking.getBooking_services());

        // Lấy thông tin payment
        PaymentEntity payment = booking.getPayment();
        PaymentModel paymentDetail = getPaymentDetails(payment);

        // Tạo BookingResponseDTO từ thông tin lấy được
        BookingBranchResponseDTO bookDTO = new BookingBranchResponseDTO();
        bookDTO.setBranchName(room.getBranch().getLocation());
        bookDTO.setBookingId(booking.getId());
        bookDTO.setCheckIn(booking.getCheckIn());
        bookDTO.setCheckOut(booking.getCheckOut());
        bookDTO.setUserModel(userDetails);
        bookDTO.setRoomModel(roomDetails);
        bookDTO.setBookingServiceEntityList(serviceModels);
        bookDTO.setPaymentModel(paymentDetail);

        return bookDTO;
    }
}
