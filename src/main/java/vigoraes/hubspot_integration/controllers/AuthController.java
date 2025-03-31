package vigoraes.hubspot_integration.controllers;

import java.io.IOException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import vigoraes.hubspot_integration.Dtos.AuthDto;
import vigoraes.hubspot_integration.services.AuthService;
import vigoraes.hubspot_integration.utils.CacheUtils;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/initialize-oauth")
    public  ResponseEntity<String> initializeOauth(HttpServletResponse res) throws IOException{
        return authService.initializeOauth(res);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleOAuthCallback(@RequestParam("code") String code) {

        CacheUtils.cacheOAuthCode(code);
        
        return ResponseEntity.ok("Código recebido: " + code);
    }
    

    @PostMapping("create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody AuthDto authDto) {
        return authService.createUser(authDto);
    }
    
    @PostMapping("/get-tokens")
    public ResponseEntity<String> getAccessTokenAndRefreshToken(@Valid @RequestBody AuthDto authDto) {
        
        return authService.getAccessTokenAndRefreshToken(authDto);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>("Erro de validação: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
