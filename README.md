# motivaimagine-android-scanner-serial
A Simple Serial Number reader using **Google Mobile Vision.** for motiva Implants.


## snapshot

#### like this:SN Scanner
<img width="300" width=“500” src="example/scan_demo.gif"></img>



## how to use

Add permissions in the manifest:

	   <uses-feature android:name="android.hardware.camera" />
    
        <!-- To auto-complete the email text field in the login form with the user's emails -->
        <uses-permission android:name="android.permission.CAMERA" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.VIBRATE" />
    
        <meta-data
            android:name="com.google.android.gms.vision.DEPENDENCIES"
            android:value="ocr" />
		
Step 1. Add the dependency

	dependencies {
	            implementation 'com.google.android.gms:play-services-vision:11.8.0'
	}		



### use SN Scanner in a Activity

in xml

```java
 <com.motivaimagine.snscanner.scanner_serial.camera.CameraSourcePreview
        android:id="@+id/preview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/appbar">


    </com.motivaimagine.snscanner.scanner_serial.camera.CameraSourcePreview>

    <com.motivaimagine.snscanner.scanner_serial.ScannerOverlay
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#44000000"
        app:line_color="@color/colorPrimary"
        app:line_speed="6"
        app:line_width="8"
        app:square_height="50"
        app:square_width="300">

        <com.motivaimagine.snscanner.scanner_serial.camera.GraphicOverlay

            android:id="@+id/graphicOverlay"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </com.motivaimagine.snscanner.scanner_serial.ScannerOverlay>
```



## Thanks to 
* [android-vision](https://github.com/googlesamples/android-vision) 
* [ravi8x](https://github.com/ravi8x) 


