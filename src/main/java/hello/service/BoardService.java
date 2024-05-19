package hello.service;

import hello.dto.BoardDto;
import hello.entity.Board;

import java.util.List;
import java.util.NoSuchElementException;

public interface BoardService {
    //TODO 가릴것
    //외부설정으로 분리하는게 좋은데 생략
    String DB_URL = "jdbc:mysql://localhost:3306/servletTest";
    String DB_USER = "root";
    String DB_PASSWORD = "3062";



    /**
     * 일치하는 id를 가지는 board를 리턴하는 함수
     * @return Board
     */
    Board findById(Long id);


    List<Board> findAll(int page);

    /**
     *
     */
    void save(BoardDto boardDto, String username);


    void update(Long id, BoardDto updateBoardDto, String username) throws NoSuchElementException;

    void delete(Long id, String username);





}
