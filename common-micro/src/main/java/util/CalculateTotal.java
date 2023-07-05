package util;

import models.ProductRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Maximiliano
 */
@Component
public class CalculateTotal {

    public Double calcTotal(List<ProductRequest> productRequestList) {
        AtomicReference<Double> totalPrice = new AtomicReference<>(0D);
        productRequestList.forEach(p -> totalPrice.updateAndGet(v -> v + p.getPrice() * p.getAmount()));
        return totalPrice.get();
    }
}
