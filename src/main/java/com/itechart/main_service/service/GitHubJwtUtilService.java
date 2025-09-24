package com.itechart.main_service.service;

import com.itechart.main_service.config.GitHubConfig;
import com.itechart.main_service.config.RsaKeyLoader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class GitHubJwtUtilService {
    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
    private final GitHubConfig config;


    public String generateJwt() {
        long nowMillis = System.currentTimeMillis();
        Date issuedAt = new Date(nowMillis - 60_000);
        Date expiration = new Date(nowMillis + 600_000);

        return Jwts.builder()
                .setIssuer(String.valueOf(config.getAppId()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(loadPrivateKey(config.getPemPath()), SignatureAlgorithm.RS256)
                .compact();
    }


    public static RSAPrivateKey loadPrivateKey(String pemPath) {
        try (PEMParser parser = new PEMParser(new FileReader(pemPath))) {
            Object object = parser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair keyPair) {
                return (RSAPrivateKey) converter.getPrivateKey(keyPair.getPrivateKeyInfo());
            } else if (object instanceof PrivateKeyInfo keyInfo) {
                return (RSAPrivateKey) converter.getPrivateKey(keyInfo);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object: " + object.getClass());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA private key from " + pemPath, e);
        }
    }

}
