package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimeZone;


@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = request.getParameter("timezone");

        // Проверяем наличие параметра timezone и его корректность
        if (timezoneParam != null && !isValidTimezone(timezoneParam)) {
            // Если таймзона некорректна, возвращаем ошибку 400
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.write("<html>");
                out.write("<head><title>Invalid Timezone</title></head>");
                out.write("<body>");
                out.write("<h1>Invalid timezone</h1>");
                out.write("</body>");
                out.write("</html>");
            }
            return; // Прерываем дальнейшую обработку
        }

        // Если таймзона корректна, продолжаем обработку запроса
        chain.doFilter(request, response);
    }

    private boolean isValidTimezone(String timezone) {
        if (timezone == null) {
            return false;
        }

        // Проверка корректности часового пояса для форматов "UTC " и "UTC-"
        if (timezone.startsWith("UTC ")) {
            try {
                int getUTC = Integer.parseInt(timezone.substring(4));
                return getUTC >= -12 && getUTC <= 14;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (timezone.startsWith("UTC-")) {
            try {
                int getUTC = Integer.parseInt(timezone.substring(4));
                return getUTC >= -12 && getUTC <= 14;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Проверка, существует ли таймзона в TimeZone
        return TimeZone.getTimeZone(timezone).getID().equals(timezone);
    }
}

