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
        // Obtenemos el usuario actual y el email
        User user = (User) session.getAttribute("user");
        String email = (String) session.getAttribute("email");
        System.out.println(email);
        // Verificar si el usuario o el email están autenticados.
        if (user == null && email == null) {
            // Redirigir a la página de inicio de sesión si no están autenticados.
            resp.sendRedirect("/login");
            return false;
        }

        // Agregar el usuario al atributo. Puede ser nulo si solo se ha autenticado con el email.
        req.setAttribute("user", user);
        // Continuar con la ejecución del controlador actual
        return true;
    }
}
