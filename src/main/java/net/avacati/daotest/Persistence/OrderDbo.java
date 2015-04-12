package net.avacati.daotest.Persistence;

import net.avacati.daotest.OrderStatus;

import java.util.List;
import java.util.UUID;

public class OrderDbo {
    public UUID id;
    public String orderData;
    public OrderStatus status;
    public List<OrderLogDbo> log;
}
