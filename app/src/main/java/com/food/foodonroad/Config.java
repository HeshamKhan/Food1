package com.food.foodonroad;

public interface Config {


    // CONSTANTS
    static final String YOUR_SERVER_URL =  "http://192.168.1.96:8013/gcm_server_php/register.php";
    // YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
    static final String GOOGLE_SENDER_ID = "249006616070";  // Place here your Google project id

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Food on road";

    static final String DISPLAY_MESSAGE_ACTION ="com.food.foodonroad.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";


}
