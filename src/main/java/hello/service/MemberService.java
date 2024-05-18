package hello.service;

import hello.entity.Member;

public interface MemberService {
    String DB_URL = "jdbc:mysql://localhost:3306/servletTest";
    String DB_USER = "root";
    String DB_PASSWORD = "3062";



    Member findById(Long id);

    void save(String username, String password);
}
