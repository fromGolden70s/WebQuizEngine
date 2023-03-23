package engine.repo;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Quiz findById(long id);


}
