package vigoraes.hubspot_integration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Mono;
import vigoraes.hubspot_integration.Dtos.AuthDto;
import vigoraes.hubspot_integration.Dtos.TokensDto;
import vigoraes.hubspot_integration.utils.CacheUtils;

import java.io.IOException;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    private final WebClient webClient = WebClient.create("https://api.hubapi.com");

    @Value("${hubspot.client_id}")
    private String clientId; 

    @Value("${hubspot.client_secret}")
    private String clientSecret;

    private String uri;

    @Autowired
    private CacheUtils cacheUtils;

    private String redirectUri = "http://localhost:3333/auth/login"; 

    AuthService(CacheUtils cacheUtils){
        cacheUtils = this.cacheUtils;
    }

    @PostConstruct
    public void init() {
        this.uri = UriComponentsBuilder.fromUriString("https://app.hubspot.com/oauth/authorize")
                                    .queryParam("client_id", clientId)
                                    .queryParam("redirect_uri", redirectUri)
                                    .queryParam("scope", "oauth")
                                    .toUriString();
    }

    public ResponseEntity<String> initializeOauth(HttpServletResponse res) throws IOException{

        res.sendRedirect(uri);

        return new ResponseEntity<>("Redirecionado para OAuth 2.0", HttpStatus.OK);
    }

    public void getAccessTokenAndRefreshToken(String oauthCode){

        this.cacheUtils.cacheOAuthCode(oauthCode);

        MultiValueMap<String, String> payloadBody = new LinkedMultiValueMap<>();

        payloadBody.add("grant_type", "authorization_code");
        payloadBody.add("client_id", clientId);
        payloadBody.add("client_secret", clientSecret);
        payloadBody.add("redirect_uri", redirectUri);
        payloadBody.add("code", oauthCode);
        
        Mono<TokensDto> responseBody = webClient.post()
                                .uri("/oauth/v1/token")
                                .bodyValue(payloadBody)
                                .retrieve()
                                .bodyToMono(TokensDto.class);

        TokensDto tokens = responseBody.block();

        this.cacheUtils.cacheTokens(tokens);
        
        // return new ResponseEntity<>("Tokens resgatados com sucesso.", HttpStatus.OK);
    }

    public ResponseEntity<String> createUser(AuthDto authDto){

        return new ResponseEntity<>("Usuário criado com sucesso: " + authDto.getUsername(), HttpStatus.CREATED);
    }

}
