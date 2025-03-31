package vigoraes.hubspot_integration.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vigoraes.hubspot_integration.Dtos.ContactDto;
import vigoraes.hubspot_integration.services.ContactService;

@RestController
@RequestMapping("/contactcontroller")
public class ContactController {

    private final ContactService contactService;


    ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping("/create-contact")
    public ResponseEntity<String> createContact(@Valid @RequestBody ContactDto contactDto) {

        return contactService.createContact(contactDto);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>("Erro de validação: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
}
