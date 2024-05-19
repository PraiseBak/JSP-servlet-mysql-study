package hello.service;

import hello.entity.Member;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.logging.Logger;

@Log
public class MemberServiceImpl implements MemberService{
    public MemberServiceImpl(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member findById(Long id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM MEMBER WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1,id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long userId = rs.getLong("id");
                        String curUsername = rs.getString("username");
                        String curPassword = rs.getString("password");
                        return new Member(userId,curUsername,curPassword);
                    }
                }

            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }

        return null;

    }

    @Override
    public Member findByUsername(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM MEMBER WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1,username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long userId = rs.getLong("id");
                        String curUsername = rs.getString("username");
                        String curPassword = rs.getString("password");
                        return new Member(userId,curUsername,curPassword);
                    }
                }

            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }

        return null;

    }


    @Override
    public void save(String username, String password) {
        if(this.findByUsername(username) != null) throw new IllegalArgumentException("이미 존재하는 유저입니다");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO MEMBER (username,password) VALUES (?,?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1,username);
                stmt.setString(2,password);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }



    }

    @Override
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM MEMBER WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1,username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String objPassword = rs.getString("password");
                        return password.equals(objPassword);
                    }
                }
            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }

        return false;
    }
}
