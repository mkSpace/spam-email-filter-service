package hello.spamemailfilterservice.controller;

import hello.spamemailfilterservice.config.auth.PrincipalDetails;
import hello.spamemailfilterservice.dto.EmailRequestDto;
import hello.spamemailfilterservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/emails")
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        model.addAttribute("emails", emailService.listEmails(pageable, principalDetails.getUsername()));

        return "home";
    }

    @GetMapping("/emails/write")
    public String writeForm() {
        return "emails/writeForm";
    }

    @PostMapping("/emails/write")
    public String write(@AuthenticationPrincipal PrincipalDetails principalDetails, EmailRequestDto emailDto) {
        emailService.writeEmail(emailDto, principalDetails.getUsername());
        return "redirect:/";
    }
}

