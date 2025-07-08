package space.thinhtran.questionservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import space.thinhtran.questionservice.entity.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {
}
