package kuit.springbasic.db;


import kuit.springbasic.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryQuestionRepository {
    private Map<String, Question> questions = new HashMap<>();
    private static int PK = 0;

    public MemoryQuestionRepository() {
        insert(new Question("조하상", "저는 회식이 너무 좋아요", "회식을 하면 저를 꼭 불러주세요", 1));
        insert(new Question("김나은", "안드랑 서버 개잘하니까 질문받을게요", "질문 ㄱ", 1));
    }

    public static int getPK() {
        return ++PK;
    }

    public Question findByQuestionId(int id) {
        return questions.get(String.valueOf(id));
    }

    public void update(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.update(question);
    }

    public void insert(Question question) {
        question.setQuestionId(getPK());
        questions.put(Integer.toString(question.getQuestionId()), question);
    }

    public void updateCountOfAnswer(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.updateCountofAnswer(question);
    }

    public List<Question> findAll() {
        return questions.values().stream().toList();
    }
}
