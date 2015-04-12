package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;
import net.avacati.lib.aggregaterepository.InMemoryDataStore;
import net.avacati.lib.aggregaterepository.UnitOfWork;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class OrderServiceTest {
    @Test
    public void atest(){
        OrderService sut = createSut();
        UUID newOrderId = sut.registerOrder(new RegisterOrderDto("foobar"));

        ViewOrderDto result = sut.getOrder(newOrderId);
        Assert.assertEquals("foobar", result.orderData);
    }

    private OrderService createSut() {
        InMemoryDataStore<OrderDbo> innerRepo = new InMemoryDataStore<>();
        UnitOfWork<Order, OrderDbo> unitOfWork = new UnitOfWork<>(innerRepo);
        return new OrderService(new OrderRepository(innerRepo, unitOfWork), new OrderValidator(), unitOfWork);
    }

    @Test
    public void btest(){
        OrderService sut = createSut();
        UUID newOrderId = sut.registerOrder(new RegisterOrderDto("foobar"));

        ViewOrderDto result = sut.getOrder(newOrderId);
        Assert.assertEquals(OrderStatus.InProgress, result.status);

        sut.cancelOrder(newOrderId);

        ViewOrderDto result2 = sut.getOrder(newOrderId);
        Assert.assertEquals(OrderStatus.Cancelled, result2.status);
    }

    @Test
    public void ctest(){
        OrderService sut = createSut();
        UUID newOrderId = sut.registerOrder(new RegisterOrderDto("foobar"));

        ViewOrderDto result = sut.getOrder(newOrderId);
        Assert.assertEquals(1, result.log.stream().count());

        sut.cancelOrder(newOrderId);

        ViewOrderDto result2 = sut.getOrder(newOrderId);
        Assert.assertEquals(2, result2.log.stream().count());
    }
}
