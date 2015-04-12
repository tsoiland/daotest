package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;

import java.util.*;

public class OrderRepository {
    private InnerRepo innerRepo;
    private UnitOfWork unitOfWork;

    public OrderRepository(InnerRepo innerRepo, UnitOfWork unitOfWork) {
        this.innerRepo = innerRepo;
        this.unitOfWork = unitOfWork;
    }

    public void add(Order order) {
        this.unitOfWork.insert(order);
    }

    public Order get(UUID id) {
        OrderDbo dbo = this.innerRepo.get(id);
        Order order = new Order(dbo);
        this.unitOfWork.maybeUpdate(order);
        return order;
    }
}

