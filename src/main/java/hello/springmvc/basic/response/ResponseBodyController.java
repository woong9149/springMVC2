package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@ResponseBody
@RestController /* @Controller + @ResponseBody */
public class ResponseBodyController {
    /**
     * @Controller 대신에 @RestController 애노테이션을 사용하면, 해당 컨트롤러에 모두 @ResponseBody가
     * 적용되는 효과가 있다.
     * 따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메세지 바디에 직접 데이터를 입력한다.
     * 이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

//    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        /**
         * @ResponseBody를 사용하면 view를 사용하지 않고, HTTP 메세지 컨버터를 통해서 HTTP 메세지를 직접 입력할 수 있다.
         * ResponseEntity도 동일한 방식으로 동작한다.
         */
        return "ok";
    }

    @GetMapping("response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        /**
         * ResponseEntity를 반환한다. HTTP 메세지 컨버터를 통해서 JSON형식으로 변환되어서 반환된다.
         */
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
    @GetMapping("response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        /**
         * ResponseEntity는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody를 사용하면 이런 것을 설정하기 까다롭다.
         * @ResponseStatus(HttpStatus.OK) 애노테이션을 사용하면 응답 코드도 설정할 수 있다.
         *
         * 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다.
         * 동적으로 변경하려면 ResponseEntity 를 사용하면 된다.
         */
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }

}
