package vigoraes.hubspot_integration.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokensDto {


    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // Optional: Override toString for better printing
    @Override
    public String toString() {
        return "OAuthResponseDTO{" +
               "tokenType='" + tokenType + '\'' +
               ", refreshToken='" + refreshToken + '\'' +
               ", expiresIn=" + expiresIn +
               ", accessToken='" + accessToken + '\'' +
               '}';
    }
    
}
