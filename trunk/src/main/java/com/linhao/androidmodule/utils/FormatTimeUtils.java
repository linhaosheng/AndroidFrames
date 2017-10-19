package com.linhao.androidmodule.utils;

/**
 * 用来计算显示的时间是多久之前的！
 */

public class FormatTimeUtils {

    private FormatTimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis  开始时间戳
     * @return
     */
    public static String getTimeSpanByNow1(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        }else if (span < DateUtils.MIN) {
            return String.format("%d秒前", span /  DateUtils.SEC);
        }else if (span <  DateUtils.HOUR) {
            return String.format("%d分钟前", span /  DateUtils.MIN);
        }else if (span < DateUtils.DAY) {
            return String.format("%d小时前", span /  DateUtils.HOUR);
        }else if (span < DateUtils.WEEK) {
            return String.format("%d天前", span /  DateUtils.DAY);
        }else if (span < DateUtils.MONTH) {
            return String.format("%d周前", span /  DateUtils.WEEK);
        }else if (span < DateUtils.YEAR) {
            return String.format("%d月前", span /  DateUtils.MONTH);
        }else {
            return String.format("%d年前", span /  DateUtils.YEAR);
        }
    }


    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis  开始时间戳
     * @return
     */
    public static String getTimeSpanByNow2(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        long day = span / DateUtils.DAY;
        if (day == 0) {// 今天
            long hour=span/ DateUtils.HOUR;
            if(hour <=4){
                return String.format("凌晨%tR", millis);
            }else if(hour >4 && hour <=6){
                return String.format("早上%tR", millis);
            }else if(hour >6 && hour <=11){
                return String.format("上午%tR", millis);
            }else if(hour >11 && hour <=13){
                return String.format("中午%tR", millis);
            }else if(hour >13 && hour <=18){
                return String.format("下午%tR", millis);
            }else if(hour >18 && hour <=19){
                return String.format("傍晚%tR", millis);
            }else if(hour >19 && hour <=24){
                return String.format("晚上%tR", millis);
            }else{
                return String.format("今天%tR", millis);
            }
        } else if (day == 1) {// 昨天
            return String.format("昨天%tR", millis);
        } else if (day == 2) {// 前天
            return String.format("前天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }


}
