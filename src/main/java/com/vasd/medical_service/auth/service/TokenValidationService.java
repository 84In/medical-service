package com.vasd.medical_service.auth.service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.vasd.medical_service.auth.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final InvalidatedTokenRepository tokenRepository;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Value("${jwt.refreshable-duration}")
    private long refreshDuration;

    public boolean isValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            log.info("Validating token {}", token);
            log.info(verifier.toString());

            boolean verified = signedJWT.verify(verifier);

            Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean expired = expiredTime == null || new Date().after(expiredTime);

            boolean revoked = tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());

            log.info("verified = {}, expired = {}, revoked = {}", verified, expired,revoked);
            return verified && !expired && !revoked;
        } catch (Exception e) {
            return false;
        }
    }

}
