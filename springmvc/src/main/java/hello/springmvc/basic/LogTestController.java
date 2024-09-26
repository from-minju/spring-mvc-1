package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //롬복 lombok이 제공하는 기능
@RestController //문자를 반환하면 바로 스트링이 반환되는... 그냥 컨트롤러는 모델앤뷰 반환해야...
/**
 * 반환값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력.
 */
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //@Slf4j가 이 코드를 대신해준다.

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);

        //로그 레벨 : trace > debug > info > warn > error
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        //올바른 로그 사용법
//        log.trace("trace my log=" + name); //trace레벨이 아닐 때도 일단 +연산이 일어남.낭비.위의 코드는 그냥 파라미터만 넘기므로 연산 안일어남.

        /**
         * 운영 서버에는 보통 info 레벨 까지만. (info, warn, error)
         * println()은 무조건 다 출력됨. 운영 서버에도 필요없는 수많은  로그 다 남는거임.
         *
         * 보통 개발 서버는 debug, 운영 서버는 info 출력
         */

        return "ok";

    }
}
