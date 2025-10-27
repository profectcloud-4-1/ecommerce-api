package profect.group1.goormdotcom.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class PasswordService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 암호화
    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // 평문과 암호 비교
    public boolean isMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean validate(String password) {
        // 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 포함.

        if (password.length() < 8 || password.length() > 15) {
            return false;}

        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}