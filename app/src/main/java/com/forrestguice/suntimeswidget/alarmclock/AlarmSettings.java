/**
    Copyright (C) 2018 Forrest Guice
    This file is part of SuntimesWidget.

    SuntimesWidget is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SuntimesWidget is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SuntimesWidget.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.forrestguice.suntimeswidget.alarmclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * AlarmSettings
 */
public class AlarmSettings
{
    public static final String PREF_KEY_ALARM_HARDAREBUTTON_ACTION = "app_alarms_hardwarebutton_action";
    public static final String PREF_DEF_ALARM_HARDAREBUTTON_ACTION = AlarmNotifications.ACTION_SILENT;

    public static final String PREF_KEY_ALARM_SILENCEAFTER = "app_alarms_silenceafter";
    public static final int PREF_DEF_ALARM_SILENCEAFTER = 1000 * 10; //10 * 60 * 1000;   // 10 min

    public static final String PREF_KEY_ALARM_TIMEOUT = "app_alarms_timeoutMillis";
    public static final int PREF_DEF_ALARM_TIMEOUT = 1000 * 60; //1000 * 60 * 10;  // 15 min

    public static final String PREF_KEY_ALARM_SNOOZE = "app_alarms_snoozeMillis";
    public static final int PREF_DEF_ALARM_SNOOZE = 1000 * 60; //1000 * 60 * 10;  // 10 min

    public static final String PREF_KEY_ALARM_UPCOMING = "app_alarms_upcomingMillis";
    public static final int PREF_DEF_ALARM_UPCOMING = 1000 * 60;  //1000 * 60 * 60 * 12;  // 12 hours   // TODO: expose through PrefActivity

    public static String loadPrefOnHardwareButtons(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREF_KEY_ALARM_HARDAREBUTTON_ACTION, PREF_DEF_ALARM_HARDAREBUTTON_ACTION);
    }

    public static long loadPrefAlarmSilenceAfter(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_KEY_ALARM_SILENCEAFTER, PREF_DEF_ALARM_SILENCEAFTER);
    }

    public static long loadPrefAlarmTimeout(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_KEY_ALARM_TIMEOUT, PREF_DEF_ALARM_TIMEOUT);
    }

    public static long loadPrefAlarmUpcoming(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_KEY_ALARM_UPCOMING, PREF_DEF_ALARM_UPCOMING);
    }

    public static long loadPrefAlarmSnooze(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_KEY_ALARM_SNOOZE, PREF_DEF_ALARM_SNOOZE);
    }

    public static long[] loadDefaultVibratePattern(Context context, AlarmClockItem.AlarmType type)
    {
        switch (type)
        {
            case NOTIFICATION:
            case ALARM:
            default:                    // TODO
                return new long[] {0, 400, 200, 400, 800};   // 0 immediate start, 400ms buzz, 200ms break, 400ms buzz, 800ms break [repeat]
        }
    }

}
