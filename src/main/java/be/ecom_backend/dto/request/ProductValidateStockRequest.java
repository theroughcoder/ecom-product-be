package be.ecom_backend.dto.request;

import be.ecom_backend.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class ProductValidateStockRequest {

    private List <OrderItem> orderItems;
}
