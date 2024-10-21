package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.ProductOrderRequest;
import git.darul.tokonyadia.dto.response.ProductOrderResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.ProductOrder;
import git.darul.tokonyadia.repository.ProductOrderRepository;
import git.darul.tokonyadia.service.ProductOrderService;
import git.darul.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductOrderResponse> createAll(List<ProductOrderRequest> productOrderRequest, Order order) {
        List<ProductOrder> productOrders = new ArrayList<>();
        productOrderRequest.forEach(request -> {
            Product product = productService.getOne(request.getProductId());
            if (!product.getProductSizes().stream()
                    .anyMatch(productSize -> productSize.getSize().equals(request.getSize()))) {
                log.error("Product size not found on this product: {}", product.getName());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.SIZE_NOT_FOUND);
            }
            ProductOrder productOrder = ProductOrder.builder()
                    .order(order)
                    .product(product)
                    .quantity(request.getQuantity())
                    .size(request.getSize())
                    .price(product.getPrice())
                    .build();
            productOrders.add(productOrder);
            productService.addStock(product.getId(), product.getStock() - request.getQuantity());
        });
        List<ProductOrder> productOrderResult = productOrderRepository.saveAll(productOrders);
        return productOrderResult.stream()
                .map(this::getProductOrderResponse)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductOrderResponse> findAllByOrder(Order order) {
        List<ProductOrder> productOrder = productOrderRepository.findAllByOrder(order);
        if (productOrder.isEmpty()) {
            return new ArrayList<>();
        }
        return productOrder.stream().map(this::getProductOrderResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductOrderResponse findById(String id) {
        return getProductOrderResponse(getOne(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductOrder getOne(String id) {
        return productOrderRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.PRODUCT_ORDER_NOT_FOUND));
    }


    private ProductOrderResponse getProductOrderResponse(ProductOrder productOrder) {
        return ProductOrderResponse.builder()
                .id(productOrder.getId())
                .productName(productOrder.getProduct().getName())
                .quantity(productOrder.getQuantity())
                .size(productOrder.getSize())
                .price(productOrder.getProduct().getPrice())
                .totalPrice(productOrder.getProduct().getPrice() * productOrder.getQuantity())
                .build();
    }
}
