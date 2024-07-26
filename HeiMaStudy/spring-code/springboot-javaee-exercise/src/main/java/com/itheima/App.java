package com.itheima;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    static Logger logger = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        WeekClass.WeekEnum weekEnum = WeekClass.WeekEnum.Monday;
        WeekClass.MonthEnum monthEnum = WeekClass.MonthEnum.January;
        logger.info(String.valueOf(monthEnum.ordinal()));
        logger.info(weekEnum.getDescription());
    }
}
