package org.lpw.photon.scheduler;

/**
 * 每分钟执行定时器任务，每分钟执行一次任务。
 *
 * @author lpw
 */
public interface MinuteJob {
    /**
     * 执行每分钟任务。
     */
    void executeMinuteJob();
}
