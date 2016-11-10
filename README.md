# InAppPurchase

<div class="dac-toggle dac-mobile"><h2 id="billing-add-aidl" data-toggle="section" style="padding-bottom: 0px;"><span class="dac-visible-mobile-inline-block"><i class="dac-toggle-expand dac-sprite dac-expand-more-black"></i><i class="dac-toggle-collapse dac-sprite dac-expand-less-black"></i></span>Adding the AIDL file to your project</h2><hr><div class="dac-toggle-content dac-expand"><div>

<p>The <code>IInAppBillingService.aidl</code> is an Android Interface Definition
Language (AIDL) file that defines the interface to the In-app Billing Version
3 service. You can use this interface to make billing requests by invoking IPC
method calls.</p>

<p>Complete these steps to get the AIDL file:</p>
<ol>
<li>Open the <a href="https://developer.android.com/tools/help/sdk-manager.html">Android SDK Manager</a>.</li>
<li>In the SDK Manager, expand the <code>Extras</code> section.</li>
<li>Select <strong>Google Play Billing Library</strong>.</li>
<li>Click <strong>Install packages</strong> to complete the download.</li>
</ol>
<p>The <code>IInAppBillingService.aidl</code> file will be installed to <code>&amp;lt;sdk&amp;gt;/extras/google/play_billing/</code>.</p>

<p>Complete these steps to add the AIDL to your project:</p>

<ol>
  <li>Download the Google Play Billing Library to your Android project:
      <ol type="a">
      <li>Select <strong>Tools &gt; Android &gt; SDK Manager</strong>.</li>
      <li>Under <strong>Appearance &amp; Behavior &gt; System Settings &gt; Android SDK</strong>,
          select the <em>SDK Tools</em> tab to select and download <em>Google Play Billing
          Library</em>.</li></ol>
  </li><li>Copy the <code>IInAppBillingService.aidl</code> file to your project.
    <ul>
      <li>If you are using Android Studio, complete these steps to copy the file:
        <ol type="a">
          <li>Navigate to <code>src/main</code> in the Project tool window.</li>

          <li>Select <strong>File &gt; New &gt; Directory</strong>, enter <code>aidl</code> in the
          <em>New Directory</em> window, and select <strong>OK</strong>.

          </li><li>Select <strong>File &gt; New &gt; Package</strong>, enter
          <code>com.android.vending.billing</code> in the <em>New Package</em> window, and select
          <strong>OK</strong>.</li>

          <li>Using your operating system file explorer, navigate to
          <code>&amp;lt;sdk&amp;gt;/extras/google/play_billing/</code>, copy the
          <code>IInAppBillingService.aidl</code> file, and paste it into the
          <code>com.android.vending.billing</code> package in your project.
          </li>
        </ol>
      </li>

      <li>If you are developing in a non-Android Studio environment, create the
      following directory: <code>/src/com/android/vending/billing</code>. Copy the
      <code>IInAppBillingService.aidl</code> file into this directory. Place the AIDL
      file in your project and use the Gradle tool to build your project so that
      the <code>IInAppBillingService.java</code> file is generated.
      </li>
    </ul>
  </li>

  <li>Build your application. You should see a generated file named <code>IInAppBillingService.java</code> in the <code>/gen</code> directory of your project.
  </li>
</ol>

</div></div></div>


# Usage of InAppPurchase class

Your activity onCreate() , onDestroy(), OnClick(where you want user to buy product) and onActivityResult(When user have bought the product)


> private InAppPurchase inAppPurchases;
>
> @Override
>    protected void onCreate(Bundle savedInstanceState) { </br>
>        super.onCreate(savedInstanceState);  </br>
>        inAppPurchases = new InAppPurchases(this); </br>
>        inAppPurchases.bindInAppService(); </br>
>    } </br>
    
> @Override </br>
>    protected void onDestroy() { </br>
>        super.onDestroy(); </br>
>        inAppPurchases.unBindInAppService(); </br>
> }

> removeAds.setOnClickListener(new View.OnClickListener() {  </br>
>            @Override  </br>
>            public void onClick(View v) {  </br>
>                  inAppPurchases.getPremiumUpgraded();  </br>
>            }  </br>
>        });  </br>

> @Override </br>
>    protected void onActivityResult(int requestCode, int resultCode, Intent data) { </br>
>         if (inAppPurchases.onActivityResult(requestCode, resultCode, data)) { </br>
>            new SharedPreference(this).setPremiumUpgrade(true); </br>
>            /*Todo what you want to after purchase*/ </br>
>        } </br>
>    } </br>

Note : Use two classes InAppPurchase and SharedPreference in order to compile the code 
