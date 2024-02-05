package com.esliceu.PracticaDrawing2.Filter;

import com.esliceu.PracticaDrawing2.Entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.http.HttpRequest;
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    HttpSession session;
    @Override
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws Exception{
        // Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        // Agregar el usuario al atributo.
        req.setAttribute("user", user);

        // Verificar si el usuario está autenticado.
        if (user == null) {
            // Redirigir a la página de inicio de sesión si no está autenticado.
            resp.sendRedirect("/login");
            return false;
        }
        // Continuar con la ejecución del controlador actual
        return true;
    }
}
