package gujarprawjal12gmail.com.example.qrscanner

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*


private const val CAMERA_REQUEST = 101

class MainActivity : AppCompatActivity() {


    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scannerView = findViewById<CodeScannerView>(R.id.scannerview)


        setupPermissions()
        codeScanner()
    }


    private fun codeScanner () {

        codeScanner =CodeScanner ( this, scannerView)
        //codeScanner = CodeScanner(this, scannerview)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        codeScanner.scanMode = ScanMode.PREVIEW


        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
               tv_textview.text = it.text

            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Log.e("Main", "Camera error: ${it.message}")
            }
        }


        scannerView.setOnClickListener {
            codeScanner.startPreview()
    }
    }

         override fun onResume() {
         super.onResume()
         codeScanner.startPreview()
    }

     //to relise the memory
        override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private  fun setupPermissions(){
        val permission: Int = ContextCompat.checkSelfPermission( this,android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){

            makeRequest()
        }
    }

       private  fun makeRequest() {
             ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                 CAMERA_REQUEST)
       }

       override  fun  OnRequestPermissionsResult(requestCode: Int, permission: Array<out String>, grantResult: IntArray){
           when (requestCode){
               CAMERA_REQUEST -> {
                   if (grantResult.isEmpty() || grantResult[0] != PackageManager.PERMISSION_GRANTED) {
                       Toast.makeText(
                           this,
                           "You Need the Camera permission to be able to usee the app",
                           Toast.LENGTH_SHORT
                       ).show()
                   }else {
                       // done
                   }
               }
           }

       }
    }