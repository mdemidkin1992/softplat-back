package ru.softplat.web.controller.basket;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.softplat.dto.basket.OrderCreateDto;
import ru.softplat.dto.basket.OrderResponseDto;
import ru.softplat.dto.basket.OrdersListResponseDto;
import ru.softplat.exception.EntityNotFoundException;
import ru.softplat.exception.WrongConditionException;
import ru.softplat.mapper.OrderMapper;
import ru.softplat.message.LogMessage;
import ru.softplat.model.buyer.Order;
import ru.softplat.service.buyer.OrderService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class BuyerOrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(summary = "Оформление заказа", description = "Доступ для покупателя")
    @PreAuthorize("hasAuthority('buyer:write')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public OrderResponseDto addOrder(@ApiIgnore Principal principal, @RequestBody OrderCreateDto orderCreateDto) {
        log.debug(LogMessage.TRY_ADD_ORDER.label);
        if (orderCreateDto.getBasketPositionIds() == null || orderCreateDto.getBasketPositionIds().size() == 0)
            throw new EntityNotFoundException("Перед оформлением заказа добавьте товары в корзину");
        Order response = orderService.createOrder(principal.getName(), orderCreateDto.getBasketPositionIds());
        return orderMapper.orderToOrderDto(response);
    }

    @Operation(summary = "Список: Мои покупки", description = "Доступ для покупателя")
    @PreAuthorize("hasAuthority('buyer:write')")
    @GetMapping
    public OrdersListResponseDto getAllOrders(@ApiIgnore Principal principal) {
        log.debug(LogMessage.TRY_GET_ALL_ORDERS.label);
        List<OrderResponseDto> response = orderService.getAllOrders(principal.getName()).stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
        return orderMapper.toOrdersListResponseDto(response);
    }

    @Operation(summary = "Получение конкретной покупки", description = "Доступ для покупателя")
    @PreAuthorize("hasAuthority('buyer:write')")
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@ApiIgnore Principal principal, @PathVariable long orderId) {
        log.debug(LogMessage.TRY_GET_ORDER.label, orderId);
        Order response = orderService.getOrder(orderId);
        if (!response.getBuyer().getEmail().equals(principal.getName()))
            throw new WrongConditionException("Ошибка при выполнении запроса. Попробуйте ввести корректный orderId");
        return orderMapper.orderToOrderDto(response);
    }
}
