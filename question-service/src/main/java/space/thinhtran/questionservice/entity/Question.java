package space.thinhtran.questionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.List;

@Document(collection = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    private String id;
    private String text;
    private List<String> choices;
    private List<Integer> correctAnswers;
    private String explanation;
    private String difficulty;
    private List<String> tags;
    private List<String> topicId;
    private String createdBy;
}
