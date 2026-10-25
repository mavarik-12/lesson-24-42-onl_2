package by.tms.lesson2442_3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ageCheck")
public class AgeCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // КРИТИЧЕСКИ ВАЖНО: явно задаём кодировку ответа
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Для POST обязательно: читаем параметры в UTF-8
        request.setCharacterEncoding("UTF-8");

        String ageParam = request.getParameter("age");
        PrintWriter out = response.getWriter();

        if (ageParam == null || ageParam.trim().isEmpty()) {
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Ошибка</title></head><body>");
            out.println("<h3>Ошибка: возраст не указан</h3>");
            out.println("</body></html>");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageParam);
        } catch (NumberFormatException e) {
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Ошибка</title></head><body>");
            out.println("<h3>Ошибка: введите корректное число</h3>");
            out.println("</body></html>");
            return;
        }

        String result = (age >= 18) ? "Человек совершеннолетний." : "Человек несовершеннолетний.";

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Результат проверки</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Результат проверки возраста</h2>");
        out.println("<p>Возраст: " + age + "</p>");
        out.println("<p>" + result + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}