[![Analytics](https://ga-beacon.appspot.com/UA-69014260-4/Kentico/cloud-sample-app-android-tv?pixel)](https://github.com/igrigorik/ga-beacon)

# Kentico Cloud Sample Application for Android TV
<img align="right" width="200" src="https://i.imgur.com/lbl0sTZ.jpg" alt="Dancing Goat Sample App for Android TV" />
A sample app for Android TV written in Java that utilizes the [KenticoCloudDeliveryJavaRxSDK](https://github.com/Kentico/KenticoCloudDeliveryJavaRxSDK) to manage and retrieve content
You can register your account for free at https://app.kenticocloud.com.

## Application Setup
1. Install [Android Studio](https://developer.android.com/studio/) and the latest Android SDK tools. 
2. Clone or download the repository into a chosen folder. 
3. Open the project in the IDE, let it install all the necessary libraries and tools. 
4. Create a virtual TV device, for example with specifications 1080p, and Android API 25. 
5. Run the project witht the created TV device as a specified deployment target.

## Content Administration
1. Navigate to https://app.kenticocloud.com in your browser.
2. Sign in with your credentials.
3. Manage content in the content administration interface of your sample project.

You can learn more about content editing with Kentico Cloud in the [documentation](https://developer.kenticocloud.com/docs).

## Content Delivery
1. In order to utilize your own project instead of the default one, you need to change the `KENTICO_CLOUD_PROJECT_ID` constant in the `AppConfig.java` file to the ID of your own project.
2. The project ID can be found on Kentico Cloud by navigating to *Project Settings* and then to *API Keys*

## Application Installation
1. Building the project in Android Studio creates an `.apk` file, located in `app\build\outputs\apk\debug`.
2. The file can be used to install the application on a TV, using the [Android Debug Bridge](https://forum.xda-developers.com/showthread.php?t=2588979).
3. The *Developer Mode* and *Allow USB debugging* modes have to be turned on the TV. 
4. By connecting the laptop and the TV with a USB cable and having both of them on the same network, one can install the app:
  
   `adb connect <TV_IP_ADDRESS>`
   
   `adb install <PATH_TO_.APK_FILE>`
5. The TV has to be connected to the Internet in order to retrieve content from Kentico Cloud.
