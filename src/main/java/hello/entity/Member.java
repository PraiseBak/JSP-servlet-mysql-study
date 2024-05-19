package hello.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class Member
{
    Long id;

    String username;

    String password;

    public Member(Long id,String username,String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return this.id;
    }
}
