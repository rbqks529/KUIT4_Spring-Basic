package kuit.springbasic.util;


import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;

public class UserSessionUtils {
    public static final String USER_SESSION_KEY = "user";

    public static User getUserFromSession(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUserFromSession(session) != null;
    }
}
