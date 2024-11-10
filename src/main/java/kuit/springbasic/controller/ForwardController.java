package kuit.springbasic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForwardController {

    private static final String VIEW_PREFIX = "/";
    private static final String REDIRECT_PREFIX = "redirect:/";

    public String getViewPath(String... paths) {
        return VIEW_PREFIX + String.join("/", paths);
    }

    public String getRedirectPath(String... paths) {
        return REDIRECT_PREFIX + String.join("/", paths);
    }
}