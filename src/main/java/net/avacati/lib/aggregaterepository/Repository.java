package net.avacati.lib.aggregaterepository;

import java.util.UUID;

/**
 * Generic repository for entities that
 *  - uses dbo pattern,
 *  - can implement AggregateRoot interface,
 *  - and do not need queries other than getById.
 *
 * @param <A> the entity type we're storing
 * @param <Dbo> the corresponding dbo type
 */
public class Repository<A extends Repository.AggregateRoot<Dbo>, Dbo>{
    private DataStore<Dbo> innerRepo;
    private UnitOfWork<A, Dbo> unitOfWork;
    private AggregateRoot.AggregateRootFactory<A, Dbo> factory;

    public Repository(DataStore<Dbo> innerRepo, UnitOfWork<A, Dbo> unitOfWork, AggregateRoot.AggregateRootFactory<A, Dbo> factory) {
        this.innerRepo = innerRepo;
        this.unitOfWork = unitOfWork;
        this.factory = factory;
    }

    public void add(A order) {
        this.unitOfWork.insert(order);
    }

    public A get(UUID id) {
        // Fetch dbo
        Dbo dbo = this.innerRepo.get(id);

        // Construct domain entity
        A aggregate = this.factory.createFromDbo(dbo);

        // Register the object for update right away to relieve the services of the responsibility.
        // We can start comparing  new and old in the unit of work if it starts to be a performance problem.
        this.unitOfWork.update(aggregate);

        return aggregate;
    }

    /**
     * Represents an entity class that the unit of work can handle.
     *
     * An aggregate root is the root and public interface in a small
     * cluster of objects that can perform a usecase on its own data.
     *
     * @param <Dbo> the dbo class of the aggregate root entity
     */
    public interface AggregateRoot<Dbo> {
        UUID getId();
        Dbo getDbo();

        /**
         * This is just a way for the client to specify a lambda that takes
         * a dbo and returns an aggregate root object. This will probably
         * always be a method reference to a constructor, and we could have
         * just used reflection, but for now we're staying pure.
         *
         * @param <A> the type of the Aggregate root that will be created.
         * @param <Dbo> the type of the corresponding dbo that will be given to the lambda.
         */
        interface AggregateRootFactory<A extends AggregateRoot<Dbo>, Dbo> {
            A createFromDbo(Dbo dbo);
        }
    }
}

