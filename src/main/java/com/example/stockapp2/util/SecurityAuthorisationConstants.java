package com.example.stockapp2.util;

public class SecurityAuthorisationConstants {
    public static final String[] PUBLIC_URIS = new String[]{
            "/",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/#/**",
            "/register-user",
            "/login",
            "/actuator/**",
            "/instances/**",
            "/webjars/**",
            "/api/auth/**",
            "/api/auth/forgot-security-details/**",
            "/api/auth/reset-security-details/**",
            "/api/auth/security-questions",
            "/api/auth/verify-email/**",
            "/api/subscription/confirm", //TODO     Remove on production
            "/api/subscription/confirm/", //TODO     Remove on production
            "/ws-message",
            "/api/payment/callback",
            "/api/payment/check",
            "/test/**",
            "/api/v1/firebasetesting/**",
            "/api/v1/test",
            "/api/receive-yellowdot-subscription-callback",
            "/api/receive-yellowdot-subscription-callback/",
            "/api/deactivate-subscription",
            "/api/notify-subscription",
            "/api/notify-billing",
            "/api/notify-send-sms",
            "/api/notify-user-deactivation",
            "/api/notify-mobile-originated-message",
            "/api/subscribe",
            "/api/sync-exchange",
            "/api/sync-exchange/"
    };
}
