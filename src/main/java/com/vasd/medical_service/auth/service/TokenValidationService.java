package com.vasd.medical_service.auth.service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.vasd.medical_service.auth.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

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

            boolean verified = signedJWT.verify(verifier);

            Date issueTime = signedJWT.getJWTClaimsSet().getIssueTime();
            Date expiredTime = Date.from(issueTime.toInstant().plus(refreshDuration, ChronoUnit.HOURS));

            boolean expired = new Date().after(expiredTime);

            boolean revoked = tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());

            return verified && !expired && !revoked;

        } catch (Exception e) {
            return false;
        }
    }
}
