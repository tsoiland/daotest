package net.avacati.daotest;

import java.util.Date;
import java.util.UUID;

class OrderLog {
    private final UUID id;
    private final Date date;
    private final String message;

    OrderLog(String message) {
        this.id = UUID.randomUUID();
        this.date = new Date();
        this.message = message;
    }

    OrderLog(OrderLogDbo dbo) {
        this.id = dbo.id;
        this.date = new Date(dbo.date);
        this.message = dbo.message;
    }

    String render() {
        return this.date.toGMTString() + " " + this.message;
    }

    OrderLogDbo getDbo(UUID orderId) {
        OrderLogDbo dbo = new OrderLogDbo();
        dbo.id = this.id;
        dbo.orderId = orderId;
        dbo.date = this.date.toGMTString();
        dbo.message = this.message;
        return dbo;
    }

    static class OrderLogDbo {
        public UUID id;
        public UUID orderId;
        public String date;
        public String message;
    }
}
