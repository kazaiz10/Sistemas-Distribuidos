package com.example.jardinagem;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto, String role1);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    List<User> findAllCursos();
    User findUserByNome(String Nome);

    List<User> findall();

    void deleteuser(String nome);

    public void updateuser(long id, String email, String nome);
}