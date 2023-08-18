package org.lpw.photon.util;

public interface Shell {
    /**
     * 执行Shell命令。
     *
     * @param command 命令。
     * @return 执行结果，失败则返回null。
     */
    String run(String command);
}
