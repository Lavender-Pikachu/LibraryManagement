package org.lavender.library.annotation;

import java.lang.annotation.*;

/**
 * a happy start
 *
 * @author thg
 * @since 2025/1/3 17:40
 * @version 1.0.0
 */

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
