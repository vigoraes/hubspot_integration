package vigoraes.hubspot_integration.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import vigoraes.hubspot_integration.Dtos.AuthDto;


@RestController
@RequestMapping("/authcontroller")
public class AuthController {

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody AuthDto AuthDTO) {
        return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthDto authDTO) {
        
        return "Login criado com sucesso: " + authDTO.getUsername();
    }

    @PostMapping("/create-contact")
    public String createContact(@Valid @RequestBody AuthDto authDTO) {

        return "Contato criado com sucesso.";
    }
    

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>("Erro de validação: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
