package dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String access_token;
    private Long expires_in;
}
