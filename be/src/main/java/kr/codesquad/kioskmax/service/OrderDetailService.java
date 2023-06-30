package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.domain.Menus;

import kr.codesquad.kioskmax.domain.OrderDetail;
import kr.codesquad.kioskmax.repository.MenuRankRepository;
import kr.codesquad.kioskmax.repository.OrderDetailRepository;
import kr.codesquad.kioskmax.service.dto.OrderDetailSaveInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final MenuRankRepository menuRankRepository;

    public void save(Long orderId, Menus menus,
                     List<OrderDetailSaveInformation> orderDetailSaveInformations) {
        orderDetailSaveInformations.forEach(odsi -> {
            orderDetailRepository.save(odsi.toOrderDetail(orderId, menus));
            odsi.toMenuRanks()
                    .forEach(menuRankRepository::save);
        });
    }

    public List<OrderDetail> findAllByOrderId(Long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }
}
