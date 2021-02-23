package org.javaboy.vhr.model;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/12 21:05
 */
public class Meta {
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
