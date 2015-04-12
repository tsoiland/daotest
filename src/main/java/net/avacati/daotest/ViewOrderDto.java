package net.avacati.daotest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ViewOrderDto {
    String orderData;
    OrderStatus status;
    List<OrderLogDto> log;

    public ViewOrderDto(Order order) {
        this.orderData = order.getOrderData();
        this.status = order.getStatus();
        this.log = StreamSupport.stream(order.getLog().spliterator(), false)
                .map(l -> new OrderLogDto(l))
                .collect(Collectors.toList());
    }
}
