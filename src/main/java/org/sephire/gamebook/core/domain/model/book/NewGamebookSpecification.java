package org.sephire.gamebook.core.domain.model.book;

import org.sephire.gamebook.core.domain.shared.specifications.NotNullSpecification;
import org.sephire.gamebook.core.domain.shared.specifications.Specification;

import static org.sephire.gamebook.core.domain.shared.specifications.Utils.fieldNotNull;

/**
 * The validation class for a gamebook given it's parameters
 */
public class NewGamebookSpecification implements Specification<Gamebook> {

    private GamebookRepository gamebookRepository;

    public NewGamebookSpecification(GamebookRepository gamebookRepository) {
        this.gamebookRepository = gamebookRepository;
    }

    @Override
    public boolean isSatisfiedBy(Gamebook gamebook) {

        Specification<Gamebook> titleIsUnique = (g) -> {
            return g.getTitle().getAllTitles()
                    .find(t -> !this.gamebookRepository.findGamebookByTitle(t._2()).isEmpty())
                    .isEmpty();
        };

        return new NotNullSpecification<Gamebook>()
                .and(titleIsUnique)
                .and(fieldNotNull(g -> g.getAuthor()))
                .isSatisfiedBy(gamebook);

    }

}
