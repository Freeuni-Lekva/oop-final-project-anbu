package quizapp.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.settings.Endpoints;

import java.io.IOException;

/* Authwall, every request made to an endpoint matching /secured/* pattern will be filtered */
@WebFilter("/secured/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);
        if (!req.getRequestURI().endsWith("login")) {
            boolean isAuthenticated = (boolean) session.getAttribute("AUTHENTICATED");

            if (!isAuthenticated) {
                resp.sendRedirect(Endpoints.LOGIN);
                return;
            }
        }

        chain.doFilter(req, resp);
    }
}
