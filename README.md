## Webtime

Here is an easy and quick way to make an Android “app” for any website. The application is just a wrapper around Android's WebView.

#### Instruction:
1) Get Android Studio Portable from https://portapps.io/app/android-studio-portable/ (if you dont have Android Studio already)

2) Clone or download the project zip file and open in ASP, let Gradle sync finish

3) do ```Ctrl + Shift + F``` for the phrase ```com.example.app``` and change the package name everywhere in the listings (there are many parts of files that need to be edited) to whatever you want.

4) edit lines 2 and 3 in strings.xml in app/res/layout folder to change the name of your app and the website it points to 

5) right click on the res folder, click on new and click on image asset and then select the icon for your app, then click next and finish

6) after all modifications are done, go to build menu and choose clean project. Then either build a debug build or a signed released build using your own key using the build menu.

NOTES: - install SDK command line tools from ASP SDK manager then gradle sync again if gradle sync fails
