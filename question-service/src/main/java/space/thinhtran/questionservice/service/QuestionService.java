package space.thinhtran.questionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.thinhtran.questionservice.entity.Question;
import space.thinhtran.questionservice.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
