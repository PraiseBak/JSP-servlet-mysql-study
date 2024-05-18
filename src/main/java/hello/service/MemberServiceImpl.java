package hello.service;

import hello.entity.Member;
import lombok.extern.java.Log;

import java.sql.*;

@Log
public class MemberServiceImpl implements MemberService{

    @Override
    public Member findById(Long id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM board WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1,id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long userId = rs.getLong("id");
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        return new Member(userId,username,password);
                    }
                }

            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }

        return null;

    }
}
