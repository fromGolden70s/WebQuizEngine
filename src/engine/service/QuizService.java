package engine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.exception.QuizNotFoundException;
import engine.exception.UserNotAuthorException;
import engine.model.Completions;
import engine.model.Quiz;
import engine.model.UserAnswer;
import engine.repo.CompletionsRepository;
import engine.repo.QuizRepository;
import engine.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;


import static java.lang.String.valueOf;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private CompletionsRepository completionsRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository,
                       UserRepository userRepository,
                       CompletionsRepository completionsRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completionsRepository = completionsRepository;
    }



    ObjectMapper objectMapper = new ObjectMapper();

    List<Quiz> quizList = new ArrayList<>();

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        try {
            return quizRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Reply checkAnswer(Long id, UserAnswer answer, String email) {
        Reply reply = new Reply();

        if (quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            if (valueOf(quizRepository.findById(id).get().getAnswer()).equals(valueOf(answer.getAnswer()))) {
                LocalDateTime now = LocalDateTime.now();
                completionsRepository.save(new Completions(email, id, now));
                return new Reply(true, reply.CORRECT);
            } else {
                return new Reply(false, reply.WRONG);
            }
        }
    }

    public Page<Quiz> getAllQuizzes(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        /*List<Quiz> allQuizes = new ArrayList<>();
        quizRepository.findAll().forEach(allQuizes::add);
        String text = "[\n ";
        for (Quiz quiz : allQuizes) {
            try {
                text += objectMapper.writeValueAsString(quiz);
            } catch (Exception e) {
                text += "";
            }
        }
        text = text.replace("}{", "},\n {");
        text += "\n]";*/

        return quizRepository.findAll(pageable);
    }

    public void delete(Long id, String username) {
        if (quizRepository.findById(id).isEmpty()) {
            throw new QuizNotFoundException();
        } else if (!quizRepository.findById(id).get().getUser().getEmail().equals(username)) {
            throw new UserNotAuthorException();
        } else {
            quizRepository.deleteById(id);
        }
    }

    public Page<Completions> getCompleted(int page, String userEmail) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        //return completionsRepository.findAll(pageable);
        return completionsRepository.findAllByUserEmail(userEmail, pageable);
    }
}
