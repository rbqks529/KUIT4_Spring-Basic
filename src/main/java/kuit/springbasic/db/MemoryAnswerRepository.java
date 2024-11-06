package kuit.springbasic.db;


import kuit.springbasic.domain.Answer;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class MemoryAnswerRepository {

    private Map<String, Answer> answers= new HashMap<>();
    private  static int PK = 0;

    public MemoryAnswerRepository() {
        insert(new Answer(0,"박성현","저는 막내지만 팀장 맡는 것을 좋아한답니다. 팀장은 모두 잘생긴 제게 맡겨주세요^^~~!!"));
        insert(new Answer(1,"김윤서","?? 내가 더 잘하는데 ??"));
    }

    public int getPK(){
        return ++PK;
    }
    public List<Answer> findAllByQuestionId(int id) {
        ArrayList<Answer> result = new ArrayList<>();

        Set<String> answerKeys = answers.keySet();
        for (String key : answerKeys) {
            Answer repoAnswer = answers.get(key);
            if(repoAnswer.getQuestionId() == id){
                result.add(repoAnswer);
            }
        }

        return result;
    }


    public Answer insert(Answer answer) {
        answer.setAnswerId(getPK());
        answer.setCreatedDate(Date.valueOf(LocalDate.now()));
        answers.put(Integer.toString(answer.getAnswerId()),answer);
        return answer;
    }
}
