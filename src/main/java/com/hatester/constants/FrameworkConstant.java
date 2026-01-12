package com.hatester.constants;

import com.hatester.helpers.PropertiesHelper;
import com.hatester.helpers.SystemHelper;

public class FrameworkConstant {
    private FrameworkConstant() {
    }

    public static final String PROJECT_PATH = SystemHelper.getCurrentDir();
    public static final String EXCEL_DATA_FILE_PATH = PropertiesHelper.getValue("EXCEL_DATA_FILE_PATH");

    public static final String BROWSER = PropertiesHelper.getValue("BROWSER");
    public static final String HEADLESS = PropertiesHelper.getValue("HEADLESS");
    public static final String WINDOW_SIZE = PropertiesHelper.getValue("WINDOW_SIZE");
    public static final String URL = PropertiesHelper.getValue("URL");
    public static final String EMAIL = PropertiesHelper.getValue("EMAIL");
    public static final String PASSWORD = PropertiesHelper.getValue("PASSWORD");

    public static final String VIDEO_RECORD_PATH = PropertiesHelper.getValue("VIDEO_RECORD_PATH");
    public static final String VIDEO_RECORD = PropertiesHelper.getValue("VIDEO_RECORD");
    public static final String SCREENSHOT_PATH = PropertiesHelper.getValue("SCREENSHOT_PATH");
    public static final String SCREENSHOT_PASSED_TCS = PropertiesHelper.getValue("SCREENSHOT_PASSED_TCS");
    public static final String SCREENSHOT_FAILED_TCS = PropertiesHelper.getValue("SCREENSHOT_FAILED_TCS");
    public static final String SCREENSHOT_SKIPPED_TCS = PropertiesHelper.getValue("SCREENSHOT_SKIPPED_TCS");
    public static final String SCREENSHOT_ALL_STEPS = PropertiesHelper.getValue("SCREENSHOT_ALL_STEPS");

    public static final int WAIT_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("WAIT_TIMEOUT"));
    public static final int POOL_TIME = Integer.parseInt(PropertiesHelper.getValue("POOL_TIME"));
    public static final double WAIT_SLEEP_STEP = Double.parseDouble(PropertiesHelper.getValue("WAIT_SLEEP_STEP"));
    public static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));
    public static final String ACTIVE_PAGE_LOADED = PropertiesHelper.getValue("ACTIVE_PAGE_LOADED");

    public static final String CREATE_TICKET_JIRA = PropertiesHelper.getValue("CREATE_TICKET_JIRA");
}
