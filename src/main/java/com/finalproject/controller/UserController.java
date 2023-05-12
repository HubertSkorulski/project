package com.finalproject.controller;

import com.finalproject.auth.AuthRequest;
import com.finalproject.auth.AuthResponse;
import com.finalproject.domain.RestaurantUser;
import com.finalproject.dto.UserDto;
import com.finalproject.exception.UserNotFoundException;
import com.finalproject.mapper.UserMapper;
import com.finalproject.service.TokenService;
import com.finalproject.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/user")
@CrossOrigin
public class UserController {

    private final UserDbService userDbService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @GetMapping//dodać
    public UserDto getCustomer(@RequestParam Long userId) throws UserNotFoundException {
        RestaurantUser restaurantUser = userDbService.getUser(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.mapToCustomerDto(restaurantUser);
    }

    @PostMapping
    public UserDto createCustomer(@RequestParam String emailAddress) {
        RestaurantUser restaurantUser = new RestaurantUser(emailAddress,"test");
        userDbService.save(restaurantUser);
        return userMapper.mapToCustomerDto(restaurantUser);
    }

    @PutMapping("updateCustomer")
    public UserDto updateCustomer(@RequestParam Long customerId, @RequestParam String emailAddress) throws UserNotFoundException {
        RestaurantUser restaurantUser = userDbService.getUser(customerId).orElseThrow(UserNotFoundException::new);
        restaurantUser.setUsername(emailAddress);
        userDbService.save(restaurantUser);
        return userMapper.mapToCustomerDto(restaurantUser);
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam Long userId) throws UserNotFoundException {
        RestaurantUser restaurantUser = userDbService.getUser(userId).orElseThrow(UserNotFoundException::new);
        userDbService.deleteUser(restaurantUser);
        //tu tez sie przyjrzec czy sie usunie jak mu sie nie wyjebie wszystkich pozostalych obiektów
    }

    @GetMapping(value = "getCustomers")
    public List<UserDto> getAllCustomers() {
        List<RestaurantUser> restaurantUsers = userDbService.getAllUsers();
        return userMapper.mapToCustomerDtoList(restaurantUsers);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = tokenService.generateToken(user);
            AuthResponse authResponse = new AuthResponse(user.getUsername(), token);
            return ResponseEntity.ok(authResponse);
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
