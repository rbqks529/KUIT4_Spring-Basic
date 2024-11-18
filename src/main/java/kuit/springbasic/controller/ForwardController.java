package kuit.springbasic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForwardController {
    private static final String VIEW_PREFIX = "/";

    public String getViewPath(String... paths) {
        return VIEW_PREFIX + String.join("/", paths);
    }
}