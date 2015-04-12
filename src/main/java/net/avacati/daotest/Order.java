package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;
import net.avacati.lib.aggregaterepository.Aggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order implements Aggregate<OrderDbo> {
    protected UUID id;
    protected String orderData;
    protected OrderStatus status;
    protected List<OrderLog> log;
    protected String[] validationErrors;

    public Order(RegisterOrderDto dto, String[] errors) {
        this.log = new ArrayList<>();
        this.id = UUID.randomUUID();
        this.orderData = dto.orderData;

        if(errors.length > 0){
            this.updateStatus(OrderStatus.Error);
            this.validationErrors = errors;
        } else {
            this.updateStatus(OrderStatus.InProgress);
        }
    }

    public void cancel() {
        this.updateStatus(OrderStatus.Cancelled);
    }

    private void updateStatus(OrderStatus status) {
        this.status = status;
        this.addLog("Changed status to " + status.toString());
    }

    private void addLog(String message) {
        this.log.add(new OrderLog(new Date(), message));
    }

    public UUID getId() {
        return this.id;
    }

    public String getOrderData() {
        return orderData;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Iterable<OrderLog> getLog() {
        return this.log;
    }

    /*
     * Persistence methods
     */
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

    public Order(OrderDbo dbo) {
        this.id = dbo.id;
        this.orderData = dbo.orderData;
        this.status = dbo.status;
        this.log = dbo.log
                .stream()
                .map(OrderLog::new)
                .collect(Collectors.toList());
    }
}

