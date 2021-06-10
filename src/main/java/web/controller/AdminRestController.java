package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dto.RoleDTO;
import web.dto.UserDTO;
import web.exeptionHandler.DataInfoHandler;
import web.exeptionHandler.UserWithSuchLoginExist;
import web.mappers.RoleMapper;
import web.mappers.UserMapper;
import web.model.Role;
import web.model.User;
import web.model.UserTest;
import web.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//29-05
@RestController
@RequestMapping()
public class AdminRestController {

    private UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    //31-05
    //коллекция юзера
    @GetMapping("/admin/test")
    public ResponseEntity<List<UserDTO>> apiGetUsers() {           //универсальный тип (можно любые данные передавать)
        List<User> users = userService.listUsers();

//        final List<UserTest> users = new ArrayList<UserTest>() {{
//            add(new UserTest(10, "Alex", "Tester"));
//            add(new UserTest(20, "Biba", "Director"));
//            add(new UserTest(30, "Boba", "CEO"));
//        }};

        //.map(user -> modelMapper.map(user, UserDTO.class))    //modelMapper - бин из конфига
        List<UserDTO> userDTOs = users.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);      //тело ответа со статусом ОК
    }

    //05-06
    //коллекция роли
    @GetMapping("/admin/test/roles")
    public ResponseEntity<List<RoleDTO>> apiGetRoles() {           //универсальный тип (можно любые данные передавать)
        List<Role> roles = userService.getRoles();
        List<RoleDTO> roleDTOs = roles.stream().map(RoleMapper.INSTANCE::toDTO).collect(Collectors.toList());

        return new ResponseEntity<>(roleDTOs, HttpStatus.OK);      //тело ответа со статусом ОК
    }

    //01-06
    //конкретный юзер, а не коллекция
    //PostDto postDto = modelMapper.map(post, PostDto.class);               //modelmapper маппер
    //PersonDTO personDto = PersonMapper.INSTANCE.personToPersonDTO(entity);        //mapstruct маппер
    @GetMapping("/users-update/{id}/edit")
    public ResponseEntity<UserDTO> edit(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);          //!!!!!!!!!!!!
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //arthur
    //valid https://www.youtube.com/watch?v=96AVOLMnS8c alishev
    @PutMapping("/users-update/{id}")
    public ResponseEntity<DataInfoHandler> apiUpdateUser(@PathVariable("id") int id,
                                                         @RequestBody @Valid User user,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new DataInfoHandler(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(user);       //юзера не пропускаем через дтошку, глянуть как у стасона
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new UserWithSuchLoginExist("User with such login Exist");
        }
    }


//    @PostMapping("/users-add")
//    public ResponseEntity<DataInfoHandler> apiAddUser(@RequestBody @Valid User user){
//        userService.addUser(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/users-add")
    public ResponseEntity<DataInfoHandler> apiAddNewUser(@Valid @RequestBody User user,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new DataInfoHandler(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new UserWithSuchLoginExist("User with such login Exist");
        }
    }



//    @PostMapping("/users")
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.addUser(user);
//        return "redirect:/admin";
//    }


    @GetMapping("/users-delete/{id}")
    public ResponseEntity<DataInfoHandler> apiDeleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return new ResponseEntity<>(new DataInfoHandler("User was deleted"), HttpStatus.OK);
    }

    //arthur
    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}
