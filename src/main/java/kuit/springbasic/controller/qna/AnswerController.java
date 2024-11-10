package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */

    //@RequestMapping(value = "/api/qna/addAnswer", method = RequestMethod.POST)
    //@PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam("questionId") int questionId,
                            @RequestParam("writer") String writer,
                            @RequestParam("contents") String contents,
                            HttpServletResponse response) throws SQLException, IOException {
        log.info("AnswerController.addAnswerV0");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }

    //@PostMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              Model model) throws SQLException {
        log.info("AnswerController.addAnswerV1");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();

        model.addAttribute("answer", savedAnswer);
        return "jsonView";
    }

    @ResponseBody
    //@PostMapping("/api/qna/addAnswer")
    public Answer addAnswerV2(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents) throws SQLException {
        log.info("AnswerController.addAnswerV2");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();

        return savedAnswer;
    }

    @ResponseBody
    @PostMapping("/api/qna/addAnswer")
    public Answer addAnswerV3(@ModelAttribute Answer answer) throws SQLException {
        log.info("AnswerController.addAnswerV3");

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();

        return savedAnswer;
    }

}
