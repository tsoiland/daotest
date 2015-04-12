package net.avacati.daotest;

import net.avacati.daotest.Persistence.OrderDbo;

import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {
    private InnerRepo innerRepo;
    private List<Order> insertsDirty;
    private List<Order> maybeDirty;

    public UnitOfWork(InnerRepo innerRepo) {
        this.innerRepo = innerRepo;
        this.insertsDirty = new ArrayList<>();
        this.maybeDirty = new ArrayList<>();
    }

    public void insert(Order dbo) {
        this.insertsDirty.add(dbo);
    }

    public void maybeUpdate(Order order) {
        this.maybeDirty.add(order);
    }

    public void save() {
        this.insertsDirty.forEach(order -> this.innerRepo.insert(order.getDbo()));
        this.maybeDirty.forEach(order -> this.processModified(order.getDbo()));

        this.insertsDirty.clear();
        this.maybeDirty.clear();
    }

    private void processModified(OrderDbo newDbo) {
        OrderDbo existingDbo = this.innerRepo.get(newDbo.id);
        if(!newDbo.equals(existingDbo)) {
            this.innerRepo.update(newDbo);
        }
    }
}
