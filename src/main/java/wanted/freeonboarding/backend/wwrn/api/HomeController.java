package wanted.freeonboarding.backend.wwrn.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "여기는 원티드 프리온보딩 백엔드 - 사전과제 [wanted wantU right now]의 메인 페이지입니다.!";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "로그인 되었!";
    }

    @GetMapping("/logout")
    public String logoutUrl() {
        return "로그아웃 되었!";
    }

}
