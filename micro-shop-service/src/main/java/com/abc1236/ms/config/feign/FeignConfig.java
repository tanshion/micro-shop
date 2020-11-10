package com.abc1236.ms.config.feign;

import com.abc1236.ms.config.feign.ssl.NoopHostnameVerifier;
import com.abc1236.ms.config.feign.ssl.TrustingSSLSocketFactory;
import feign.Client;
import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeignConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }


    @Bean
    public FeignClientInterceptor getFeignClientInterceptor() {
        return new FeignClientInterceptor();
    }


    @Bean
    public Feign.Builder feignBuilder() {
        final Client trustSSLSockets = feignClient();
        return Feign.builder().client(trustSSLSockets);
    }

    @Bean
    public Client feignClient() {
        return new Client.Default(TrustingSSLSocketFactory.get(), NoopHostnameVerifier.INSTANCE);
    }
}
