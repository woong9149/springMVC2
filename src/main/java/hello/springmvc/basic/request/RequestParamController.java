package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt((request.getParameter("age")));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2 (
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3 (
            @RequestParam String username,
            @RequestParam int userAge
    ) {
        log.info("username={}, age={}", username, userAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4 (String username, int userAge) {
        log.info("username={}, age={}", username, userAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired (
            @RequestParam(required = true) String username,
            @RequestParam(required = false) int userAge) {

        /**
         *  /request-param?username=
         *  파라미터 이름만 있고 값이 없는 경우 -> 빈 문자로 통과
         */
        log.info("username={}, age={}", username, userAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault (
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int userAge) {

        /**
         *  defaultValue를 지정하면 required 의미가 없어짐
         */
        log.info("username={}, age={}", username, userAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap (@RequestParam Map<String, Object> paramMap) {
        /**
         * 요청 파라미터의 값이 1개가 확실하면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하는 것이 좋다.
         */
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
}
