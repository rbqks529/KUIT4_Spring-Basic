package kuit.springbasic.controller.qna;

import kuit.springbasic.controller.ForwardController;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ForwardController forwardController;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/show")
    public String showQnA(@RequestParam("questionId") String showQuestion, Model model) {
        log.info("showQnA");
        Question question = questionRepository.findByQuestionId(Integer.parseInt(showQuestion));
        Collection<Answer> answers = answerRepository.findAllByQuestionId(question.getQuestionId());

        model.addAttribute("question", question)
                .addAttribute("answers", answers);

        return forwardController.getViewPath("qna", "show");
    }
}
