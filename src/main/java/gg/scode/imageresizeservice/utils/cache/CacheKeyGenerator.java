package gg.scode.imageresizeservice.utils.cache;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class CacheKeyGenerator  implements KeyGenerator {

    private final HttpServletRequest req;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return req.getParameter("key");
    }

}
