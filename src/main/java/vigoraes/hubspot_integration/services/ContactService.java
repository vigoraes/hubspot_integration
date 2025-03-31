package vigoraes.hubspot_integration.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import vigoraes.hubspot_integration.Dtos.ContactDto;

@Service
@Transactional
public class ContactService {

    public ResponseEntity<String> createContact(ContactDto contactDto){

        return new ResponseEntity<>("Contato criado com sucesso: " + contactDto.getContact(), HttpStatus.CREATED);
        
    }
}
