package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.controller.ForwardController;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final ForwardController forwardController;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return forwardController.getViewPath("user", "form");
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("createUserV1");

        User registerUser = new User(userId, password, name, email);
        userRepository.insert(registerUser);
        return "redirect:/";
    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("createUserV2");
        userRepository.insert(user);

        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(Model model, HttpServletRequest request) {
        log.info("listUsers");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);

            return forwardController.getViewPath("user", "list");
        }

        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, HttpServletRequest request) {
        log.info("showUserUpdateForm");
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = session.getAttribute(USER_SESSION_KEY);

        if (user != null && user.isSameUser((User) value)) {
            return forwardController.getViewPath("user", "updateForm");
        }

        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@RequestMapping("/update")
    public String updateUserV2(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("updateUserV1");
        User user = new User(userId, password, name, email);
        userRepository.update(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User updatedUser) {
        log.info("updateUserV2");

        User user = new User(updatedUser.getUserId(), updatedUser.getPassword(), updatedUser.getName(), updatedUser.getEmail());
        userRepository.update(user);

        return "redirect:/user/list";
    }
}
