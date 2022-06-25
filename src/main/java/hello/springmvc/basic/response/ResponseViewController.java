package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    /*
     * String을 반환하는 경우 - View or HTTP 메세지
     *  @ResponseBody가 없으면 'response/hello'로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.
     *  @ResponseBody가 있으면 뷰 리졸보를 실행하지 않고, HTTP 메세지 바디에 직접 'response/hello' 라는 문자가 입력된다.
     *
     * Void를 반환하는 경우 (명시성이 너무 떨어지고, 요청 URL과 논리 뷰의 이름이 딱 맞아 떨어지는 경우가 많이 없어서 권장되지 않는다.)
     * - @Controller를 사용하고, HttpServletResponse, OutputStream(Writer) 같은 HTTP 메세지 바디를 처리하는 파라미터가 없으면
     *  요청 URL을 참고해서 논리 뷰 이름으로 사용
     *      - 요청 URL: /response/hello
     *      - 실행: templates/response/hello.html
     *
     * HTTP 메세지
     * @ResponseBody, HttpEntity를 사용하면, 뷰 템플릿을 사용하는 것이 아니라 HTTP 메세지 바디에 직접 응답 데이터를 출력할 수 있다.
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data","hello!");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /* 권장되지 않는 방법 */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }

}
