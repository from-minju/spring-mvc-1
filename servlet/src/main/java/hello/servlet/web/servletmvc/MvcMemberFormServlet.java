package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
* 항상 Controller을 거치고 View로 가도록 해야 함.
* */
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response); //다른 서블릿이나 JSP로 이동할 수 있는 기능. 서부 내부에서 다시 호출 발생

    }

    /*
    * dispatcher.forward() : 서버 내부에서 다시 "호출"이 발생
    * redirect() : 클라이언트에 다시 갔다가 웹 브라우저에 다시 응답 / 웹 브라우저가 다시 요청 /
    * redirect가 아니라 서버 내부에서 한번 더 호출하는 거임. 서버끼리 내부적으로.
    *
    * /WEB-INF : 룰임. 정해진 규격?. 외부에서 마음대로 불려지지 않게 하기 위해.
    *            이전 jsp실습때는 그냥 localhost:8080/jsp/members/...로 넣으면 됐음.띄워졌음.
    *            하지만 localhost:8080/WEB-INF/views/...로 부르면 Error페이지 뜸. 불려지지 않음.
    *            하지만 이젠 항상 컨트롤러를 거쳐서 불려지기를 원함. 외부에서 직접적으로 부를수없음.
    * */
}
