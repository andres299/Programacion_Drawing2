package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{
    static List<User> userList = new ArrayList<>() ;

    @Override
    public boolean existUser(String login) {
        // Verifica si la lista de usuarios no es nula.
        if (userList != null) {
            // Recorremos la lista de usuarios.
            for (User user : userList) {
                // Retorna true si se encuentra un usuario con el mismo nombre de usuario.
                if (user.getLogin().equalsIgnoreCase(login)) {
                    return true;
                }
            }
        }
        //Si no encuentra un usuario retorna false
        return false;
    }
    @Override
    public void register(User newUser) {
        //Añade un usuario a las lista
        userList.add(newUser);
    }
    @Override
    public boolean login(String login, String password) {
        //Recorre la lista de usuarios
        for (User user : userList) {
            // Compara el nombre de usuario y la contraseña y retorna true si coinciden.
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return true;
            }
        }
        //Si no retorna false
        return false;
    }
}
