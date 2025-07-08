package space.thinhtran.questionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import space.thinhtran.questionservice.entity.Question;
import space.thinhtran.questionservice.service.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "Question Controller", description = "APIs for managing questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "Get all questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

}
