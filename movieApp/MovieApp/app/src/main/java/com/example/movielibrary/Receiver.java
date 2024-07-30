package com.example.movielibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;


public class Receiver extends BroadcastReceiver { // able to receive the broadcast
    public static final String SMS_FILTER = "SMS_FILTER";  // to store the message somewhere
    public static final String SMS_KEY = "SMS_MSG_KEY";


    @Override
//    invoke when broadcast is receive
    public void onReceive(Context context, Intent intent) {   //    intent contains message coming from outside

        // get a array list of messages
        // extract the incoming messages from the intent
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];  // message object, not string
            String message = currentMessage.getDisplayMessageBody();
            // message = title; year; Country; Genre; Cost; Keywords



            // each new message, send a broadcast contains the new message to MainActivity
            // create a new intent, to broadcast
            // Broadcast Intents
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_KEY, message); // put message into the key
            context.sendBroadcast(msgIntent); // send the broadcast
        }
    }
}
