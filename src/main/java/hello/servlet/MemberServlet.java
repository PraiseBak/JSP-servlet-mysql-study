package hello.servlet;


import hello.dto.AddBoardDto;
import hello.helper.HttpMethod;
import hello.service.BoardService;
import hello.service.BoardServiceImpl;
import hello.service.MemberService;
import hello.service.MemberServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@WebServlet(urlPatterns = "/login")
public class MemberServlet extends HttpServlet {
    private MemberService memberService;

    // 사용자 정보를 저장할 캐시 맵
    private Map<String, String> userCache = new HashMap<>();

    @Override
    public void init() throws ServletException {
        this.memberService = new MemberServiceImpl();
    }

    /**
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     *
     * @throws ServletException
     * @throws IOException
     *
     * login
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 파라미터 가져오는 부분
         */
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        //로그인 구현해야함..
        AddBoardDto addBoardDto = new AddBoardDto(title,content);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (authenticate(username, password)) {
            // 로그인 성공 시, 사용자 정보를 캐시에 저장
            userCache.put(username, password);
            resp.getWriter().println("Login successful");
        } else {
            resp.getWriter().println("Login failed");
        }

        memberService.save(username,password);
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        // 캐시에서 사용자 정보를 가져옴
        String cachedPassword = userCache.get(username);

        if (cachedPassword != null) {
            response.getWriter().println("Cached user password: " + cachedPassword);
        } else {
            response.getWriter().println("User not found in cache");
        }
    }

    // 사용자 인증을 확인하는 메서드 (예제용)
    private boolean authenticate(String username, String password) {
        // 실제로는 데이터베이스나 다른 인증 서비스를 사용하여 인증을 수행해야 함
        // 여기서는 간단히 "admin"과 "password"로 하드코딩된 값으로만 인증
        return "admin".equals(username) && "password".equals(password);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        HttpMethod httpMethod = HttpMethod.valueOf(method);

        switch (httpMethod) {
            case POST:
                doPost(req,resp);
                break;
            default:
                break;
        }

    }
}
