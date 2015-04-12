package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InnerRepo {
    private Map<UUID, OrderDbo> dboList;

    public InnerRepo() {
        this.dboList = new HashMap<>();
    }

    public void insert(OrderDbo dbo) {
        this.dboList.put(dbo.id, dbo);
    }

    public void update(OrderDbo dbo) {
        this.insert(dbo);
    }

    public OrderDbo get(UUID id) {
        return this.dboList.get(id);
    }
}
