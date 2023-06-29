package kr.codesquad.kioskmax.controller;

import kr.codesquad.kioskmax.controller.dto.OrderSaveRequest;
import kr.codesquad.kioskmax.controller.dto.OrderResponse;
import kr.codesquad.kioskmax.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse checkOrder(@RequestBody final OrderSaveRequest orderSaveRequest) {
        return OrderResponse.from(orderService.save(orderSaveRequest.toOrderSaveInformation()));
    }
}
