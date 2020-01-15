/**
    Copyright (C) 2019 Forrest Guice
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

package com.forrestguice.suntimeswidget.settings;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.SuntimesActivity;
import com.forrestguice.suntimeswidget.SuntimesUtils;
import com.forrestguice.suntimeswidget.calculator.SuntimesData;

import java.util.Set;
import java.util.TreeSet;

/**
 * WidgetActions
 */
public class WidgetActions
{
    public static final String TAG = "WidgetActions";

    public static final String PREFS_WIDGETS = WidgetSettings.PREFS_WIDGET;                  // Widget related actions are stored with WidgetSettings (where the actionID is either null or 0),
    public static final String PREFS_ACTIONS = "com.forrestguice.suntimeswidget.actions";    // while the action collection is stored in separate .actions file.

    public static final String PREF_PREFIX_KEY = WidgetSettings.PREF_PREFIX_KEY;
    public static final String PREF_PREFIX_KEY_ACTION = "_action_";

    public static final String PREF_KEY_ACTION_LAUNCH_TITLE = "title";
    public static String PREF_DEF_ACTION_LAUNCH_TITLE = "Suntimes";

    public static final String PREF_KEY_ACTION_LAUNCH_DESC = "desc";
    public static String PREF_DEF_ACTION_LAUNCH_DESC = "";

    public static final String PREF_KEY_ACTION_LAUNCH_COLOR = "color";
    public static int PREF_DEF_ACTION_LAUNCH_COLOR = Color.WHITE;

    public static final String PREF_KEY_ACTION_LAUNCH = "launch";
    public static final String PREF_DEF_ACTION_LAUNCH = "com.forrestguice.suntimeswidget.SuntimesActivity";

    public static final String PREF_KEY_ACTION_LAUNCH_ACTION = "action";
    public static final String PREF_DEF_ACTION_LAUNCH_ACTION = "";

    public static final String PREF_KEY_ACTION_LAUNCH_EXTRAS = "extras";
    public static final String PREF_DEF_ACTION_LAUNCH_EXTRAS = "";

    public static final String PREF_KEY_ACTION_LAUNCH_DATA = "data";
    public static final String PREF_DEF_ACTION_LAUNCH_DATA = "";

    public static final String PREF_KEY_ACTION_LAUNCH_DATATYPE = "datatype";
    public static final String PREF_DEF_ACTION_LAUNCH_DATATYPE = "";

    public static final String PREF_KEY_ACTION_LAUNCH_TYPE = "type";
    public static final LaunchType PREF_DEF_ACTION_LAUNCH_TYPE = LaunchType.ACTIVITY;

    public static final String LAUNCH_TYPE_ACTIVITY = "ACTIVITY";
    public static final String LAUNCH_TYPE_BROADCAST = "BROADCAST";
    public static final String LAUNCH_TYPE_SERVICE = "SERVICE";

    public static final String PREF_KEY_ACTION_LAUNCH_LIST = "list";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * LaunchType
     */
    public static enum LaunchType
    {
        ACTIVITY("Activity"),
        BROADCAST("Broadcast"),
        SERVICE("Service");

        private LaunchType(String displayString)
        {
            this.displayString = displayString;
        }

