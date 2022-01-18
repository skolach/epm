package com.epam.taxvicespring.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.epam.taxvicespring.entity.User;

public class AppUtils {

    private AppUtils(){}

    private static int redirectId = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    public static void storeMessage(HttpSession session, String message) {
        session.setAttribute("infoMessage", message);
    }

    public static void storeError(HttpSession session, String error) {
        session.setAttribute("errorMessage", error);
    }

    // Store user info in Session.
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        // On the JSP can access via `${loginedUser}`
        session.setAttribute("loginedUser", loginedUser);
    }

    // Get the user information stored in the session.
    public static User getLoginedUser(HttpSession session) {
        if (null != session.getAttribute("loginedUser")) {
            return (User) session.getAttribute("loginedUser");
        }
        return null;
    }

    public static int storeRedirectAfterLoginUrl(String requestUri) {
        Integer id = uri_id_map.get(requestUri); //NOSONAR

        if (id == null) {
            id = redirectId++;
            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(int redirectId) {
        String url = id_uri_map.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }

    public static Timestamp stringToTimestamp(String dateTime) {
        try {
            //return Timestamp.valueOf(dateTime.replace("T", " ") + ":00");
            return Timestamp.valueOf(dateTime.replace("T", " "));
        } catch (Exception e) {
            return null;
        }
    }

    public static String timestampToString(Timestamp dateTime) {
        try {
            return dateTime.toString().replace(" ", "T");
        } catch (Exception e) {
            return null;
        }
    }
}