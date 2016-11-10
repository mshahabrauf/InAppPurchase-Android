package com.appswallet.rosie.app.inapppurchaseservice;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;
import com.appswallet.rosie.app.ApplicationConstants;
import com.appswallet.rosie.app.MainActivity;
import com.appswallet.rosie.app.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Muhammad Shahab on 10/6/16.
 */
public class InAppPurchases implements ServiceConnection {

    IInAppBillingService mService;
    ServiceConnection serviceConnection;
    Context context;
    ArrayList<String> skuList = new ArrayList<String>(){{
        add("premium_upgrade");
    }};
    private MainActivity mActivity;
    public InAppPurchases(Context context) {
        this.context =context;
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = IInAppBillingService.Stub.asInterface(service);
        /*  Checking premium upgrade to decide
            whether ads would be loaded or not  */
        if (isPremiumUpgraded())
        {
            mActivity.removeTheAddService();
        }
        else
        {
            mActivity.addTheAdsServices();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
    }

    public void bindInAppService()
    {
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
    }
    public void unBindInAppService()
    {
        if (mService != null) {
            context.unbindService(this);
        }
    }
    public void getPremiumUpgraded()
    {
        try {
            Bundle buyIntentBundle = mService.getBuyIntent(3, context.getPackageName(),
                    skuList.get(0), "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");

            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
            mActivity.startIntentSenderForResult(pendingIntent.getIntentSender(),
                    ApplicationConstants.REQUEST_CODE_PREMIUM_UPGRADED, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                    Integer.valueOf(0));

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    public boolean isPremiumUpgraded()
    {
        SharedPreference sharedPreference = new SharedPreference(context);

        if (!sharedPreference.isPremiumUpgrade()) {
            try {
                Bundle ownedItems = mService.getPurchases(3, context.getPackageName(), "inapp", null);
                int response = ownedItems.getInt("RESPONSE_CODE");
                if (response == 0) {
                    ArrayList<String> ownedSkus =
                            ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                    ArrayList<String> purchaseDataList =
                            ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");

                    for (int i = 0; i < purchaseDataList.size(); ++i) {
                        String sku = ownedSkus.get(i);
                        boolean premium = sku.equals(skuList.get(0));
                        sharedPreference.setPremiumUpgrade(premium);
                        return premium;
                    }
                }

            } catch (RemoteException e) {

                return false;
            }
            return false;
        }
        else return true;

    }
    public boolean onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ApplicationConstants.REQUEST_CODE_PREMIUM_UPGRADED) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == mActivity.RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    return sku.equals(skuList.get(0));
                }
                catch (JSONException e) {

                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
}
