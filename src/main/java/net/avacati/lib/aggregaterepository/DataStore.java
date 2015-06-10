package net.avacati.lib.aggregaterepository;

import java.util.UUID;

/**
 * These are the operations that {@link UnitOfWork} needs
 * in order to handle persistence of objects.
 *
 * Expect to be implemented in memory and for various peristence
 * technologies.
 *
 * @param <D> the type of the dbo to be persisted.
 */
public interface DataStore<D> {
    void insert(UUID id, D r);
    void update(UUID id, D newDbo, D oldDbo);
    D get(UUID id);
}
