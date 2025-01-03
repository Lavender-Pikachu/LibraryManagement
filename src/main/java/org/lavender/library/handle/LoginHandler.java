package org.lavender.library.handle;

import org.lavender.library.utils.ResultVo;

/**
 * <p>
 * a happy start
 * </p>
 *
 * @author thg
 * @version 1.0.0
 * @since 2024/12/27  21:53
 */
public interface LoginHandler {
    ResultVo handleLogin(String username, String password);
}
