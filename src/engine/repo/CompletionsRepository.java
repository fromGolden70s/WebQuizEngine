package engine.repo;

import engine.model.Completions;
import engine.model.Quiz;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CompletionsRepository extends PagingAndSortingRepository<Completions, String>,
        CrudRepository<Completions, String> {
    Page<Completions> findAllByUserEmail(String userEmail, Pageable pageable);
}
