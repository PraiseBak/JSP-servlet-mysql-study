package hello.servlet;


import hello.entity.Board;
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
import java.util.List;

@WebServlet(urlPatterns = "/")
public class MainViewServlet extends HttpServlet {
    private BoardService boardService;

    @Override
    public void init() throws ServletException {
        boardService = new BoardServiceImpl(new MemberServiceImpl());
    }

    /**
     * board/add
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("redirect");
        resp.sendRedirect("/board");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        HttpMethod httpMethod = HttpMethod.valueOf(method);

        switch (httpMethod) {
            case GET:
                doGet(req,resp);
                break;
            default:
                resp.sendRedirect("/error");
                break;
        }
    }
}
