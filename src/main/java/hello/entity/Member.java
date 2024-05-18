package hello.entity;


import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@AllArgsConstructor
public class Member
{
    Long id;

    String username;

    String password;
}
