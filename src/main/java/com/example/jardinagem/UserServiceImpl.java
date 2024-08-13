package com.example.jardinagem;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CursoRepo cursoRepository;
    private PasswordEncoder passwordEncoder;
    private static Long index = 10L;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, CursoRepo cursoRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cursoRepository = cursoRepository;
        index++;
    }

    @Override
    public void saveUser(UserDto userDto,String role1) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setCurso(userDto.getCurso());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(role1.equals("Professor")){
            Role role = roleRepository.findByName("ROLE_Professor");
            if(role == null){
                role = checkRoleExist(role1);
            }

            user.setRoles(Arrays.asList(role));
        }else if(role1.equals("Aluno")){
            Role role = roleRepository.findByName("ROLE_Aluno");
            if(role == null){
                role = checkRoleExist(role1);
            }
            user.setRoles(Arrays.asList(role));
        }else {
            Role role = roleRepository.findByName("ROLE_ADMIN");
            if(role == null){
                role = checkRoleExist(role1);
            }
            user.setRoles(Arrays.asList(role));
        }
        userRepository.save(user);
    }

    @Override
    public User findUserByNome(String Nome){return userRepository.findByName(Nome);}

    @Override
    public List<User> findall(){return userRepository.findAll();}

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }
    @Override
    public List<User> findAllCursos(){
        return userRepository.findAll();
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist(String role1){
        Role role = new Role();
        role.setName("ROLE_"+ role1);
        return roleRepository.save(role);
    }

    @Override
    public void deleteuser(String nome){
        User user = userRepository.findByName(nome);
        user.setCurso(null);
        user.setRoles(null);
        userRepository.delete(user);
    }

    @Override
    public void updateuser(long id,String email, String nome){
        User user = userRepository.findByid(id);
        user.setName(nome);
        user.setEmail(email);
        userRepository.save(user);
    }
}