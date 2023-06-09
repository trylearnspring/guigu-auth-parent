package password;

import com.atguigu.common.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码加密和验证器
 */
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    /**
     * 转码
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String encrypt = MD5.encrypt(rawPassword.toString());
        return encrypt;
    }

    /**
     * 验证
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean equals = encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
        return equals;
    }
}
