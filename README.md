##SkyRocket Adaptors

Please follow these instructions to integrate any of the compatible adaptors found in this GitHub repo.

###Download required files

1. Download the latest LITE version of the SkyRocket [iOS](https://controls.skyrocketapp.com/SDKDownload/DownloadSDK?sdkId=74) or [Android](https://controls.skyrocketapp.com/SDKDownload/DownloadSDK?sdkId=71) SDK here. 

   Note: The LITE version does not contain any of the SDKs for our integrated networks. If you also plan to use our integrated networks in your app, download the latest FULL version of the SkyRocket [iOS](https://controls.skyrocketapp.com/SDKDownload/DownloadSDK?sdkId=75) or [Android](https://controls.skyrocketapp.com/SDKDownload/DownloadSDK?sdkId=70) SDK here.

2. Navigate to the folder of the adaptor you are going to integrate in this repo. There you will find all of the adaptor files for your specific platform, as well a README.md file containing important information specific to your selected adaptor.

3. Download all of the files for your selected adaptor.

4. Click on the link to download the network SDK for your selected adaptor.

5. Copy the required JSON parameters for your selected adaptor. We will be using these in the next section.

###Configuring Networks in the Dashboard

1. Go to [SkyRocketApp.com](http://www.skyrocketapp.com/). After logging in, click on the + at the top right of the page to select New Network.

2. Select Manual for Network Type.
     
3. Select Compatible SDK Networks for Network Type. That selection will allow you to enter your ad network name and required parameters inside the Edit JSON Data text box.

   **Make sure to replace the dummy values in the JSON data with your values.**

   The following screenshot show a sample configuration from [Chartboost](https://www.chartboost.com), a compatible ad network for SkyRocket.
    
   ![SkyRocket Adapters](https://raw.github.com/burstly/burstly-integration-docs/master/res/img/SkyRocketAdaptors/Step_32.png)
   
   
###Integrating the SkyRocket Adaptors into your project

Now that you have configured your compatible network in the SkyRocket Dashboard, you are ready to add the required files to your project.

1. In your explorer, locate the adaptor files, the network SDK file, and the BurstlySDK folder that were just downloaded.
    
2. Drag and drop all of the above into the root folder in your project.

   For iOS: When prompted, verify that the checkbox next to your project is checked in the "Add to targets" section.

3. In your project, replace the zoneID and appID with your values.

**That was easy! You can now run your project to see ads from your selected compatible network.**