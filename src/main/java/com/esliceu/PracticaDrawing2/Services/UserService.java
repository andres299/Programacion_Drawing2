package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    //Metodo para registrarse
    public String registerUser(String login, String name, String password) {
        if (existsLogin(login)) {
            return "El usuario ya existe.";
        } else if (password.length() < 5) {
            return "La contraseña debe tener al menos 5 caracteres.";
        } else if (!validUsername(login)) {
            return "El login solo permite letras y números.";
        } else if (!noSpaces(password)) {
            return "La contraseña no debe contener espacios.";
        } else {
            register(login, name, password);
            return null;
        }
    }

    // Método que verifica si existe un usuario con el nombre de usuario proporcionado.
    public boolean existsLogin(String login) {
        return userRepo.existUser(login);
    }

    // Método para registrar un nuevo usuario.
    public void register(String login, String name, String password) {
        User user = new User(login,name,password);
        user.setPassword(xifratMD5(user.getPassword()));
        userRepo.register(user);
    }

    // Métodos para validar login y contraseñas
    private boolean validUsername(String word) {
        return word.matches("[a-zA-Z0-9]+");
    }

    private boolean noSpaces(String password) {
        return !password.contains(" ");
    }

    // Método que comprueba la existencia de un usuario.
    public boolean userExists(String login, String password) {
        return userRepo.login(login,xifratMD5(password));
    }
    // Función que cifra una contraseña utilizando el algoritmo de hash MD5
    public String xifratMD5(String password){
        return DigestUtils.md5Hex(password).toUpperCase();
    }









    public User user(String login) {
        return userRepo.user(login);
    }

    public List<User> allUsers(int id_user) { return userRepo.allUsers(id_user);}

}
