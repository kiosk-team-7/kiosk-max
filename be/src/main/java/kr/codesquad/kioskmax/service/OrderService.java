package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.domain.Menus;
import kr.codesquad.kioskmax.domain.RandomGenerator;
import kr.codesquad.kioskmax.exception.PaymentTypeCashNotEnoughMoneyException;
import kr.codesquad.kioskmax.repository.MenuRepository;
import kr.codesquad.kioskmax.repository.OrderDetailRepository;
import kr.codesquad.kioskmax.repository.OrderRepository;
import kr.codesquad.kioskmax.service.dto.OrderInformation;
import kr.codesquad.kioskmax.service.dto.OrderSaveInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;
    private final RandomGenerator randomGenerator;

    public OrderInformation save(OrderSaveInformation orderSaveInformation) {
        orderSaveInformation.getPaymentType().processPayment(randomGenerator.getRandom());
        Menus menus = new Menus(menuRepository.findAll());
        long totalPrice = menus.calculateTotalPrice(orderSaveInformation.toMapForMenuIdAndMenuCount());
        validateTotalPriceBiggerThanInputAmount(orderSaveInformation.getInputAmount(), totalPrice);
        long orderId = orderRepository.save(orderSaveInformation.toOrder(totalPrice));
        saveOrderDetails(orderSaveInformation, orderId, menus);

        return OrderInformation.from(orderRepository.findById(orderId),
                orderDetailRepository.findAllByOrderId(orderId), menus);
    }

    private void saveOrderDetails(OrderSaveInformation orderSaveInformation,
                                  long orderId, Menus menus) {
        orderSaveInformation.getOrderDetailSaveInformations()
                .forEach(odsi -> orderDetailRepository.save(odsi.toOrderDetail(orderId, menus)));
    }

    private void validateTotalPriceBiggerThanInputAmount(long inputAmount, long totalPrice) {
        if (totalPrice > inputAmount) {
            throw new PaymentTypeCashNotEnoughMoneyException();
        }
    }
}
