package vigoraes.hubspot_integration.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Mono;
import vigoraes.hubspot_integration.Dtos.AuthDto;

import java.io.IOException;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    private final WebClient webClient = WebClient.create("https://api.hubapi.com/");

    @Value("${hubspot.client_id}")
    private String clientId; 

    private String uri = UriComponentsBuilder.fromUriString("https://app.hubspot.com/oauth/authorize")
                                            .queryParam("client_id", clientId)
                                            .queryParam("redirect_uri", "http://localhost:3333/auth/callback")
                                            .queryParam("scope", "oauth")
                                            .toUriString();

    public ResponseEntity<String> initializeOauth(HttpServletResponse res) throws IOException{

        res.sendRedirect(uri);

        return new ResponseEntity<>("Redirecionado para OAuth 2.0", HttpStatus.OK);
    }

    public ResponseEntity<String> getAccessTokenAndRefreshToken(AuthDto authDto){

        Mono<String> body = WebClient.builder()
                .baseUrl("https://app.hubspot.com/")
                .build()
                .get()
                .uri("/oauth/authorize?client_id=" + this.clientId + "&scope=contacts%20automation&redirect_uri=https://www.example.com/")
                .retrieve()
                .bodyToMono(String.class);
       
        return new ResponseEntity<>("Login efetuado com sucesso: " + authDto.getUsername(), HttpStatus.OK);
    }

    public ResponseEntity<String> createUser(AuthDto authDto){

        return new ResponseEntity<>("Usuário criado com sucesso: " + authDto.getUsername(), HttpStatus.CREATED);
    }

}
