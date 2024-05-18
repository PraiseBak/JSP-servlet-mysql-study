package hello.servlet;


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

@WebServlet(urlPatterns = "/board/view")
public class BoardViewServlet extends HttpServlet {
    private BoardService boardService;

    @Override
    public void init() throws ServletException {
        boardService = new BoardServiceImpl(new MemberServiceImpl());
    }

    /**
     * board/view
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/board_list.jsp").forward(req, resp);
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
