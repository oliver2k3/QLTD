package com.nhom7.qltd.restcontroller;

import com.nhom7.qltd.dto.LoanDto;
import com.nhom7.qltd.mobile_service.LoanService;
import com.nhom7.qltd.mobile_service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {
    private final LoanService loanService;
    private final UsersService userService;

    @PostMapping("/apply")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanDto loanDto, @RequestHeader("Authorization") String token) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            String email = userService.getEmailfromToken(token.substring(7));
            loanDto.setEmail(email);
            loanService.createLoan(loanDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            responseBody.put("error", ie.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (DuplicateKeyException de) {
            responseBody.put("error", de.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
    }
}