        private String displayString;
        public String getDisplayString()
        {
            return displayString;
        }
        public void setDisplayString(String value)
        {
            displayString = value;
        }
        public static void initDisplayStrings(Context context)
        {
            ACTIVITY.setDisplayString(context.getString(R.string.launchType_activity));
            BROADCAST.setDisplayString(context.getString(R.string.launchType_broadcast));
            SERVICE.setDisplayString(context.getString(R.string.launchType_service));
        }
        public String toString()
        {
            return displayString;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public static void saveActionLaunchPref(Context context, @Nullable String titleString, @Nullable String descString, @Nullable Integer color, int appWidgetId, @Nullable String id, @Nullable String launchString, @Nullable String type, @Nullable String action, @Nullable String dataString, @Nullable String mimeType, @Nullable String extrasString) {
        saveActionLaunchPref(context, titleString, descString, color, appWidgetId, id, launchString, type, action, dataString, mimeType, extrasString, true);
    }

    public static void saveActionLaunchPref(Context context, @Nullable String titleString, @Nullable String descString, @Nullable Integer color, int appWidgetId, @Nullable String id, @Nullable String launchString, @Nullable String type, @Nullable String action, @Nullable String dataString, @Nullable String mimeType, @Nullable String extrasString, boolean listed)
    {
        boolean hasID = true;
        if (id == null) {
            id = "0";
            hasID = false;
        }
        if (action != null && action.trim().isEmpty()) {
            action = null;
        }
        if (dataString != null && dataString.trim().isEmpty()) {
            dataString = null;
        }
        if (mimeType != null && mimeType.trim().isEmpty()) {
            mimeType = null;
        }
        if (extrasString != null && extrasString.trim().isEmpty()) {
            extrasString = null;
        }

        SharedPreferences.Editor prefs = context.getSharedPreferences(getPrefsId(appWidgetId, id), 0).edit();
        String prefs_prefix0 = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + id + "_";

        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH, (launchString != null ? launchString : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_TYPE, (type != null ? type : LAUNCH_TYPE_ACTIVITY));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_ACTION, (action != null ? action : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DATA, (dataString != null ? dataString : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DATATYPE, (mimeType != null ? mimeType : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_EXTRAS, (extrasString != null ? extrasString : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_TITLE, (titleString != null ? titleString : ""));
        prefs.putString(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DESC, (descString != null ? descString : ""));
        prefs.putInt(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_COLOR, (color != null ? color : PREF_DEF_ACTION_LAUNCH_COLOR));
        prefs.apply();

        if (hasID && listed)
        {
            Set<String> actionList = loadActionLaunchList(context, 0);
            actionList.add(id);
            prefs.putStringSet(PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + PREF_KEY_ACTION_LAUNCH_LIST, actionList);
            prefs.apply();
        }
    }
    public static Set<String> loadActionLaunchList(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_ACTIONS, 0);
        String listKey = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + PREF_KEY_ACTION_LAUNCH_LIST;
        Set<String> actionList = prefs.getStringSet(listKey, null);
        return (actionList != null) ? actionList : new TreeSet<String>();
    }
    public static String loadActionLaunchPref(Context context, int appWidgetId, @Nullable String id, @Nullable String key)
    {
        if (id == null) {
            id = "0";
        }

        SharedPreferences prefs = context.getSharedPreferences(getPrefsId(appWidgetId, id), 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + id + "_";

        if (key == null || key.isEmpty())
        {
            return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH, PREF_DEF_ACTION_LAUNCH);

        } else {
            switch (key)
            {
                case PREF_KEY_ACTION_LAUNCH_TYPE:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_TYPE, LAUNCH_TYPE_ACTIVITY);

                case PREF_KEY_ACTION_LAUNCH_ACTION:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_ACTION, "");

                case PREF_KEY_ACTION_LAUNCH_DATA:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_DATA, "");

                case PREF_KEY_ACTION_LAUNCH_DATATYPE:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_DATATYPE, "");

                case PREF_KEY_ACTION_LAUNCH_EXTRAS:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_EXTRAS, "");

                case PREF_KEY_ACTION_LAUNCH_TITLE:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_TITLE, PREF_DEF_ACTION_LAUNCH_TITLE);

                case PREF_KEY_ACTION_LAUNCH_DESC:
                    return prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH_DESC, PREF_DEF_ACTION_LAUNCH_DESC);

