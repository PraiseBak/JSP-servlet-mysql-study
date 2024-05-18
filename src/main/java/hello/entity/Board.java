package hello.entity;


import lombok.AllArgsConstructor;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;


@AllArgsConstructor
public class Board
{

    Long id;

    String content;

    LocalDateTime createAt;

    LocalDateTime updatedAt;

    Member member;
}
