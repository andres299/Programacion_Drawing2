package com.esliceu.PracticaDrawing2.Filter;

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
        //Obtenemos el usuario atual
        String login = (String) session.getAttribute("login");
        req.setAttribute("loginSession", login);

        //Verificar si el usuario esta autentificado.
        if (login == null) {
            //Redirigir a la pagina de inicio de sesion si no esta autenticado.
            resp.sendRedirect("/login");
        }
        return true;
    }
}
