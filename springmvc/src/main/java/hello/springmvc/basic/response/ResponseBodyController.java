package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
//RestController = controller + responsebody

public class ResponseBodyController {

    /**
     * V1
     * 초반에 직접 서블릿 다룰때처럼 HttpServletResponse객체를 통해 바디에 직접 메시지 전달
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException{
        response.getWriter().write("ok");
    }


    /**
     * V2
     * HttpEntity를 상속받은 ResponseEntity (추가로 Http Status 설정 가능)
     */
    //ResponseEntity는 status지정 가능하지만, @ResponseBody인 경우에는 지정못하므로 @ResponseStatus
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    /**
     * V3
     * @ResponseBody 사용 -> 뷰를 사용하지 않고 메시지 컨버터를 통해 바로 직접 입력.
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }


    /**
     * JSON으로 응답 V1
     * ResponseEntity를 반환. HTTP 메시지 컨버터를 통해 JSON형식으로 변환되어서 반환됨.
     * 응답 코드 동적으로 변경 가능.
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }


    /**
     * JSON으로 응답 V2
     * @ResponseBody 애노테이션 + 리턴값은 자바객체 (단점: 응답 코드를 동적으로 변경 불가. 코드 내부가 아닌 애노테이션에서 박아놓기 때문.)
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }

}
