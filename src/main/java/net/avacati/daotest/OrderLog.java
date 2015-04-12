package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderLogDbo;

import java.util.Date;
import java.util.UUID;

public class OrderLog {
    public final UUID id;
    public final Date date;
    public final String message;

    public OrderLog(Date date, String message) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.message = message;
    }

    public OrderLog(OrderLogDbo dbo) {
        this.id = dbo.id;
        this.date = new Date(dbo.date);
        this.message = dbo.message;
    }

    public OrderLogDbo getDbo(UUID orderId) {
        OrderLogDbo dbo = new OrderLogDbo();
        dbo.id = this.id;
        dbo.orderId = orderId;
        dbo.date = this.date.toGMTString();
        dbo.message = this.message;
        return dbo;
    }
}
