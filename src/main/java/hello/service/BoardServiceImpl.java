package hello.service;

import hello.dto.BoardDto;
import hello.entity.Board;
import hello.entity.Member;
import lombok.extern.java.Log;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Log
public class BoardServiceImpl implements BoardService {
    private static final int pageSize = 10;
    private final MemberService memberService;

    public BoardServiceImpl(MemberService memberService){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.memberService = memberService;
    }

    @Override
    public Board findById(Long id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM board WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long boardId = rs.getLong("id");
                        String content = rs.getString("content");
                        String title = rs.getString("title");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                        Long userId = rs.getLong("user_id");
                        Member member = memberService.findById(userId);

                        Board board = new Board(boardId,title, content, createdAt, updatedAt,member);
                        return board;
                    }
                }
            }
        } catch (SQLException e) {
            log.info("sql connect error" + e.getMessage());
        }


        return null;
    }

    @Override
    public List<Board> findAll(int page) {
        List<Board> boardList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM board ORDER BY id LIMIT ?, ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int offset = (page - 1) * pageSize; // 시작 인덱스
                stmt.setInt(1, offset);
                stmt.setInt(2, pageSize); // 페이지당 게시물 수

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        String title = rs.getString("title");
                        String content = rs.getString("content");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                        Long memberId = rs.getLong("user_id");
                        Member member = memberService.findById(memberId);
                        Board board = new Board(id, title, content, createdAt,updatedAt,member);
                        boardList.add(board);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게시물을 조회하는데 실패하였습니다.");
        }

        return boardList;
}

    @Override
    public void save(BoardDto boardDto, String username) {
        boardValidCheck(boardDto);

        Member member = memberService.findByUsername(username);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO board (title, content,created_at,user_id) VALUES (?, ?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, boardDto.getTitle());
                stmt.setString(2, boardDto.getContent());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.setLong(4, member.getId());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게시물을 작성하는데 실패하였습니다.");
        }
    }

    /**
     * 입력된 board 객체가 유효한지
     * @param boardDto
     */
    public void boardValidCheck(BoardDto boardDto) {
        if (boardDto.getContent().length() == 0 || boardDto.getTitle().length() == 0) {
            throw new IllegalArgumentException("게시물의 내용이 비어있습니다.");
        }
    }



    @Override
    public void update(Long id, BoardDto updateBoardDto, String username) throws NoSuchElementException {
        Board board = findById(id);
        if (board == null) throw new NoSuchElementException("게시물을 찾을 수 없습니다");

        boardValidCheck(updateBoardDto);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO board (title, content,updated_at) VALUES (?, ?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, updateBoardDto.getTitle());
                stmt.setString(2, updateBoardDto.getContent());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id, String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM board WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
