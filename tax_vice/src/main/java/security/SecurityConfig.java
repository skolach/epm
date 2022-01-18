package security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {
    private SecurityConfig(){}

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig =
        new HashMap<>();

    static {
        init();
    }

    private static void init() {

        // Configure For "CUSTOMER" Role.
        List<String> urlPatternsCustomer = new ArrayList<>();

        urlPatternsCustomer.add("/customerTask");
        urlPatternsCustomer.add("/proposalTask");

        mapConfig.put(ROLE_CUSTOMER, urlPatternsCustomer);

        // Configure For "ADMIN" Role.
        List<String> urlPatternsAdmin = new ArrayList<>();

        urlPatternsAdmin.add("/adminTask");
        urlPatternsAdmin.add("/customerTask");
        urlPatternsAdmin.add("/proposalTask");

        mapConfig.put(ROLE_ADMIN, urlPatternsAdmin);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}