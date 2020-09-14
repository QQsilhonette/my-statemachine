package com.org.example.my.statemachine;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:25 下午
 * @description：调试日志
 */
public class Debugger {

    private static boolean isDebugOn = false;

    public static void debug(String message){
        if(isDebugOn){
            System.out.println(message);
        }
    }

    public static void enableDebug(){
        isDebugOn = true;
    }
}
