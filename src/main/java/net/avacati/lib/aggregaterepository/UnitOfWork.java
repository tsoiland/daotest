package net.avacati.lib.aggregaterepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of objects affected by a business transaction
 * and coordinates the writing out of changes. --Martin Fowler
 *
 * @param <A> the entity type we're storing
 * @param <D> the corresponding dbo type
 */
public class UnitOfWork<A extends Repository.AggregateRoot<D>, D> {
    private DataStore<D> dataStore;
    private List<A> inserts;
    private List<A> update;

    public UnitOfWork(DataStore<D> dataStore) {
        this.dataStore = dataStore;
        this.inserts = new ArrayList<>();
        this.update = new ArrayList<>();
    }

    /**
     * Flag a new object as needing to be inserted.
     */
    public void insert(A entity) {
        this.inserts.add(entity);
    }

    /**
     * Flag an existing object as needing an update.
     */
    public void update(A entity) {
        this.update.add(entity);
    }

    /**
     * Perform inserts and updates using the underlying {@link DataStore}.
     */
    public void save() {
        // Perform inserts.
        this.inserts.forEach(entity -> this.dataStore.insert(entity.getId(), entity.getDbo()));
        this.inserts.clear();

        // Perform updates
        this.update.forEach(
                entity -> {
                    // Fetch old dbo. This class should keep a list of snapshots from when
                    // the original dbo was fetched from datasource, but this was quicker to implement.
                    D oldDbo = this.dataStore.get(entity.getId());

                    // Call update with the new and old entity so data store
                    // can compare individual fields if it needs to.
                    this.dataStore.update(entity.getId(), entity.getDbo(), oldDbo);
                });
        this.update.clear();
    }
}

