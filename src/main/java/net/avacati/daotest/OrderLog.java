package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderLogDbo;

import java.util.Date;

public class OrderLog {
    public final Date date;
    public final String message;

    public OrderLog(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public OrderLog(OrderLogDbo dbo) {
        this.date = dbo.date;
        this.message = dbo.message;
    }

    public OrderLogDbo getDbo() {
        OrderLogDbo dbo = new OrderLogDbo();
        dbo.date = date;
        dbo.message = message;
        return dbo;
    }
}
