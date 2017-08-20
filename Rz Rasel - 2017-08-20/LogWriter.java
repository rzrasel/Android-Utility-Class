package com.library;

/**
 * Created by Rz Rasel on 01-Aug-16.
 */
public class LogWriter {
    public static void Log(String argMessage) {
        //|------------------------------------------------------------|
        String buildMessage = "";
        buildMessage = "Message:- " + argMessage + " - "
                + "Class Name:- " + getCallerClassName() + " - "
                + "Method Name:- " + getCallerMethodName() + " - "
                + "Line Number:- " + getCallerLineNumber();
        System.out.println("LOG_PRINT_WRITER:- " + buildMessage);
        //|------------------------------------------------------------|
    }

    public static void Log(String argTag, String argMessage) {
        //|------------------------------------------------------------|
        argTag = argTag.toUpperCase();
        argTag = "LOG_PRINT_WRITER_" + argTag.replaceAll("\\s+", "_");
        String buildMessage = "";
        buildMessage = "Message:- " + argMessage + " - "
                + "Class Name:- " + getCallerClassName() + " - "
                + "Method Name:- " + getCallerMethodName() + " - "
                + "Line Number:- " + getCallerLineNumber();
        System.out.println(argTag + ":- " + buildMessage);
        //|------------------------------------------------------------|
        /*int pid = android.os.Process.myPid();
        File outputFile = new File(Environment.getExternalStorageDirectory() + "/logs/logcat.txt");
        try {
            String command = "logcat | grep " + pid + " > " + outputFile.getAbsolutePath();
            Process p = Runtime.getRuntime().exec("su");
            OutputStream os = p.getOutputStream();
            os.write((command + "\n").getBytes("ASCII"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //|------------------------------------------------------------|
    }

    public static String getCallerClassName() {
        /*String callerClassName = new Exception().getStackTrace()[1].getClassName();
        String calleeClassName = new Exception().getStackTrace()[0].getClassName();*/
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        if (stElements.length >= 4) {
            return Thread.currentThread().getStackTrace()[4].getClassName();
        } else if (stElements.length == 3) {
            return Thread.currentThread().getStackTrace()[3].getClassName();
        } else if (stElements.length == 2) {
            return Thread.currentThread().getStackTrace()[2].getClassName();
        }
        return "";
    }

    public static String getCallerMethodName() {
        /*String callerClassName = new Exception().getStackTrace()[1].getClassName();
        String calleeClassName = new Exception().getStackTrace()[0].getClassName();*/
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        if (stElements.length >= 4) {
            return Thread.currentThread().getStackTrace()[4].getMethodName();
        } else if (stElements.length == 3) {
            return Thread.currentThread().getStackTrace()[3].getMethodName();
        } else if (stElements.length == 2) {
            return Thread.currentThread().getStackTrace()[2].getMethodName();
        }
        return "";
    }

    public static String getCallerLineNumber() {
        /*String callerClassName = new Exception().getStackTrace()[1].getClassName();
        String calleeClassName = new Exception().getStackTrace()[0].getClassName();*/
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        if (stElements.length >= 4) {
            return Thread.currentThread().getStackTrace()[4].getLineNumber() + "";
        } else if (stElements.length == 3) {
            return Thread.currentThread().getStackTrace()[3].getLineNumber() + "";
        } else if (stElements.length == 2) {
            return Thread.currentThread().getStackTrace()[2].getLineNumber() + "";
        }
        return "";
    }

    public static String getCallerClassNameTemp() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            /*if (!ste.getClassName().equals(KDebug.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste.getClassName();
            }*/
        }
        return null;
    }
}