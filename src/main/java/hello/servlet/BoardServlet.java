package hello.servlet;


import hello.dto.AddBoardDto;
import hello.dto.BoardDto;
import hello.helper.HttpMethod;
import hello.service.BoardService;
import hello.service.BoardServiceImpl;
import hello.service.MemberServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(urlPatterns = "/board")
public class BoardServlet extends HttpServlet {
    private BoardService boardService;

    @Override
    public void init() throws ServletException {
        boardService = new BoardServiceImpl(new MemberServiceImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 파라미터 가져오는 부분
         */
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        //로그인 구현해야함..
        AddBoardDto addBoardDto = new AddBoardDto(title,content);

        /**
         * todo cache login implement
         */
        String username = "";
        boardService.save(addBoardDto,username);

        resp.sendRedirect("/");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long boardId = Long.parseLong(req.getParameter("id"));

        /**
         * todo cache login implement
         */
        String username = "";
        boardService.delete(boardId,username);
        resp.sendRedirect("/board");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long boardId = Long.parseLong(req.getParameter("id"));

        /**
         * req parameter to boardUpdateDto
         */
        try{
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            AddBoardDto addBoardDto = new AddBoardDto(title,content);
            /**
             * todo cache login implement
             */
            String username = ""
            boardService.update(boardId,addBoardDto,username);
            resp.sendRedirect("/board");
        }catch (NoSuchElementException e){
            resp.sendRedirect("/error?error=" + e.getMessage());
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        HttpMethod httpMethod = HttpMethod.valueOf(method);

        switch (httpMethod) {
            case POST:
                doPost(req,resp);
                break;
            case GET:
                doGet(req,resp);
                break;
            case DELETE:
                doDelete(req,resp);
                break;
            case PUT:
                doPut(req,resp);
                break;
            default:
                break;
        }

        resp.getWriter().println("test");
    }
}
