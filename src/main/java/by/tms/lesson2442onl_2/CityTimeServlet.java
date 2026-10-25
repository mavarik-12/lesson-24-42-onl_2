package by.tms.lesson2442onl_2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//@WebServlet({
//        "/minsk",
//        "/washington",
//        "/beijing"
//})
public class CityTimeServlet extends HttpServlet {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        String contextPath = request.getContextPath();

        String citySegment = requestURI.substring(contextPath.length());

        if (citySegment.startsWith("/")) {
            citySegment = citySegment.substring(1);
        }

        String timezoneId;
        switch (citySegment) {
            case "minsk":
                timezoneId = "Europe/Minsk";
                break;
            case "washington":

                timezoneId = "America/New_York";
                break;
            case "beijing":

                timezoneId = "Asia/Shanghai";
                break;
            default:

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write(
                        "Город не найден. Доступные пути: /minsk, /washington, /beijing"
                );
                return;
        }
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(timezoneId));
        String timeString = zdt.format(formatter);

        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(citySegment + ": " + timeString);
    }
}