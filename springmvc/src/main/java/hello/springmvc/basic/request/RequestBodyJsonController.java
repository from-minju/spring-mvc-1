package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1. HTTP 요청의 바디 데이터를 문자열로 변환, 읽어옴.
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        // 2. 자바 객체로 변환
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        response.getWriter().write("ok");
    }


    /**
     * @RequestBody
     * v1의 1번 과정을 대체함.
     * HTTP 요청 메시지(바디)에서 문자열(데이터)를 꺼내옴, 읽어옴.
     * HttpMessageConverter(본문을 특정타입으로변환) 사용 -> StringHttpMessageConverter(주로 text/plain 타입의 요청을 문자열로 변환하는데 사용) 적용 *
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException { // 1. 바디 본문을 문자열로 변환

        // 2. 자바 객체로 변환
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    /**
     * @RequestBody 생략 불가능(생략했을 때 단순 타입(String, Int...)이 아닌 경우 @ModelAttribute 가 적용되어 버리므로)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) { //1. 바디 본문을 문자열로 변환 + 2. 자바 객체로 변환 (HelloData객체로)
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    /**
     * V5 : 반환값을 객체(HelloData)로도 가능.
     * @ResponseBody를 사용하면 객체를 그대로 HTTP 바디에 넣을 수 있다.
     *
     * @RequestBody로 문자열을 자바 객체로 변환 -> @ResponseBody로 자바객체 그 자체를 메시지 바디에 넣어 응답할 수 있음.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }

}
