package core.helpers;

import org.springframework.stereotype.Component;

@Component
public class TimeHelper {
    public Long next30Day() {
        return System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000;
    }
}
