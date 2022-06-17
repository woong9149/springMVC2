package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController
 * - @Controller는 반환 값이 String이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 렌더링 된다.
 * - @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력 한다.
 *      따라서 실행 결과로 ok 메시지를 받을 수 있다.
 *
 *  <로그 사용시 장점>
 *  - 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
 *  - 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영 서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있다.
 *  - 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는
 *      일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.
 *  - 성능도 일반 System.out 보다 좋다.(내부 버퍼링, 멀티 쓰레드 등) 그래서 실무에서는 꼭 로그를 사용해야 한다.
 *
 * <log.trace("trace log={}", name)와 log.trace("trace log={}" + name)의 차이>
 *  - log.trace("trace log={}" + name)는 "trace log={}" + name 의 연산이 이미 이루어지고, 그 값을 가지고 있기 때문에
 *      로그가 출력만 안될 뿐 cpu까지 사용이 된다.
 *  - log.trace("trace log={}", name) 는 함수에 파라미터를 넘기는 것이기 때문에 아에 실행이 되지 않는다.
 *      올바른 로그의 사용.
 */
@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());  @Slf4j가 이걸 자동으로 해줌

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log = {}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
