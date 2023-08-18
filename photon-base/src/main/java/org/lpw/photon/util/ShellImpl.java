package org.lpw.photon.util;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("photon.util.shell")
public class ShellImpl implements Shell {
    @Inject
    private Io io;
    @Inject
    private Logger logger;

    @Override
    public String run(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            int code = process.waitFor();
            String string = "---------------- input ----------------\n" + io.readAsString(process.getInputStream());
            string += "\n---------------- error ----------------\n" + io.readAsString(process.getErrorStream());
            if (logger.isDebugEnable())
                logger.debug("执行Shell命令[{}:{}:{}]", command, code, string);

            return string;
        } catch (Throwable throwable) {
            logger.warn(throwable, "执行Shell命令[{}]时发生异常！", command);

            return null;
        }
    }
}
