package hello.service;

import java.util.HashMap;
import java.util.Map;

public class UserSessionManager {
    // 사용자 정보를 저장할 캐시 맵
    private Map<String, String> sessionUserMap;

    private static UserSessionManager instance;

    private UserSessionManager() {
        sessionUserMap = new HashMap<>();
    }

    public static synchronized UserSessionManager getInstance() {
        if (instance == null) {
            instance = new UserSessionManager();
        }
        return instance;
    }

    public void addUserSession(String sessionId, String username) {
        sessionUserMap.put(sessionId, username);
    }

    public String getUsername(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    public void removeUserSession(String sessionId) {
        sessionUserMap.remove(sessionId);
    }
}
