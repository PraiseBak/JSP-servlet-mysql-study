package hello.servlet;


import hello.dto.AddBoardDto;
import hello.helper.HttpMethod;
import hello.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 */
@WebServlet(urlPatterns = "/member")
public class MemberServlet extends HttpServlet {
    private static final int MAX_INACTIVE_INTERNAL = 30 * 60;
    private MemberService memberService;
    private UserSessionManager userSessionManager;

    @Override
    public void init() throws ServletException {
        this.memberService = new MemberServiceImpl();
        this.userSessionManager = UserSessionManager.getInstance();
    }

    /**
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     *
     * @throws ServletException
     * @throws IOException
     *
     * 회원가입
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mode = req.getParameter("mode");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(mode == null || mode.equals("signup")){
            try{
                memberService.save(username,password);
                resp.sendRedirect("/");
            }catch (IllegalArgumentException e){
                resp.getWriter().println(e.getMessage());
                resp.sendRedirect("/error");
            }
        }else{
            if (memberService.authenticate(username, password)) {
                // 로그인 성공 시, 사용자 정보를 캐시에 저장
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(MAX_INACTIVE_INTERNAL);
                userSessionManager.addUserSession(session.getId(),username);
                resp.sendRedirect("/");
            } else {
                resp.getWriter().println("Login failed");
            }
        }

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 파라미터 가져오는 부분
         */
        String mode = req.getParameter("mode");
        System.out.println(mode);
        if(mode == null || mode.equals("login")){
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }else{
            System.out.println("signup`");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        HttpMethod httpMethod = HttpMethod.valueOf(method);

        switch (httpMethod) {
            case GET:
                doGet(req,resp);
                break;
            case POST:
                doPost(req,resp);
                break;
            default:
                break;
        }
    }
}
