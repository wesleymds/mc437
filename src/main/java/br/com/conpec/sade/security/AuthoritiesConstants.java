package br.com.conpec.sade.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String CCO = "ROLE_CCO";

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String MEMBER = "ROLE_MEMBER";

    public static final String USER = "ROLE_USER";

    public static final String EXTERNAL = "ROLE_EXTERNAL";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
