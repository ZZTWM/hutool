package cn.how2j;
 
import cn.hutool.cron.CronUtil;
 
public class TestCron01 {
 
    public static void main(String[] args) {
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}