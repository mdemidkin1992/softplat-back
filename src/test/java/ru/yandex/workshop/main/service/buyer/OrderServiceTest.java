package ru.yandex.workshop.main.service.buyer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.workshop.main.model.buyer.Buyer;
import ru.yandex.workshop.main.model.buyer.Order;
import ru.yandex.workshop.main.repository.buyer.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BuyerService buyerService;

    private static Buyer buyer;
    private static final String email = "NameTwo@gmail.com";
    private static Order order;


    @BeforeEach
    void init() {
        buyer = Buyer.builder()
                .id(1L)
                .email("NameTwo@gmail.com")
                .name("user")
                .phone("1234567890")
                .build();

        order = new Order(1L, LocalDateTime.now(), buyer, new ArrayList<>(), 12.12F);
    }

    @Test
    @SneakyThrows
    void getOrderTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order response = orderService.getOrder(1L);

        assertEquals(order.getId(), response.getId());
        assertEquals(order.getProductionTime(), response.getProductionTime());
        assertEquals(order.getBuyer().getEmail(), response.getBuyer().getEmail());
    }

    @Test
    @SneakyThrows
    void getAllOrdersTest() {
        when(buyerService.getBuyerByEmail(email)).thenReturn(buyer);
        when(orderRepository.findAllByBuyer_Id(anyLong(), any())).thenReturn(List.of(order));

        List<Order> response = orderService.getAllOrders(email);

        assertEquals(order.getId(), response.get(0).getId());
        assertEquals(order.getProductionTime(), response.get(0).getProductionTime());
        assertEquals(order.getBuyer().getEmail(), response.get(0).getBuyer().getEmail());
    }
}

