package com.example.jardinagem;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JardinagemController {
    @Autowired
    private CursoService cursoService;

    private UserService userService;

    public JardinagemController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page request
    @GetMapping("/")
    public String home(){
        return "index";
    }
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        List<Curso> curso = cursoService.findAllCursos();
        model.addAttribute("cursoList", curso);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               @RequestParam("role") String role,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto,role);
        return "redirect:/register?success";
    }



    @GetMapping("/users")
    public String users(Model model){
        List<User> users=new ArrayList<>();
        userService.findall().forEach(a ->
        {
        if (!a.getRoles().get(0).getName().equals("ROLE_Admin")) {
                users.add(a);
            }
        });
        model.addAttribute("users", users);


        List<Curso> curso = cursoService.findAllCursos();
        model.addAttribute("cursoList", curso);



        return "users";
    }
    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }

    @GetMapping("/remove")
    public String remove(Model model){
        List<User> users=new ArrayList<>();
        userService.findall().forEach(a ->
        {
            if (!a.getRoles().get(0).getName().equals("ROLE_Admin")) {
                users.add(a);
            }
        });
        model.addAttribute("users", users);

        String s = "";
        model.addAttribute("remove", s);
        return "remove";
    }

    @PostMapping("/remove")
    public String removed(@ModelAttribute("remove") String s){
        userService.deleteuser(s);
        return "redirect:/remove?sucess";
    }

    @GetMapping("/registerCurso")
    public String registerC(Model model){
        Curso curso = new Curso();
        model.addAttribute("curso", curso);
        return "registerC";
    }

    @PostMapping("/registerCurso")
    public String registerCsave(@ModelAttribute("curso") CursoDto cursoDto, BindingResult result, Model model){
        Curso curso = cursoService.findCursoByNome(cursoDto.getNome());

        if(curso != null && curso.getNome() != null && !curso.getNome().isEmpty()){
            result.rejectValue("name",null,"Já existe uma curso com este nome");
        }

        if(result.hasErrors()){
            model.addAttribute("curso", cursoDto);
            return "registerC";
        }

        cursoService.saveCurso(cursoDto);

        return "redirect:/registerCurso?sucess";
    }


    @GetMapping("/update")
    public String update(Model model){
        List<User> users=new ArrayList<>();
        userService.findall().forEach(a ->
        {
            if (!a.getRoles().get(0).getName().equals("ROLE_Admin")) {
                users.add(a);
            }
        });
        model.addAttribute("users", users);

        String s = "";
        String curso="";
        model.addAttribute("remove", s);
        model.addAttribute("curso", curso);
        return "updateC";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("remove") String s,String curso){
        userService.findUserByNome(s).setCurso(cursoService.findCursoByNome(curso));
        return "redirect:/updateC?sucess";
    }
    @GetMapping("/professor")
    public String professor(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        model.addAttribute("user", user);
        return "professor";
    }

    @PostMapping("/professor")
    public String professorsave(@ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("curso", user);
            return "professor";
        }

        userService.updateuser(user.getId(),user.getEmail(), user.getName());
        return "redirect:/professor?sucess";
    }

    @GetMapping("/aluno")
    public String aluno(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        model.addAttribute("user", user);
        return "aluno";
    }

    @PostMapping("/aluno")
    public String aluno(@ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("curso", user);
            return "professor";
        }

        userService.updateuser(user.getId(),user.getEmail(), user.getName());
        return "redirect:/aluno?sucess";
    }

}
