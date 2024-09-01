package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Устанавливаем тип содержимого ответа
        response.setContentType("text/html;charset=UTF-8");

        // Отправляем HTML-ответ в браузер
        try (PrintWriter out = response.getWriter()) {
            out.write("<html>");
            out.write("<head><title>Current UTC Time</title>");
//            out.write("<meta http-equiv=\"refresh\" content=\"1\">"); // Обновление страницы каждые 5 секунд
            out.write("<style>");
            out.write("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.write("h1, p { text-align: center; }");
            out.write("</style>");
            out.write("</head>");
            out.write("<body>");
            out.write("<div>");

            out.write("<h1>Current UTC Time</h1>");
            out.write("<p>Time: " + getTime(request) + "</p>");

            String timezoneParam = request.getParameter("timezone");

            if (timezoneParam != null) {
                timezoneParam = timezoneParam.replace(" ", "+"); // Заменяем пробел на плюс
                try {
                    out.write("<p>Timezone: " + timezoneParam + "</p>");
                } catch (NumberFormatException e) {
                    out.write("<p>Timezone: " + timezoneParam + "</p>");
                }
            } else {
                out.write("<p>No timezone specified</p>");
            }

            out.write("</div>");
            out.write("</body>");
            out.write("</html>");
        }
    }

    public static String getTime(HttpServletRequest request) {
        String timezone = request.getParameter("timezone");

        try {
            if (timezone == null || timezone.isEmpty()) {
                return ZonedDateTime.now(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));

            } else if (timezone.startsWith("UTC ")) {
                int getUTC = Integer.parseInt(timezone.substring(4));
                return ZonedDateTime.now(ZoneOffset.ofHours(getUTC))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));

            } else if (timezone.startsWith("UTC-")) {
                int getUTC = Integer.parseInt(timezone.substring(4));
                return ZonedDateTime.now(ZoneOffset.ofHours(-getUTC))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));

            } else {
                return ZonedDateTime.now(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));
            }
        } catch (Exception e) {
            return "error";
        }
    }

    private static String insertLetterAtPosition(String str, char newChar, int number) {
        StringBuilder newStr = new StringBuilder(str);
        return  newStr.insert(number, newChar).toString();

    }
}


