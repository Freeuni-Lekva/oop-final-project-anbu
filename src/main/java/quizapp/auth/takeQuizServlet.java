//package quizapp.auth;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import quizapp.models.dao.QuizDAO;
//import quizapp.models.questions.Quiz;
//
//import java.io.IOException;
//
//@WebServlet(name = "takeQuizServlet", value = "/home")
//public class takeQuizServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            // Get the index of the quiz from the form submission
//            int quizIndex = Integer.parseInt(request.getParameter("quizIndex"));
//
//            // In a real application, you would fetch the quiz details from the database using the quizIndex
//            // For this example, let's assume we have a QuizDao class to retrieve the quiz object
//            QuizDAO quizDao = new QuizDAO(); // Instantiate your QuizDao class
//            Quiz quiz = quizDao.get(quizIndex).orElse(null); // Implement this method to retrieve the quiz by index
//
//            if (quiz != null) {
//                // Save the quiz object in the session so it can be accessed in the question page
//                HttpSession session = request.getSession();
//                session.setAttribute("quiz", quiz);
//
//                // Redirect to the question page
//                response.sendRedirect("questionPage.jsp");
//            } else {
//                // If the quiz with the provided index is not found, redirect back to the homepage
//                response.sendRedirect("index.jsp");
//            }
//        } catch (NumberFormatException e) {
//            // If the user didn't enter a valid number, redirect back to the homepage
//            response.sendRedirect("homepage.jsp");
//        }
//    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/index.jsp").forward(req, resp);
//    }
//}
