package com.itheima;/**
 * WeekEnum
 * 
 * @author dingtao
 * @date 2024/7/26 9:59
 */

public class WeekClass{
    public enum WeekEnum {

        Monday(1,"星期一"),

        Tuesday(2,"星期二"),

        Wendesday(3,"星期三");

        private int code;
        private String description;

        WeekEnum(){

        }

        WeekEnum(int code, String description){
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum MonthEnum{
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    }
}


