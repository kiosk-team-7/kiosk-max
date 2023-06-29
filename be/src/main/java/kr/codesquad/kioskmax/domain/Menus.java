package kr.codesquad.kioskmax.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Menus {

    private final Map<Long, Menu> menus;

    public Menus(List<Menu> menus) {
        this.menus = menus.stream()
                .collect(Collectors.toMap(Menu::getId, Function.identity()));
    }

    public long calculateTotalPrice(Map<Long, Integer> mapForMenuIdAndMenuCount) {
        return mapForMenuIdAndMenuCount.entrySet()
                .stream()
                .mapToLong(m -> calculatePrice(m.getKey(), m.getValue()))
                .sum();
    }

    public long calculatePrice(Long id, int count) {
        return getPrice(id) * count;
    }

    private long getPrice(Long id) {
        if (Objects.isNull(menus.get(id))) {
            throw new IllegalArgumentException();
        }

        return menus.get(id)
                .getPrice();
    }

    public String getName(Long id) {
        if (Objects.isNull(menus.get(id))) {
            throw new IllegalArgumentException();
        }

        return menus.get(id)
                .getName();
    }
}
