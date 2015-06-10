package net.avacati.daotest;

import net.avacati.lib.aggregaterepository.UnitOfWork;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderService {
    private OrderRepository orderRepository;
    private UnitOfWork unitOfWork;

    public OrderService(OrderRepository orderRepository, UnitOfWork unitOfWork) {
        this.orderRepository = orderRepository;
        this.unitOfWork = unitOfWork;
    }

    public UUID registerOrder(RegisterOrderDto dto) {
        // Create order
        Order order = new Order(dto);

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

    // ========== Dto classes below ============ //
    public static class RegisterOrderDto {
        public String orderData;

        public RegisterOrderDto(String orderData) {
            this.orderData = orderData;
        }
    }

    public static class ViewOrderDto {
        String orderData;
        Order.OrderStatus status;
        Collection<String> log;

        public ViewOrderDto(Order order) {
            this.orderData = order.getOrderData();
            this.status = order.getStatus();
            this.log = order.getLog();
        }
    }
}

