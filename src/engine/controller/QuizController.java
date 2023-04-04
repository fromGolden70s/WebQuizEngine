package engine.controller;



import engine.model.Completions;
import engine.repo.UserRepository;
import engine.service.QuizService;
import engine.service.Reply;
import engine.model.Quiz;
import engine.model.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Validated
public class QuizController {

    private final QuizService quizService;
    private final UserRepository userRepository;

    @Autowired
    public QuizController(QuizService quizService, UserRepository userRepository) {
        this.quizService = quizService;
        this.userRepository = userRepository;
    }



    @GetMapping (path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping (path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam int page) {

        return quizService.getAllQuizzes(page);
    }

    @PostMapping (path = "/api/quizzes/{id}/solve")
    public Reply answerQuiz(@PathVariable Long id, @RequestBody UserAnswer answer,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return quizService.checkAnswer(id, answer, userDetails.getUsername());
    }

    @PostMapping (path = "/api/quizzes")
    public Quiz createQuiz(@Valid @RequestBody Quiz newQuiz,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        newQuiz.setUser(userRepository.findById(userDetails.getUsername()).get());
        return quizService.saveQuiz(newQuiz);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication auth) {
        quizService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<Completions> getCompleted(@RequestParam int page,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        return quizService.getCompleted(page, userDetails.getUsername());
    }


}
