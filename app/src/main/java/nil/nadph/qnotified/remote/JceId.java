package nil.nadph.qnotified.remote;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD, PARAMETER, FIELD})
public @interface JceId {
    int value();

    boolean required() default true;
}
