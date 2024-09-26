package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j //로그 찍을 수 있게 하는
@RestController //리턴한 문자 그대로 응답본문에 넣음
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod, //http메서드 조회 - get, post...
                          Locale locale, //언어정보
                          @RequestHeader MultiValueMap<String, String> headerMap, //모든 헤더를 MultiValueMap형식으로 조회
                          @RequestHeader("host") String host_v, //특정 헤더 조회
                          @CookieValue(value = "myCookie", required = false) String cookie) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host_v);
        log.info("myCookie={}", cookie);
        return "ok";

        /**
         * MultiValueMap : 하나의 키에 여러 값을 받을 수 있음. keyA=value1&keyA=value2 -> keyA : [value1,value2]
         *
         * Slf4j : 로그 생성하는 코드를 자동으로 생성해준다. 로그생성코드 생략하고 애노테이션으로 편리하게. log로 편리하게 사용하면됨.
         */
    }

}
