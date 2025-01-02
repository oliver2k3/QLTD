package com.nhom7.qltd.controller;

import com.nhom7.qltd.dto.GetUserInfoDto;
import com.nhom7.qltd.dto.LoginDto;
import com.nhom7.qltd.dto.RegisterDto;
import com.nhom7.qltd.dto.TransitionDto;
import com.nhom7.qltd.model.UserEntity;
import com.nhom7.qltd.mapper.UserMapper;
import com.nhom7.qltd.service.TransitionService;
import com.nhom7.qltd.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {
    private final UsersService userService;
    private final TransitionService transitionService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody RegisterDto registerDto) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            UserEntity userEntity = userService.createUser(registerDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{email}")
                    .buildAndExpand(registerDto.getEmail())
                    .toUri();
            return ResponseEntity.created(location).body(userEntity);
        } catch (IllegalArgumentException ie) {
            responseBody.put("error", ie.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (DuplicateKeyException de) {
            responseBody.put("error", de.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
    }

    @GetMapping("/{card}")
    public ResponseEntity<Object> getUserByCard(@PathVariable String card) {
        UserEntity userEntity = userService.getUserByCard(card);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.entityToDto(userEntity));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransitionDto transitionDto, @RequestHeader("Authorization") String token) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            String currentCardNumber = userService.getCardNumberfromToken(token.substring(7));
            transitionDto.setSender(currentCardNumber);
            transitionService.performTransfer(transitionDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            responseBody.put("error", ie.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (DuplicateKeyException de) {
            responseBody.put("error", de.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
    }
    @GetMapping("/all")
    public String getAllUsers() {
        return "List of users";
    }
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDto) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            UserEntity userEntity = userService.validateUser(loginDto);
            if (userEntity != null) {
                String token = userService.generateToken(userEntity);
                System.out.println("Login successful for user: " + loginDto.getEmail());
                responseBody.put("token", token);
                return ResponseEntity.ok(responseBody);
            } else {
                responseBody.put("error", "Invalid credentials");
                System.out.println("Invalid credentials for user: " + loginDto.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }
        } catch (Exception e) {
            responseBody.put("error", e.getMessage());
            System.out.println("Exception during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<Object> getCurrentUser(@RequestHeader("Authorization") String token) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            UserEntity userEntity = userService.getUserByEmail(userService.getEmailfromToken(token.substring(7)));
            System.out.println(userEntity);
            GetUserInfoDto getUserInfoDto = userMapper.entityToDto(userEntity);
            return ResponseEntity.ok(getUserInfoDto);
        } catch (IllegalArgumentException ie) {
            responseBody.put("error", ie.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (EmptyResultDataAccessException ee) {
            responseBody.put("error", ee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @PostMapping ("/recipient-name")
    public ResponseEntity<Object> getRecipientNameByCardNumber(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        UserEntity userEntity = userService.getUserByCard(cardNumber);

        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity.getName());
    }
    @GetMapping("/receiver-name/{card}")
    public ResponseEntity<Object> getReceiverByCardNumber(@PathVariable String card) {
        UserEntity userEntity = userService.getUserByCard(card);

        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity.getName());
    }
}