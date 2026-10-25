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

    // Форматировщик: дата, время и название зоны
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Получаем полный URI запроса, например: /city-time/minsk
        String requestURI = request.getRequestURI();

        // Контекст приложения — имя WAR без слэша, например: /city-time
        String contextPath = request.getContextPath();

        // Вырезаем контекст, остаётся сегмент: minsk / washington / beijing
        String citySegment = requestURI.substring(contextPath.length());

        // Если есть ведущий слэш — убираем его
        if (citySegment.startsWith("/")) {
            citySegment = citySegment.substring(1);
        }

        // Определяем часовой пояс по сегменту URL
        String timezoneId;
        switch (citySegment) {
            case "minsk":
                timezoneId = "Europe/Minsk";
                break;
            case "washington":
                // Вашингтон находится в зоне America/New_York
                timezoneId = "America/New_York";
                break;
            case "beijing":
                // Пекин находится в зоне Asia/Shanghai
                timezoneId = "Asia/Shanghai";
                break;
            default:
                // Неизвестный URL — возвращаем 404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write(
                        "Город не найден. Доступные пути: /minsk, /washington, /beijing"
                );
                return;
        }

        // Получаем текущее время в нужной зоне
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(timezoneId));
        String timeString = zdt.format(formatter);

        // Устанавливаем тип контента и кодировку
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Пишем ответ: город и время
        response.getWriter().write(citySegment + ": " + timeString);
    }
}