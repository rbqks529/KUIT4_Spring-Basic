package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final UserRepository userRepository;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/loginForm")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }


    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    //@RequestMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest req) {
        log.info("loginV1");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = req.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //V2 버전은 오류가 발생함
    //@RequestMapping("/user/login")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {
        log.info("loginV2");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @RequestMapping("/login")
    public String loginV2(@ModelAttribute User loggedInUser,
                          HttpServletRequest request) {
        log.info("loginV4");
        User user = userRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/"; // 홈으로 리다이렉트
    }

}
