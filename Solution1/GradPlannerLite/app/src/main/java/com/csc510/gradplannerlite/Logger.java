package com.csc510.gradplannerlite;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jordy on 2/28/2016.
 */
public class Logger {
    public static void Log(Context context, String tag, String msg) {
        i(context, tag, msg);
    }

    public static void i(Context context, String logMessageTag, String logMessage) {
        if (!Log.isLoggable(logMessageTag, Log.ERROR))
            return;

        int logResult = Log.i(logMessageTag, logMessage);
        if (logResult > 0)
            logToFile(context, logMessageTag, logMessage);
    }

    private static String getDateTimeStamp() {
        Date dateNow = Calendar.getInstance().getTime();
        return (DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CANADA_FRENCH).format(dateNow));
    }

    private static void logToFile(Context context, String logMessageTag, String logMessage) {
        try {
            File logFile = new File(Environment.getExternalStorageDirectory(), "GradPlannerLiteLog.txt");
            if (!logFile.exists())
                logFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(String.format("%1s [%2s]:%3s\r\n", getDateTimeStamp(), logMessageTag, logMessage));
            writer.close();
            MediaScannerConnection.scanFile(context,
                    new String[]{logFile.toString()},
                    null,
                    null);

        } catch (IOException e) {
            Log.e("Logger", "Unable to log exception to file.");
        }
    }
}
