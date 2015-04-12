package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;
import net.avacati.lib.aggregaterepository.DataStore;
import net.avacati.lib.aggregaterepository.Repository;
import net.avacati.lib.aggregaterepository.UnitOfWork;

public class OrderRepository extends Repository<Order, OrderDbo> {
    public OrderRepository(DataStore<OrderDbo> innerRepo, UnitOfWork<Order, OrderDbo> unitOfWork) {
        super(innerRepo, unitOfWork, orderDbo -> new Order(orderDbo));
    }
}
