package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.controller.ForwardController;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final ForwardController forwardController;
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/form")
    public String ShowQuestionForm(HttpServletRequest request) {
        log.info("showUserForm");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {          // 회원만 질문 등록 가능
            return forwardController.getViewPath("qna", "form");
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    //@RequestMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        log.info("createQuestionV1");

        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    @RequestMapping("/create")
    public String createQuestionV1(@ModelAttribute Question createdQuestion) {
        log.info("createQuestionV2");

        Question question = new Question(createdQuestion.getWriter(), createdQuestion.getTitle(), createdQuestion.getContents(), 0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    //@RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") String updateQuestion, HttpServletRequest request, Model model) {
        log.info("showUpdateQuestionFormV1");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(updateQuestion));
        model.addAttribute("question", question);

        return forwardController.getViewPath("qna", "updateForm");
    }

    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(
            @RequestParam("questionId") String questionId,
            @SessionAttribute(name = USER_SESSION_KEY, required = false) User loggedInUser,
            Model model) {

        log.info("showUpdateQuestionFormV2");

        // 로그인 체크
        if (loggedInUser == null) {
            return "redirect:/user/loginForm";
        }

        // 질문 조회
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);

        return forwardController.getViewPath("qna", "updateForm");
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/update")
    public String updateQuestion(@ModelAttribute Question updatedQuestion, HttpServletRequest request) {
        log.info("updateQuestion");
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(updatedQuestion.getQuestionId());
        question.updateTitleAndContents(updatedQuestion.getTitle(), updatedQuestion.getContents());
        questionRepository.update(question);

        return "redirect:/";
    }

}
