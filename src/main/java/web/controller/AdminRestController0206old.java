//package web.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import web.dto.UserDTO;
//import web.mappers.UserMapper;
//import web.model.User;
//import web.service.UserService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
////29-05
//@RestController
//@RequestMapping()
//public class AdminRestController0206old {
//
//    private UserService userService;
//
//    @Autowired
//    public AdminRestController0206old(UserService userService) {
//        this.userService = userService;
//    }
//
//    //31-05
//    //коллекция юзера
//    @GetMapping("/admin/test")
//    public ResponseEntity<List<UserDTO>> apiGetUsers() {           //универсальный тип (можно любые данные передавать)
//        List<User> users = userService.listUsers();
//
////        final List<UserTest> users = new ArrayList<UserTest>() {{
////            add(new UserTest(10, "Alex", "Tester"));
////            add(new UserTest(20, "Biba", "Director"));
////            add(new UserTest(30, "Boba", "CEO"));
////        }};
//
//        //.map(user -> modelMapper.map(user, UserDTO.class))    //modelMapper - бин из конфига
//        List<UserDTO> userDTOs = users.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
//        return new ResponseEntity<>(userDTOs, HttpStatus.OK);      //тело ответа со статусом ОК
//    }
//
//
//    //01-06
//    //конкретный юзер, а не коллекция
//    //PostDto postDto = modelMapper.map(post, PostDto.class);               //modelmapper маппер
//    //PersonDTO personDto = PersonMapper.INSTANCE.personToPersonDTO(entity);        //mapstruct маппер
//    @GetMapping("/users-update/{id}/edit")
//    public ResponseEntity<UserDTO> edit(@PathVariable("id") int id) {
//        User user = userService.getUserById(id);
//        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);          //!!!!!!!!!!!!
//        return new ResponseEntity<>(userDTO, HttpStatus.OK);
//    }
//
//
//    //02-06
//    @GetMapping("/users-update/{id}")
//    public ResponseEntity<UserDTO> update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
////    //02-06
////    @GetMapping("/users-update/{id}")
////    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
////        userService.updateUser(user);
////        return "redirect:/admin";
////    }
//
//
//
//
//
////    @GetMapping("/admin/test")
////    public ResponseEntity<List<User>> apiGetUsers() {           //универсальный тип (можно любые данные передавать)
////        List<User> users = userService.listUsers();
////        return new ResponseEntity<>(users, HttpStatus.OK);      //тело ответа со статусом ОК
////    }
//
////    @GetMapping("/admin/test")
////    public ResponseEntity<List<UserTest>> getAllUsers() {
////
////        final List<UserTest> users = new ArrayList<UserTest>() {{
////            add(new UserTest(10, "Alex", "Tester"));
////            add(new UserTest(20, "Biba", "Director"));
////            add(new UserTest(30, "Boba", "CEO"));
////        }};
////
////        return new ResponseEntity<>(users, HttpStatus.OK);
////    }
//
//
//    //    @GetMapping("/users")
////    public ResponseEntity<Set<User>> apiGetAllUsers() {
////        Set<User> users = userService.findAllUsers();
////        return new ResponseEntity<>(users, HttpStatus.OK);
////    }
//
////    @GetMapping(value = "")
////    public String listUsersAdmin(Model model) {
////        model.addAttribute("users", userService.listUsers());
////        model.addAttribute("user", new User());
////        model.addAttribute("roles", userService.getRoles());
////
////        return "usersadmin";
////    }
//
//}
