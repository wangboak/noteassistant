package com.wangboak.noteassistant.util;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 随机数工具类。
 * @description:
 * @author: wangboak
 **/
public class RandomUtil {

    final static Random random = new Random(System.nanoTime() * 37);

    private final static char[] arr = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private final static AtomicLong inc = new AtomicLong(1);

    private final static long offset_time = 1431860854000L; //减去这个值获取的最终位数 刚好是从19位长度开始。大约是7年7个月左右

    //19位ID的 机器码。
    private final static long worker_id = 1;

    /**
     * 获取标准的UUID字符串。
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成随机的 [0， max] 的整数。
     * @param max
     * @return
     */
    public static int genInt(int max) {
        int in = random.nextInt();
        if (in < 0) {
            in = Math.abs(in);
        }
        return in % (max + 1);
    }

    /**
     * 生成19位固定长度的ID。随时间 递增。
     * 41位时间戳 + 10位机器码 + 12位自增数 。
     * 每一毫秒中最多生成4096个ID。
     * 暂不考虑 润秒及时钟回拨的情况。
     * @return
     */
    public static long genId() {
        long time = System.currentTimeMillis();//

        long index = inc.getAndIncrement() & 0xFFF;//取后12位
        //如果index过了0，则意味着4096个数字用尽，开始了一轮新的循环，为了防止单一毫秒内获取的id重复，需要时间消耗到下一毫秒
        if (index == 0) {
            synchronized (RandomUtil.class) {
                long nextTime = System.currentTimeMillis();
                while (time == nextTime) {
                    nextTime = System.currentTimeMillis();
                }
                time = nextTime;
            }
        }

        time = (time - offset_time) << 22;
        return (time | (worker_id << 12) | index);
    }
}
