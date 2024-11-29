package edu.oakland.sophomoreproject.components;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordHasher {
    private final String secretKey;

    @Autowired
    public PasswordHasher(@Value("${auth.hashing_secret_key:dev123}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String hashPassword(String password) {
        return Hashing.hmacSha256(secretKey.getBytes(StandardCharsets.UTF_8))
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
