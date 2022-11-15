package com.abc1236.ms.client;

import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.result.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "localhost", url = "http://127.0.0.1:"+"${server.port}", path = "")
public interface AuthClient {
    @PostMapping(value = "/authentication/username", headers = {"Authorization=Basic WGNXZWJBcHA6WGNXZWJBcHA="})
    ResultEntity<AccessToken> loginByUsername(@RequestParam("username") String username,
        @RequestParam("password") String password);

}
