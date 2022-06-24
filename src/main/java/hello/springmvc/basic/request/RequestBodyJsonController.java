package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData){
    /**
     * - HttpEntity, @RequestBody 를 사용하면 HTTP 메세지 컨버터가 HTTP 메세지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
     * - HTTP 메세지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해준다.
     */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody는 생략 불가능
     *  스프링은 @ModelAttribute, @RequestParam 생략시 다음과 같은 규칙을 적용한다.
     *  - String, int, Integer 같은 단순 타입 => @RequestParam
     *  - 나머지 => @ModelAttribute (argument resolver로 지정해둔 타입 외)
     *  따라서 생략하면 HTTP 메세지 바디가 아니라 요청 파라미터를 처리하게 된다.
     */

    /* HttpEntity 사용 */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity){
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data){
        /**
         * @ResponseBody
         * 응답의 경우에도 @ResponseBody를 사용하면 해당 객체를 HTTP 메세지 바디에 직접 넣어줄 수 있다.
         * 물론 이 경우에도 HttpEntity를 사용해도 된다.
         *
         * @RequestBody 요청
         *     - JSON 요청 -> HTTP 메세지 컨버터 -> 객체
         * @ReponseBody 응답
         *     - 객체 -> HTTP 메세지 컨버터 -> JSON 응답
         */
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }
}
