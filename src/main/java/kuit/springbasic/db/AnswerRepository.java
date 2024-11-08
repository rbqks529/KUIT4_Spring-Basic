package kuit.springbasic.db;

import kuit.springbasic.domain.Answer;

import java.util.Collection;

public interface AnswerRepository {
    Answer insert(Answer answer);

    Collection<Answer> findAllByQuestionId(int questionId);

}
