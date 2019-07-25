package com.ipay88;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ipay.IPayIH;
import com.ipay.IPayIHPayment;
import com.ipay.IPayIHResultDelegate;

import java.io.Serializable;

/**
 * Created by yussuf on 2/28/18.
 */

public class IPay88Module extends ReactContextBaseJavaModule {
    private static  ReactApplicationContext context;

    public IPay88Module(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "IPay88";
    }

    @ReactMethod
    public void pay(ReadableMap data) {
        context = getReactApplicationContext();

        // Precreate payment
        IPayIHPayment payment = new IPayIHPayment();
        payment.setMerchantKey (data.getString("merchantKey"));
        payment.setMerchantCode (data.getString("merchantCode"));
        payment.setPaymentId (data.getString("paymentId"));
        payment.setCurrency (data.getString("currency"));
        payment.setRefNo (data.getString("referenceNo"));
        payment.setAmount (data.getString("amount"));
        payment.setProdDesc (data.getString("productDescription"));
        payment.setUserName (data.getString("userName"));
        payment.setUserEmail (data.getString("userEmail"));
        payment.setUserContact (data.getString("userContact"));
        payment.setRemark (data.getString("remark"));
        payment.setLang (data.getString("utfLang"));
        payment.setCountry (data.getString("country"));
        payment.setBackendPostURL (data.getString("backendUrl"));

        Intent checkoutIntent = IPayIH.getInstance().checkout(payment, getReactApplicationContext(), new ResultDelegate(), IPayIH.PAY_METHOD_CREDIT_CARD);
        checkoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(checkoutIntent);
    }

    static public class ResultDelegate implements IPayIHResultDelegate, Serializable {
        /*public void onPaymentSucceeded (String transId, String refNo, String amount, String remarks, String authCode)
        {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", transId);
            params.putString("referenceNo", refNo);
            params.putString("amount", amount);
            params.putString("remark", remarks);
            params.putString("authorizationCode", authCode);
            sendEvent(context, "ipay88:success", params);
        }

        public void onPaymentFailed (String transId, String refNo, String amount, String remarks, String err)
        {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", transId);
            params.putString("referenceNo", refNo);
            params.putString("amount", amount);
            params.putString("remark", remarks);
            params.putString("error", err);
            sendEvent(context, "ipay88:failed", params);
        }

        public void onPaymentCanceled (String transId, String refNo, String amount, String remarks, String errDesc)
        {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", transId);
            params.putString("referenceNo", refNo);
            params.putString("amount", amount);
            params.putString("remark", remarks);
            params.putString("error", errDesc);
            sendEvent(context, "ipay88:canceled", params);
        }*/

        @Override
        public void onPaymentSucceeded(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", s);
            params.putString("referenceNo", s1);
            params.putString("amount", s2);
            params.putString("remark", s3);
            params.putString("authorizationCode", s4);
            params.putString("s5", s5);
            params.putString("s6", s6);
            params.putString("s7", s7);
            params.putString("s8", s8);
            sendEvent(context, "ipay88:success", params);

        }

        @Override
        public void onPaymentFailed(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", s);
            params.putString("referenceNo", s1);
            params.putString("amount", s2);
            params.putString("remark", s3);
            params.putString("error", s4);
            params.putString("s5", s5);
            params.putString("s6", s6);
            params.putString("s7", s7);
            params.putString("s8", s8);
            sendEvent(context, "ipay88:failed", params);

        }

        @Override
        public void onPaymentCanceled(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", s);
            params.putString("referenceNo", s1);
            params.putString("amount", s2);
            params.putString("remark", s3);
            params.putString("error", s4);
            params.putString("s5", s5);
            params.putString("s6", s6);
            params.putString("s7", s7);
            params.putString("s8", s8);
            sendEvent(context, "ipay88:canceled", params);

        }

        public void onRequeryResult (String merchantCode, String refNo, String amount, String result)
        {
            // No need to implement
        }

        @Override
        public void onConnectionError(String s, String s1, String s2, String s3, String s4, String s5, String s6) {
            WritableMap params = Arguments.createMap();
            params.putString("transactionId", s);
            params.putString("referenceNo", s1);
            params.putString("amount", s2);
            params.putString("remark", s3);
            params.putString("error", s4);
            params.putString("s5", s5);
            params.putString("s6", s6);
            sendEvent(context, "ipay88:canceled", params);

        }

        // end
    }

    static void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

}