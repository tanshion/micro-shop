package com.abc1236.ms.config.feign.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NoopHostnameVerifier implements HostnameVerifier {

    public static final NoopHostnameVerifier INSTANCE = new NoopHostnameVerifier();

    @Override
    public boolean verify(final String s, final SSLSession sslSession) {
        return true;
    }

    @Override
    public final String toString() {
        return "NO_OP";
    }

}