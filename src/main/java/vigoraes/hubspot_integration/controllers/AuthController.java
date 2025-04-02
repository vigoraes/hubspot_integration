package vigoraes.hubspot_integration.controllers;

import java.io.IOException;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CacheUtils cacheUtils;

    AuthController(AuthService authService, CacheUtils cacheUtils) {
        this.authService = authService;
        this.cacheUtils = cacheUtils;
    }


    @GetMapping("/oauth")
    public  ResponseEntity<String> initializeOauth(HttpServletResponse res) throws IOException{
        return authService.initializeOauth(res);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("code") String code) {

        if(code == null){
            throw new RuntimeException("Código retornado é nulo.");
        }

        authService.getAccessTokenAndRefreshToken(code);

        return ResponseEntity.ok("Login realizado.");
    }
    

    @PostMapping("create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody AuthDto authDto) {
        return authService.createUser(authDto);
    }
    
    @GetMapping("/get-cache/{cache}")
    public ResponseEntity<String> getCache(@PathVariable("cache") String cache) {
        switch (cache) {
            case "oauth-code":
                return new ResponseEntity<>("Oauth Code: " + this.cacheUtils.getCachedOAuthCode() , HttpStatus.OK);
            case "tokens":
                return new ResponseEntity<>("Access Token: " + this.cacheUtils.getCachedTokens().toString(), HttpStatus.OK);
            default:
                throw new RuntimeException("Paramêtro inválido");
        }
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>("Erro de validação: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
