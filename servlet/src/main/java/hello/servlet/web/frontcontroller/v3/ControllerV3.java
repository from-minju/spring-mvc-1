package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    //servlet 기술이 들어가지 않음. servlet에 종속적이지 않음.
    //이전에 HttpServletRequest로 파라미터를 얻었다면, 이제는 FrontController가 paramMap에 담아 호출.
    ModelView process(Map<String, String> paramMap);
}
