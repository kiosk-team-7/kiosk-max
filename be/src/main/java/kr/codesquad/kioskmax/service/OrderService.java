package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.domain.Menus;
import kr.codesquad.kioskmax.domain.RandomGenerator;
import kr.codesquad.kioskmax.exception.PaymentTypeCashNotEnoughMoneyException;
import kr.codesquad.kioskmax.repository.MenuRepository;
import kr.codesquad.kioskmax.repository.OrderRepository;
import kr.codesquad.kioskmax.service.dto.OrderInformation;
import kr.codesquad.kioskmax.service.dto.OrderSaveInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDetailService orderDetailService;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final RandomGenerator randomGenerator;

    public OrderInformation save(OrderSaveInformation orderSaveInformation) {
        orderSaveInformation.processPayment(randomGenerator.getRandom());
        Menus menus = new Menus(menuRepository.findAll());
        long totalPrice = calculateTotalPrice(orderSaveInformation, menus);
        long orderId = orderRepository.save(orderSaveInformation.toOrder(totalPrice));
        orderDetailService.save(orderId, menus, orderSaveInformation.getOrderDetailSaveInformations());

        return OrderInformation.from(orderRepository.findById(orderId),
                orderDetailService.findAllByOrderId(orderId), menus);
    }

    private long calculateTotalPrice(OrderSaveInformation orderSaveInformation, Menus menus) {
        long totalPrice = menus.calculateTotalPrice(orderSaveInformation.toMapForMenuIdAndMenuCount());

        if (totalPrice > orderSaveInformation.getInputAmount()) {
            throw new PaymentTypeCashNotEnoughMoneyException();
        }
        return totalPrice;
    }
}
