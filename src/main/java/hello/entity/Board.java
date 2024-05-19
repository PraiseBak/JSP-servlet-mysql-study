package hello.entity;


import java.lang.reflect.Constructor;
import java.time.LocalDateTime;



public class Board
{
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Member getMember() {
        return member;
    }

    public Board(Long id, String title, String content, LocalDateTime createAt, LocalDateTime updatedAt, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.member = member;
    }

    Long id;

    String title;
    String content;

    LocalDateTime createAt;

    LocalDateTime updatedAt;

    Member member;


}
