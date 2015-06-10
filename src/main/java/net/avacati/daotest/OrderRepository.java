package net.avacati.daotest;

import net.avacati.lib.aggregaterepository.DataStore;
import net.avacati.lib.aggregaterepository.Repository;
import net.avacati.lib.aggregaterepository.UnitOfWork;

public class OrderRepository extends Repository<Order, Order.OrderDbo> {
    public OrderRepository(DataStore<Order.OrderDbo> innerRepo, UnitOfWork<Order, Order.OrderDbo> unitOfWork) {
        super(
            innerRepo, // pass through
            unitOfWork, // pass through
            Order::new); // Define AggregateFactory (how to make an entity out of a dbo for this aggregate root)
    }
}
