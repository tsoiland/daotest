package net.avacati.daotest;

import net.avacati.lib.aggregaterepository.Repository;

import java.util.*;
import java.util.stream.Collectors;

class Order implements Repository.AggregateRoot<Order.OrderDbo> {
    private UUID id;
    private String orderData;
    private OrderStatus status;
    public enum OrderStatus { InProgress, Error, Cancelled }
    private List<OrderLog> log = new ArrayList<>();
    private Collection<String> validationErrors = new ArrayList<>();

    Order(OrderService.RegisterOrderDto dto) {
        // Assign identity
        this.id = UUID.randomUUID();

        // Set values from dto.
        this.orderData = dto.orderData;

        // Validate
        if(this.orderData.contains("13")){
            // Business rule: Superstitious product owner
            this.validationErrors.add("Order data contains unlucky number!");
        }

        if(this.id.toString().contains(Integer.toString(6 * 100) + (6 * 10) + 6)) {
            /*
             * Business rule: Product owner misread his bible. The actual quote goes the other way around:
             *
             * Revelations 13:18
             *  17 and he provides that no one will be able to buy or to sell,
             * except the one who has the mark, either the name of the beast or
             * the number of his name.
             *  18 Here is wisdom. Let him who has understanding
             * calculate the number of the beast, for the number is that of a man;
             * and his number is six hundred and sixty-six.
             */
            this.validationErrors.add("Order has mark of the beast!");
        }

        // Initialize status based on validation
        if(this.validationErrors.size() > 0){
            this.updateStatus(OrderStatus.Error);
        } else {
            this.updateStatus(OrderStatus.InProgress);
        }
    }

    void cancel() {
        this.updateStatus(OrderStatus.Cancelled);
    }

    private void updateStatus(OrderStatus status) {
        this.status = status;
        this.addLog("Changed status to " + status.toString());
    }

    private void addLog(String message) {
        this.log.add(new OrderLog(message));
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    String getOrderData() {
        return orderData;
    }

    OrderStatus getStatus() {
        return status;
    }

    Collection<String> getLog() {
        return
            this.log
                .stream()
                .map(OrderLog::render)
                .collect(Collectors.toList());
    }

    /*
     * Persistence methods
     */
    @Override
    public OrderDbo getDbo() {
        OrderDbo dbo = new OrderDbo();
        dbo.id = this.id;
        dbo.orderData = this.orderData;
        dbo.status = this.status;
        dbo.log = this.log
                .stream()
                .map(l -> l.getDbo(this.id))
                .collect(Collectors.toList());
        return dbo;
    }

    Order(OrderDbo dbo) {
        this.id = dbo.id;
        this.orderData = dbo.orderData;
        this.status = dbo.status;
        this.log = dbo.log
                .stream()
                .map(OrderLog::new)
                .collect(Collectors.toList());
    }

    static class OrderDbo {
        public UUID id;
        public String orderData;
        public OrderStatus status;
        public List<OrderLog.OrderLogDbo> log;
    }
}

