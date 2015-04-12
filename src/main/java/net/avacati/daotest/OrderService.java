package net.avacati.daotest;

import java.util.UUID;

public class OrderService {
    private OrderRepository orderRepository;
    private OrderValidator orderValidator;
    private UnitOfWork unitOfWork;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, UnitOfWork unitOfWork) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.unitOfWork = unitOfWork;
    }

    public UUID registerOrder(RegisterOrderDto dto) {
        // Validate
        String[] errors = this.orderValidator.validate(dto);

        // Create order
        Order order = new Order(dto, errors);

        // Save
        this.orderRepository.add(order);
        this.unitOfWork.save();
        return order.getId();
    }

    public ViewOrderDto getOrder(UUID orderId) {
        Order order = this.orderRepository.get(orderId);
        return new ViewOrderDto(order);
    }

    public void cancelOrder(UUID orderId) {
        Order order = this.orderRepository.get(orderId);
        order.cancel();
        this.unitOfWork.save();
    }
}

