package net.avacati.daotest;

import net.avacati.lib.aggregaterepository.UnitOfWork;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class OrderServiceTest {
    private OrderService createSut() {
        InMemoryDataStore innerRepo = new InMemoryDataStore<>();
        UnitOfWork unitOfWork = new UnitOfWork<>(innerRepo);
        return new OrderService(new OrderRepository(innerRepo, unitOfWork), unitOfWork);
    }

    @Test
    public void registerOrder(){
        // Arrange
        OrderService sut = createSut();

        // Act
        UUID newOrderId = sut.registerOrder(new OrderService.RegisterOrderDto("foobar"));

        // Assert
        OrderService.ViewOrderDto result = sut.getOrder(newOrderId);
        assertEquals("foobar", result.orderData);
        assertEquals(Order.OrderStatus.InProgress, result.status);
        assertEquals(1, result.log.stream().count());
    }

    @Test
    public void cancelOrder(){
        // Arrange
        OrderService sut = createSut();

        // Act
        UUID newOrderId = sut.registerOrder(new OrderService.RegisterOrderDto("foobar"));
        sut.cancelOrder(newOrderId);

        // Assert
        OrderService.ViewOrderDto result = sut.getOrder(newOrderId);
        assertEquals(Order.OrderStatus.Cancelled, result.status);
        assertEquals(2, result.log.stream().count());
    }
}
