package am.trainings.palmetto.common;

public abstract class AbstractRepositoryFactory {

    public abstract AbstractRepositoryFactory getRepositoryByType(RepositoryTypes type);
}
