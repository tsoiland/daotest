package net.avacati.daotest;

import java.util.Date;

public class OrderLogDto {
    Date date;
    String message;

    public OrderLogDto(OrderLog l) {
        this.date = l.date;
        this.message = l.message;
    }
}