                case PREF_KEY_ACTION_LAUNCH_COLOR:
                    return Integer.toString(prefs.getInt(prefs_prefix + PREF_KEY_ACTION_LAUNCH_COLOR, PREF_DEF_ACTION_LAUNCH_COLOR));
            }
            return null;
        }
    }
    public static void deleteActionLaunchPref(Context context, int appWidgetId, @Nullable String id)
    {
        if (id == null) {
            id = "0";
        }

        SharedPreferences.Editor prefs = context.getSharedPreferences(getPrefsId(appWidgetId, id), 0).edit();
        String prefs_prefix0 = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + id + "_";
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH );
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_TYPE);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_ACTION);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DATA);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DATATYPE);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_EXTRAS);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_TITLE);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_DESC);
        prefs.remove(prefs_prefix0 + PREF_KEY_ACTION_LAUNCH_COLOR);
        prefs.apply();

        Set<String> actionList = loadActionLaunchList(context, 0);
        actionList.remove(id);
        prefs.putStringSet(PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + PREF_KEY_ACTION_LAUNCH_LIST, actionList);
        prefs.commit();
    }
    public static boolean hasActionLaunchPref(Context context, int appWidgetId, @NonNull String id)
    {
        SharedPreferences prefs = context.getSharedPreferences(getPrefsId(appWidgetId, id), 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION + PREF_KEY_ACTION_LAUNCH + "_" + id + "_";
        return prefs.contains(prefs_prefix + PREF_KEY_ACTION_LAUNCH_TYPE);
    }

    public static String getPrefsId(int appWidgetId, String actionId)
    {
        return (((actionId == null) || actionId.equals("0")) ? PREFS_WIDGETS : PREFS_ACTIONS);
    }

    /**
     * startIntent
     */
    public static void startIntent(@NonNull Context context, int appWidgetId, String id, @Nullable SuntimesData data, Class fallbackLaunchClass, @Nullable Integer flags)
    {
        Intent launchIntent = WidgetActions.createIntent(context, appWidgetId, id, data, fallbackLaunchClass);
        if (flags != null) {
            launchIntent.setFlags(flags);
        }
        String launchType = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, WidgetActions.PREF_KEY_ACTION_LAUNCH_TYPE);
        WidgetActions.startIntent(context, launchIntent, launchType);
    }
    public static void startIntent(@NonNull Context context, @NonNull Intent launchIntent, @Nullable String launchType)
    {
        if (launchType != null)
        {
            try {
                Log.i(TAG, "startIntent :: " + launchType + " :: " + launchIntent.toString());
                switch (launchType)
                {
                    case WidgetActions.LAUNCH_TYPE_BROADCAST:
                        context.sendBroadcast(launchIntent);
                        break;

                    case WidgetActions.LAUNCH_TYPE_SERVICE:
                        context.startService(launchIntent);
                        break;

                    case WidgetActions.LAUNCH_TYPE_ACTIVITY:
                    default:
                        context.startActivity(launchIntent);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, "startIntent: unable to start + " + launchType + " :: " + e);
            }

        } else {
            Log.i(TAG, "startIntent :: ACTIVITY :: " + launchIntent.toString());
            context.startActivity(launchIntent);
        }
    }

    /**
     * createIntent
     */
    public static Intent createIntent(Context context, int appWidgetId, String id, @Nullable SuntimesData data, Class fallbackLaunchClass)
    {
        Intent launchIntent;
        String launchClassName = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, null);
        String actionString = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, WidgetActions.PREF_KEY_ACTION_LAUNCH_ACTION);
        String dataString = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, WidgetActions.PREF_KEY_ACTION_LAUNCH_DATA);
        String mimeType = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, WidgetActions.PREF_KEY_ACTION_LAUNCH_DATATYPE);
        String extraString = WidgetActions.loadActionLaunchPref(context, appWidgetId, id, WidgetActions.PREF_KEY_ACTION_LAUNCH_EXTRAS);

        if (launchClassName != null && !launchClassName.trim().isEmpty())
        {
            Class<?> launchClass;
            try {
                launchClass = Class.forName(launchClassName);
                launchIntent = new Intent(context, launchClass);

            } catch (ClassNotFoundException e) {
                Log.e(TAG, "createIntent :: " + launchClassName + " cannot be found! " + e.toString());
                launchClass = fallbackLaunchClass;
                launchIntent = new Intent(context, launchClass);
                launchIntent.putExtra(WidgetSettings.ActionMode.ONTAP_LAUNCH_CONFIG.name(), true);
            }
        } else {
            launchIntent = new Intent();
        }

        WidgetActions.applyAction(launchIntent, actionString);
        WidgetActions.applyData(context, launchIntent, dataString, mimeType, data);
        WidgetActions.applyExtras(context, launchIntent, extraString, data);
        launchIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return launchIntent;
    }

    /**
     * applyAction
     */
    public static void applyAction(Intent intent, @Nullable String action)
    {
        if (intent == null || action == null || action.isEmpty()) {
            return;
        }
        intent.setAction(action);
    }

    /**
     * applyData
     * @param context context
     * @param intent Intent to apply data to
     * @param dataString (optional) data string (may contain %substitutions)
     * @param mimeType (optional) dataString mimeType (text/plain)
     * @param data (optional) supporting SuntimesData to be applied to %substitutions in the dataString
     */
    public static void applyData(Context context, Intent intent, @Nullable String dataString, @Nullable String mimeType, @Nullable SuntimesData data)
    {
        //Log.d(TAG, "applyData: " + dataString + " (" + mimeType + ") [" + data + "] to " + intent);
        if (intent != null && dataString != null && !dataString.trim().isEmpty())
        {
            SuntimesUtils utils = new SuntimesUtils();
            Uri dataUri = Uri.parse(Uri.decode(
                    ((data != null) ? utils.displayStringForTitlePattern(context, dataString, data) : dataString)
            ));
            if (mimeType != null && !mimeType.trim().isEmpty()) {
                intent.setDataAndType(dataUri, mimeType);
            } else intent.setData(dataUri);
        }
    }

    public static void applyExtras(Context context, Intent intent, @Nullable String extraString, @Nullable SuntimesData data)
    {
        if (intent == null || extraString == null || extraString.trim().isEmpty()) {
            return;
        }

        String[] extras = extraString.split("&");
        for (String extra : extras)
        {
            String[] pair = extra.split("=");
            if (pair.length == 2)
            {
                String key = pair[0];
                String value = pair[1];

                char c = value.charAt(0);
                boolean isNumeric = (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7'|| c == '8' || c == '9');
                if (isNumeric)
                {
                    if (value.endsWith("L") || value.endsWith("l"))
                    {
                        try {
                            intent.putExtra(key, Long.parseLong(value));  // long
                            Log.i(TAG, "applyExtras: applied " + extra + " (long)");

                        } catch (NumberFormatException e) {
                            intent.putExtra(key, value);  // string
                            Log.w(TAG, "applyExtras: fallback " + extra + " (long)");
                        }

                    } else if (value.endsWith("D") || value.endsWith("d")) {
                        try {
                            intent.putExtra(key, Double.parseDouble(value));  // double
                            Log.i(TAG, "applyExtras: applied " + extra + " (double)");

                        } catch (NumberFormatException e) {
                            intent.putExtra(key, value);  // string
                            Log.w(TAG, "applyExtras: fallback " + extra + " (double)");
                        }

                    } else if (value.endsWith("F") || value.endsWith("f")) {
                        try {
                            intent.putExtra(key, Float.parseFloat(value));  // float
                            Log.i(TAG, "applyExtras: applied " + extra + " (float)");

                        } catch (NumberFormatException e) {
                            intent.putExtra(key, value);  // string
                            Log.w(TAG, "applyExtras: fallback " + extra + " (float)");
                        }
                    }

                } else {
                    String lowerCase = value.toLowerCase();
                    if (lowerCase.equals("true") || lowerCase.equals("false")) {
                        intent.putExtra(key, lowerCase.equals("true"));  // boolean
                        Log.i(TAG, "applyExtras: applied " + extra + " (boolean)");

                    } else {
                        intent.putExtra(key, value);  // string
                        Log.i(TAG, "applyExtras: applied " + extra + " (String)");
                    }
                }

            } else {
                Log.w(TAG, "applyExtras: skipping " + extra);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public static void deletePrefs(Context context)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_ACTIONS, 0).edit();
        prefs.clear();
        prefs.apply();
    }

    public static void deletePrefs(Context context, int appWidgetId)
    {
        String[] actionList = loadActionLaunchList(context, 0).toArray(new String[0]);
        for (String action : actionList) {
            deleteActionLaunchPref(context, appWidgetId, action);
        }
        deleteActionLaunchPref(context, appWidgetId, null);
    }

    public static void initDefaults(Context context)
    {
        PREF_DEF_ACTION_LAUNCH_TITLE = context.getString(R.string.app_name);
        PREF_DEF_ACTION_LAUNCH_DESC = context.getString(R.string.app_shortdesc);

        if (!hasActionLaunchPref(context, 0, "SUNTIMES")) {
            saveActionLaunchPref(context, WidgetActions.PREF_DEF_ACTION_LAUNCH_TITLE, WidgetActions.PREF_DEF_ACTION_LAUNCH_DESC, null, 0, "def_suntimes", WidgetActions.PREF_DEF_ACTION_LAUNCH,
                    WidgetActions.PREF_DEF_ACTION_LAUNCH_TYPE.name(), WidgetActions.PREF_DEF_ACTION_LAUNCH_ACTION, WidgetActions.PREF_DEF_ACTION_LAUNCH_DATA, WidgetActions.PREF_DEF_ACTION_LAUNCH_DATATYPE, WidgetActions.PREF_DEF_ACTION_LAUNCH_EXTRAS);
        }

        SuntimesAction.initDefaults(context);
    }

    public static void initDisplayStrings( Context context ) {
        LaunchType.initDisplayStrings(context);
    }

    /**
     * Actions that can be performed when a UI element is clicked.
     */
    public static enum SuntimesAction
    {
        NOTHING("Nothing", "Do nothing", false),

        ADD_ALARM("Suntimes", "Set alarm", false),

        CARD_NEXT("Suntimes", "Show next card", false),
        CARD_PREV("Suntimes", "Show previous card", false),
        CARD_RESET("Suntimes", "Swap cards", false),

        NOTE_NEXT("Suntimes", "Show next note", false),
        NOTE_PREV("Suntimes", "Show previous note", false),
        NOTE_RESET("Suntimes", "Show upcoming event", false),

        CONFIG_DATE("Suntimes", "Set date", false),
        CONFIG_LOCATION("Suntimes", "Set location", false),
        CONFIG_TIMEZONE("Suntimes", "Set time zone", false),

        SHOW_DIALOG_WORLDMAP("Suntimes", "Show world map dialog", true),
        SHOW_DIALOG_SOLSTICE("Suntimes", "Show solstices dialog", true),
        SHOW_DIALOG_MOON("Suntimes", "Show moon dialog", true),
        SHOW_DIALOG_SUN("Suntimes", "Show sun dialog", true),

        SHOW_CALENDAR("Calendar", "Show calendar", true),
        SHOW_MAP("Map", "Show map", true);

        private String title, desc;
        private boolean listed;

        private SuntimesAction(String title, String desc, boolean listed)
        {
            this.title = title;
            this.desc = desc;
            this.listed = listed;
        }

        public String toString() {
            return (desc != null && !desc.trim().isEmpty()) ? desc : title;
        }

        public String desc() {
            return desc;
        }
        public void setDesc( String desc ) {
            this.desc = desc;
        }

        public String title() {
            return title;
        }
        public void setTitle( String title ) {
            this.title = title;
        }

        public boolean listed() {
            return listed;
        }

        public static void initDisplayStrings( Context context )
        {
            /**SuntimesAction[] actions = SuntimesAction.values();  // TODO
            String[] titles = context.getResources().getStringArray(R.array.tapActions_titles);
            String[] desc = context.getResources().getStringArray(R.array.tapActions_display);
            for (int i=0; i<desc.length; i++)
            {
                if (i < actions.length)
                {
                    actions[i].setTitle(titles[i]);
                    actions[i].setDesc(desc[i]);
                }
            }*/
        }

        public static void initDefaults( Context context )
        {
            for (SuntimesAction action : SuntimesAction.values())
            {
                if (!hasActionLaunchPref(context, 0, action.name()))
                {
                    LaunchType launchType = LaunchType.ACTIVITY;
                    String launchString = SuntimesActivity.class.getName();
                    String launchAction = null, launchData = null, launchMime = null, launchExtras = null;

                    switch (action)
                    {
                        case SHOW_CALENDAR:
                            launchString = null;
                            launchAction = Intent.ACTION_VIEW;
                            launchData = "content://com.android.calendar/time/%dm";
                            break;

                        case SHOW_MAP:
                            launchString = null;
                            launchAction = Intent.ACTION_VIEW;
                            launchData = "geo:%lat,%lon";
                            break;

                        case SHOW_DIALOG_SUN: launchAction = SuntimesActivity.ACTION_VIEW_SUN; break;
                        case SHOW_DIALOG_MOON: launchAction = SuntimesActivity.ACTION_VIEW_MOON; break;
                        case SHOW_DIALOG_SOLSTICE: launchAction = SuntimesActivity.ACTION_VIEW_SOLSTICE; break;
                        case SHOW_DIALOG_WORLDMAP: launchAction = SuntimesActivity.ACTION_VIEW_WORLDMAP; break;

                        case CONFIG_DATE: launchAction = SuntimesActivity.ACTION_CONFIG_DATE; break;
                        case CONFIG_LOCATION: launchAction = SuntimesActivity.ACTION_CONFIG_LOCATION; break;
                        case CONFIG_TIMEZONE: launchAction = SuntimesActivity.ACTION_CONFIG_TIMEZONE; break;

                        case ADD_ALARM: launchAction = SuntimesActivity.ACTION_ADD_ALARM; break;
                        case NOTE_NEXT: launchAction = SuntimesActivity.ACTION_NOTE_NEXT; break;
                        case NOTE_PREV: launchAction = SuntimesActivity.ACTION_NOTE_PREV; break;
                        case NOTE_RESET: launchAction = SuntimesActivity.ACTION_NOTE_RESET; break;

                        case CARD_NEXT: launchAction = SuntimesActivity.ACTION_CARD_NEXT; break;
                        case CARD_PREV: launchAction = SuntimesActivity.ACTION_CARD_PREV; break;
                        case CARD_RESET: launchAction = SuntimesActivity.ACTION_CARD_RESET; break;

                        case NOTHING:
                            launchType = null;
                            break;
                        default:
                            launchType = null;
                            Log.w(TAG, "initDefaults: unhandled action: " + action.name());
                            break;
                    }

                    if (launchType != null) {
                        saveActionLaunchPref(context, action.title(), action.desc(), null, 0, action.name(), launchString, launchType.name(), launchAction, launchData, launchMime, launchExtras, action.listed());
                    }
                }
            }
        }
    }
}
