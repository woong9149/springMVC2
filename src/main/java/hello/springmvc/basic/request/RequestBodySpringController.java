package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;

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
         */
        String messageBody = httpEntity.getBody();
//        HttpHeaders headers = httpEntity.getHeaders(); header 정보도 조회 가능
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }
}
