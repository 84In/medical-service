package com.vasd.medical_service.auth.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vasd.medical_service.auth.dto.request.LoginRequestDto;
import com.vasd.medical_service.auth.dto.request.LogoutRequestDto;
import com.vasd.medical_service.auth.dto.response.AuthenticationResponseDto;
import com.vasd.medical_service.auth.entities.InvalidatedToken;
import com.vasd.medical_service.auth.repository.InvalidatedTokenRepository;
import com.vasd.medical_service.auth.entities.User;
import com.vasd.medical_service.auth.repository.UserRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESH_DURATION;

    public AuthenticationResponseDto authenticate(LoginRequestDto request) {

        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        var token = generateToken(user);
        log.info("Token generate {}",token);

        return AuthenticationResponseDto.builder()
                .token(token)
                .authenticated(true)
                .username(request.getUsername())
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) {
        SignedJWT signedJWT = null;
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            signedJWT = SignedJWT.parse(token);
            Date expiration = (isRefresh) ?
                    new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESH_DURATION, ChronoUnit.HOURS).toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            if (!verified && expiration.after(new Date())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return signedJWT;
    }

    public void logout(LogoutRequestDto request) {
        try {
            var signedJWT = verifyToken(request.getToken(), true);

            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .token(jit)
                    .expiryTime(expirationDate)
                    .build();
            tokenRepository.save(invalidatedToken);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("medical_service")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(VALID_DURATION)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", builderScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String builderScope(User user) {
        if (user.getRole() != null) {
            return "ROLE_" + user.getRole().getName();
        }
        return "";
    }


    // Scheduled task to clean up expired tokens at 00:00 AM daily
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpExpiredTokens() {
        log.info("Starting cleanup of expired tokens at 00:00 AM...");
        var now = new Date();
        var expiredTokens = tokenRepository.findAll().stream()
                .filter(token -> token.getExpiryTime().before(now))
                .toList();
        tokenRepository.deleteAll(expiredTokens);
        log.info("Completed cleanup of expired tokens.");
    }
}
