package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * [7-11] 응답 - 정적 리소스, 뷰 템플릿
 *
 * 응답 데이터 3가지 방법 : 1. 정적 리소스, 2. 뷰 템플릿 사용(동적인 html사용), 3. HTTP메시지사용(html이 아닌 json과 같은 데이터를 전달)
 *
 * 2.뷰 템플릿 사용(동적인 html)하는 경우, 아래와 같다.
 */
@Controller
public class ResponseViewController {
    //application.properties에 타임리프 경로 /templates/로 설정되어있음.

    /**
     * ModelAndView 객체 자체를 반환
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello~~");
        return mav;
    }


    /**
     * @Controller에 스트링을 반환하면 이게 바로 뷰의 논리적 이름이 됨. html열림.
     * @ResponseBody하면 그 문자자체가 띄워짐.
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello"; //뷰의 논리적 이름 반환.

    }


    /**
     * V3 : 반환값이 void
     * 요청 url(/response/hello)을 논리 뷰 이름으로도 사용.
     * 명시성이 떨어져 비추.
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello~!");
    }
}
