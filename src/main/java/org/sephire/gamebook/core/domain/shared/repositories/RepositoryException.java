package org.sephire.gamebook.core.domain.shared.repositories;

/**
 * This is the exception to be thrown when a repository fails for whathever reason so that
 * the domain can react to it in an technology-agnostic way.
 */
public class RepositoryException extends RuntimeException {
}
