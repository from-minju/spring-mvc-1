package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //여기서는 단순히 스프링이 아닌 HttpServletRequest가 제공하는 방식으로 요청 파라미터를 조회했다.
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");

        /**
         * 요청 파라미터 조회
         *
         * URL에 쿼리 파라미터로 보내든, html form을 통해 값 전송하든 둘다 형식이 같으므로 똑같은 방법으로 조회가능하다.
         *
         * html파일(리소스)는 /resources/static 아래에 두면 스프링 부트가 자동으로 인식한다.
         * Jar를 사용하면 webapp경로를 사용할 수 없다.
         */
    }


    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * 이게 없으면 지금 @RestController가 아니라 @Controller이기 때문에 리턴값을 모델앤뷰로 받아들여서 ok라는 이름의 모델앤뷰를 찾게됨.
     * @RestController를 써도되고 아님 @ResponseBody를 사용하면됨. 이걸사용하면 리턴문자 그대로 http 응답메시지 바디에 바로 넣어줌.
     * */
    @ResponseBody
    @RequestMapping("/response-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";

    }


    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     * 단, 스프링부트3.2버전부터는 자바 컴파일러에 -parameters 옵션을 넣어주어야 가능. Gradle로 선택한경우에는 가능. */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     * 단, 스프링부트3.2버전부터는 자바 컴파일러에 -parameters 옵션을 넣어주어야 가능. Gradle로 선택한경우에는 가능. */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam.required
     * /request-param-required -> username이 없으므로 예외
     *
     * 주의!
     * /request-param-required?username= -> 빈문자로 통과
     *
     * 주의!
     * /request-param-required
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는 defaultValue 사용) */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, //디폴트가 트루. 필수로 입력해야함.
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam
     * - defaultValue 사용 *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(name = "username", required = true, defaultValue = "guest") String username,
            @RequestParam(name = "age", required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }


    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때 자세히 설명
     *
     * 자동으로 HelloData객체 생성 -> heeloData객체의 프로퍼티 참음 -> 해당 프로퍼티의 setter 호출해 값 입력
     * url요청 파라미터를 자동으로 분석하여 @ModelAttribute한 변수helloData에 setXxx해서 값을 넣어줌.
     * ex) 요청 파라미터 이름이 username이면 setUsername()메서드를 찾아 호출하면서 값을 입력.
     *
     * 파라미터 : 객체에 getUSername(), setUsername()메서드가 있으면, 이 객체는 username이라는 프로퍼티를 가지고 있음.
     */
    //@ModelAttribute 사용 전
    @ResponseBody
    @RequestMapping("/model-attribute-v0")
    public String modelAttributeV0(@RequestParam("username") String username, @RequestParam("age") int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    //@ModelAttribute 사용 후
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }


    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute (ex.@HttpServlet...이런거 제외하고) */
    //@ModelAttribute 까지도 생략가능
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
