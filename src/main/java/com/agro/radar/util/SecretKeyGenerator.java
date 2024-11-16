package com.agro.radar.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Generated Secret Key:");
        System.out.println(base64Key);
    }
}
