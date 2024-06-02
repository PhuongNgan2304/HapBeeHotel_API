package com.hotel.hotelapi;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

=======
import com.hotel.hotelapi.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties(StorageProperties.class)
>>>>>>> 2c31b00 (update commit)
@SpringBootApplication
public class HotelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApiApplication.class, args);
	}

<<<<<<< HEAD
=======

//	@Autowired
//	private OrderServiceImpl orderService;
//
//	@Autowired
//	private OrderRepository orderRepository;
//
//	@Autowired
//	private BookRepository bookRepository;
//
//	@Bean
//	public CommandLineRunner run() {
//		return args -> {
//			Map<BookEntity, Double> totalOrderByBook = orderService.calculateTotalOrderByBook();
//			totalOrderByBook.forEach((book, total) -> {
//				System.out.println("Book ID: " + book.getId() + " Customer: " + book.getCustomer().getFullName()+ ", Total Order: " + total);
//			});
//		};
//	}

//	@Bean
//	public CommandLineRunner run() {
//		return args -> {
//			// Lấy tất cả các BookEntity từ cơ sở dữ liệu
//			List<BookEntity> books = bookRepository.findAll();
//
//			// Duyệt qua từng BookEntity và in kết quả ra console
//			for (BookEntity book : books) {
//				double total = orderService.calculateTotalOrderForBook(book);
//				System.out.println("Book ID: " + book.getId() + ", Total Order: " + total);
//			}
//		};
//	}


//	@Bean
//
//	public CommandLineRunner run() {
//		return args -> {
////			// Lấy tất cả các OrderEntity từ cơ sở dữ liệu
//////			List<OrderEntity> orders = orderRepository.findAll();
//////			List<BookEntity> books = bookRepository.findAll();
//////
//////			for(BookEntity book: books){
//////				System.out.println("Book ID: " + book.getId() + ", Cutomer: " + book.getCustomer().getFullName());
//////				// Duyệt qua từng OrderEntity và tính toán tổng tiền
//////				for (OrderEntity order : orders) {
//////					double total = orderService.calculateToTalOrder(order);
//////					// In ra tổng tiền của OrderEntity hiện tại
//////					System.out.println("    Order ID: " + order.getId() + ", Total: " + total);
//////				}
//////			}
//			Map<BookEntity, Double> totalOrderByBook = new HashMap<>();
//
//			List<OrderEntity> orders = orderRepository.findAll();
//
//			List<BookEntity> books = bookRepository.findAll();
//			for(BookEntity book : books){
//				double tong = 0.0;
//				double a = 12;
//				System.out.println("BOOK_ID: " + book.getId());
//				for (OrderEntity order : orders){
//					System.out.println("BOOK_ID_order: " + order.getBook().getId());
//					System.out.println("BOOK_ID_confirm: " + book.getId());
//					if(1+1 ==2){
//						System.out.println("123344TRUE: ");
//					}
//					if(order.getBook().equals(book)){
//						a = orderService.calculateToTalOrder(order);
//						System.out.println("TRUE: ");
//					}
//					System.out.println("total: " + a);
//				}
//				System.out.println("FALSE: ");
//				totalOrderByBook.put(book, a);
//			}
//
//			totalOrderByBook.forEach((book, a) -> {
//				System.out.println("Book ID: " + book.getId() + " Customer: " + book.getCustomer().getFullName()+ ", Total Order: " + a);
//			});
//		};
//	}


>>>>>>> 2c31b00 (update commit)
}
