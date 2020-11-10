package com.abc1236.ms.core.authentication.token;

import com.abc1236.ms.core.authentication.constant.TokenConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccessToken {

    @ApiModelProperty(value = "accessToken")
    private String token;

    public AccessToken() {
    }

    public AccessToken(String token) {
        this.token = token;
    }

    public AccessToken(String clientId, String userId, String jti) {
        this.token = TokenConstant.STATELESS_ACCESS_TOKEN + ":" + clientId + ":" + userId + ":" + jti;
    }


}
