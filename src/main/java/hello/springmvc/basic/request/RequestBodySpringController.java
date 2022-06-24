package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodySpringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        /**
         *  스프링MVC가 지원하는 파라미터
         *  - InputStream(Reader): HTTP 요청 메세지 바디의 내용을 직접 조회
         *  - OutPutStream(Writer): HTTP 응답 메세지의 바디에 직접 결과 출력
         */
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        /**
         *  스프링MVC가 지원하는 파라미터
         *  - HttpEntity: HTTP header, body 정보를 편리하게 조회
         *      - 메세지 바디 정보를 직접 조회
         *      - 요청 파라미터를 조회하는 기능과 관계 없음(@RequestParam, @ModelAttribute 등)
         *  - HttpEntity는 응답에도 사용 가능
         *      - 메세지 바디 정보 직접 반환
         *      - 헤더 정보 포함 가능
         *      - view 조회 안함
         *
         *  - HttpEntity를 상속 받은 다음 객체들도 같은 기능을 제공한다
         *      - RequestEntity
         *          - HttpMethod, url 정보가 추가, 요청에서 사용
         *      - ResponseEntity
         *          - HTTP 상태 코드 설정 가능, 응답에서 사용
         *          - return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
         */
        String messageBody = httpEntity.getBody();
//        HttpHeaders headers = httpEntity.getHeaders(); header 정보도 조회 가능
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV4(@RequestBody String messageBody)  {

        /**
         * @RequestBody
         * @RequestBody를 사용하면 HTTP 메세지 바디 정보를 편리하게 조회할 수 있다.
         * 참고로 헤더 정보가 필요하다면 HttpEntity를 사용하거나 @RequestHeader를 사용하면 된다.
         * 메세지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam, @ModelAttribute 와는 전혀 관계가 없다.
         *
         *  요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
         *  HTTP 메세지 바디를 직접 조회하는 기능: @RequestBody
         *
         * @ResponseBody
         * @ResponseBody를 사용하면 응답 결과를 HTTP 메세지 바디에 직접 담아서 전달할 수 있다.
         * view 사용 안함
         */
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

}
