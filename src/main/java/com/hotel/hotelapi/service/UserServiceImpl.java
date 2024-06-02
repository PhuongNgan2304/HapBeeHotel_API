package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.*;
import com.hotel.hotelapi.model.BookingResponseDTO;
import com.hotel.hotelapi.model.CartRoomModel;
import com.hotel.hotelapi.model.PaymentModel;
import com.hotel.hotelapi.model.RoomTypeModel;
import com.hotel.hotelapi.repository.*;
import com.hotel.hotelapi.user.ChangePasswordRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private CartRoomRepository cartRoomRepository;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    @Override
    @Transactional
    public PaymentModel payBooking(int bookingId, String paymentMethod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Tìm kiếm user bằng email
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm kiếm booking bằng ID
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        PaymentEntity payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Kiểm tra xem user có khớp với user trong booking không
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User does not match the booking user: booking :"
                    + booking.getUser().getId() + " user token: " + user.getId());
        }

        // Thực hiện thanh toán
        try {
            // Tạo mới PaymentEntity
            payment.setBooking(booking);
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                payment.setPaymentMethod(paymentMethod);
            }
            payment.setPaymentStatus("PAID");
            payment.setTimestamp(LocalDateTime.now());

            // Thiết lập quan hệ giữa booking và payment
//            booking.setPayment(payment);

            // Lưu booking và payment vào cơ sở dữ liệu
//            bookingRepository.save(booking);

            // Chuyển đổi BookingEntity sang BookingResponseDTO
            BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
            bookingResponseDTO.setBookingId(booking.getId());
            bookingResponseDTO.setUserId(booking.getUser().getId());
            bookingResponseDTO.setRoomId(booking.getRoom().getId());
            bookingResponseDTO.setCheckIn(booking.getCheckIn());
            bookingResponseDTO.setCheckOut(booking.getCheckOut());

            // Lấy danh sách serviceIds
            List<Integer> serviceIds = booking.getBooking_services().stream()
                    .map(bookingService -> bookingService.getService().getId())
                    .collect(Collectors.toList());
            bookingResponseDTO.setServiceIds(serviceIds);

            // Chuyển đổi PaymentEntity sang PaymentModel
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setId(payment.getId());
            paymentModel.setBooking(bookingResponseDTO);
            paymentModel.setPaymentMethod(payment.getPaymentMethod());
            paymentModel.setTotalMoney(payment.getTotalMoney());
            paymentModel.setPaymentStatus(payment.getPaymentStatus());
            paymentModel.setTimestamp(payment.getTimestamp());

            return paymentModel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Payment failed due to an error: " + e.getMessage());
        }
    }

    @Override
    public RoomTypeModel addRoomToCart(int roomTypeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Tìm kiếm user bằng email
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm kiếm loại phòng
        RoomTypeEntity roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        // Kiểm tra xem loại phòng đã bị xóa chưa
        if (roomType.isDeleted()) {
            throw new RuntimeException("Room type has been deleted.");
        }

        // Tạo một đối tượng CartRoomEntity mới
        CartRoomEntity cartRoom = new CartRoomEntity();
        cartRoom.setUser(user);
        cartRoom.setRoomType(roomType);

        // Thêm vào danh sách giỏ hàng của người dùng
        user.getCarts().add(cartRoom);
        userRepository.save(user);

        return modelMapper.map(roomType, RoomTypeModel.class);
    }

    @Override
    public String removeRoomFromCart(int roomTypeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Tìm kiếm user bằng email
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm kiếm loại phòng
        RoomTypeEntity roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        // Tìm kiếm mục trong giỏ hàng của người dùng
        CartRoomEntity cartRoom = user.getCarts().stream()
                .filter(cart -> cart.getRoomType().getId() == roomTypeId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Room not found in cart"));

        // Xóa mục khỏi giỏ hàng của người dùng
        user.getCarts().remove(cartRoom);
        cartRoomRepository.delete(cartRoom);

        return "Remove success";
    }

    // Hàm tính tổng chi phí dịch vụ
    private double calculateTotalServicesCost(List<BookingServiceEntity> bookingServices) {
        return bookingServices.stream()
                .mapToDouble(bookingService -> bookingService.getService().getPrice())
                .sum();
    }
}
