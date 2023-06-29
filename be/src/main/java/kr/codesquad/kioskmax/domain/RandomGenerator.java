package kr.codesquad.kioskmax.domain;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerator {

    public Double getRandom(){
        return Math.random();
    }
}
