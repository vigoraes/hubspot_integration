package vigoraes.hubspot_integration.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody List<Map<String, Object>> payload) {
        
        for (Map<String, Object> evento : payload) {
            System.out.println("Evento recebido: " + evento);
        }
    
        return ResponseEntity.ok("Webhook recebido com sucesso!");
    }
    
}
