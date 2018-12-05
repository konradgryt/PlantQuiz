package com.plantquiz.plantquiz.Controller

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.plantquiz.plantquiz.Model.DownloadingObject
import com.plantquiz.plantquiz.Model.ParsePlantUtility
import com.plantquiz.plantquiz.Model.Plant
import com.plantquiz.plantquiz.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var _cameraButton: Button? = null
    private var _photoGalleryButton: Button? = null
    private var _imageTaken: ImageView? = null
    private var _button1: Button? = null
    private var _button2: Button? = null
    private var _button3: Button? = null
    private var _button4: Button? = null

    val OPEN_CAMERA_BUTTON_REQUEST_ID = 1000
    val OPEN_GALLERY_BUTTON_REQUEST_ID = 2000

    var correctAnswerIndex: Int = 0
    var correctPlant: Plant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (checkForInternetConnection()) {
            val innerClassObject = DownloadingPlantTask()
            innerClassObject.execute() //calss doInBackground function in innerClassObject
        }
    /*
        Toast.makeText(this, "The onCreate method is called ", Toast.LENGTH_SHORT).show()
        val myPlant: Plant = Plant("","","","","","",0)
       // Plant("test","test","test","","","",0)
        myPlant.plantName = "Some example name"
        var nameOfPlant = myPlant.plantName
    */
    /*
        var flower = Plant()
        var tree = Plant()

        var collectionOfPlants: ArrayList<Plant> = ArrayList()
        collectionOfPlants.add(flower)
        collectionOfPlants.add(tree)
    */

        _cameraButton = findViewById(R.id.btnOpenCamera)
        _photoGalleryButton = findViewById(R.id.btnOpenPhotoGallery)
        _imageTaken = findViewById(R.id.imgTaken)


        _button1 = findViewById(R.id.button1)
        _button2 = findViewById(R.id.button2)
        _button3 = findViewById(R.id.button3)
        //Its declared in the class so i dont have to put <Button>, but if I would declare and instantiate it here
        // then I have to put it like this, example:
        _button4 = findViewById<Button>(R.id.button4)

        _cameraButton?.setOnClickListener{
            Toast.makeText(this, "Camera button is clicked", Toast.LENGTH_SHORT).show()

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)
        }

        _photoGalleryButton?.setOnClickListener{
            Toast.makeText(this, "Open gallery button is clicked", Toast.LENGTH_SHORT).show()

            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                val imageData = data?.extras?.get("data") as Bitmap
                _imageTaken?.setImageBitmap(imageData)
            }
        }
        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                val contentURI = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                _imageTaken?.setImageBitmap(bitmap)
            }
        }
    }

    fun button1IsClicked(buttonView: View)  {
//        Toast.makeText(this, "Button number 1 is clicked ", Toast.LENGTH_SHORT).show()
//
//        var myNumber = 20 //Implied data type
//        val myName: String = "Konrad" //Specified data type
//        var numberOfLetters = myName.length
//
//        //myName = "another name" val is equivalent of a constant - so cannot change it
//
//        var animalName: String? = null
//        var testNullVar = animalName?.length ?: 100 // If animalname is null, assign 100 to testNullVar
        evaluateAnswer(0)
    }

    fun button2IsClicked(buttonView: View) {
    //    Toast.makeText(this, "Button number 2 is clicked ", Toast.LENGTH_SHORT).show()
        evaluateAnswer(1)
    }

    fun button3IsClicked(buttonView: View) {
    //    Toast.makeText(this, "Button number 3 is clicked ", Toast.LENGTH_SHORT).show()
        evaluateAnswer(2)
    }

    fun button4IsClicked(buttonView: View) {
    //    Toast.makeText(this, "Button number 4 is clicked ", Toast.LENGTH_SHORT).show()
        evaluateAnswer(3)
    }

    // <Parameters that wil be passed to the task , Integer - on progress update method, Datatype of returned value of async task>
    inner class DownloadingPlantTask: AsyncTask<String, Int, List<Plant>>() {

        //overwriting function from asynctask class
        // vararg is passing multiple parameters?
        // can access background thread. not the user interface thread
        override fun doInBackground(vararg params: String?): List<Plant>? { //called by execute

            //to do it need to add:
            // (inside manifest) <uses-permission android:name="android.permission.INTERNET" />
            // (inside application) android:usesCleartextTraffic="true"
//            val downloadingObject: DownloadingObject =
//                DownloadingObject()
//            val jsonData = downloadingObject.downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")
//            Log.i("JSON", jsonData)

            val parsePlant = ParsePlantUtility()

            return parsePlant.parsePlantObjectsFromJSONData()
        }

        // after doinbackground is going to be executed completely,
        // /ts going to pass its result as parameter to onpostexecute automatically
        // can access user interface thread. not background thread
        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            var numberOfPlants = result?.size ?: 0

            if (numberOfPlants > 0) {
                var randomPlantIndexForButton1: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton2: Int = (Math.random() * result.size).toInt()
                var randomPlantIndexForButton3: Int = (Math.random() * result.size).toInt()
                var randomPlantIndexForButton4: Int = (Math.random() * result.size).toInt()

                var allRandomPlants = ArrayList<Plant>()
                allRandomPlants.add(result.get(randomPlantIndexForButton1))
                allRandomPlants.add(result.get(randomPlantIndexForButton2))
                allRandomPlants.add(result.get(randomPlantIndexForButton3))
                allRandomPlants.add(result.get(randomPlantIndexForButton4))

                button1.text = result.get(randomPlantIndexForButton1).toString()
                button2.text = result.get(randomPlantIndexForButton2).toString()
                button3.text = result.get(randomPlantIndexForButton3).toString()
                button4.text = result.get(randomPlantIndexForButton4).toString()

                correctAnswerIndex = (Math.random() * allRandomPlants.size).toInt()
                correctPlant = allRandomPlants.get(correctAnswerIndex)

                val downloadingImageTask = DownloadingImageTask()
                downloadingImageTask.execute(allRandomPlants.get(correctAnswerIndex).pictureName)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "The onStart method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        checkForInternetConnection()
        Toast.makeText(this, "The onResume method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(this, "The onPause method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        Toast.makeText(this, "The onStop method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText(this, "The onRestart method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "The onDestroy method is called ", Toast.LENGTH_SHORT).show()
    }

    fun imageViewIsClicked(imageView: View) {

        val randomNumber: Int = (Math.random() * 6).toInt() + 1
        Log.i("TAG", "THE RANDOM NUBER IS: $randomNumber")

        /*
        if (randomNumber == 1) {
            // I can use instance variables or names used in xml file

            //_cameraButton?.setBackgroundColor(Color.YELLOW) // instance variable, private field
            btnOpenCamera.setBackgroundColor(Color.YELLOW) // id from xml file
            btnOpenCamera.setBackgroundColor(Color.YELLOW) // id from xml file
        } else if (randomNumber == 2) {
            _photoGalleryButton?.setBackgroundColor(Color.MAGENTA)
            //btnOpenPhotoGallery.setBackgroundColor(Color.MAGENTA)
        } else if (randomNumber == 3) {
            _button1?.setBackgroundColor(Color.RED)
        } else if (randomNumber == 4) {
            _button2?.setBackgroundColor(Color.GRAY)
        } else if (randomNumber == 5) {
            button3.setBackgroundColor(Color.CYAN) //using button3 (from content_main.xml) instead _button3? for diversity
        } else { //if (randomNumber == 6) {
            _button4?.setBackgroundColor(Color.BLUE)
        }
        */

        when (randomNumber) {
            1 -> btnOpenCamera.setBackgroundColor(Color.YELLOW)
            2 -> btnOpenPhotoGallery.setBackgroundColor(Color.MAGENTA)
            3 -> button1.setBackgroundColor(Color.RED)
            4 -> button2.setBackgroundColor(Color.GRAY)
            5 -> button3.setBackgroundColor(Color.CYAN)
            6 -> button4.setBackgroundColor(Color.BLUE)
        }

    }

    // Check for internet connection
    private fun checkForInternetConnection(): Boolean {

        val connectivityManager: ConnectivityManager =
            this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isDeviceConnectedToInternet = networkInfo != null && networkInfo.isConnected
        if (!isDeviceConnectedToInternet) {
            createAlert()
            Log.i("INTERNET","There is no internet")
            return false
        } else {
            Log.i("INTERNET","There is internet")
            return true
        }
    }

    private fun createAlert() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please check internet connection")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
                dialog: DialogInterface?, which: Int ->
                startActivity(Intent(Settings.ACTION_SETTINGS))
        })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", {
            dialog: DialogInterface?, which: Int ->
            Toast.makeText(this@MainActivity, " You must be connected to the internet", Toast.LENGTH_SHORT).show()
            finish() // closing current activity
        })

        alertDialog.show()
    }

    private fun evaluateAnswer(userGuess: Int) {

        if (userGuess != correctAnswerIndex) {
            var correctPlantName = correctPlant.toString()
            txtState.text = "Wrong. Choose : $correctPlantName"
            when (userGuess) {
                0 -> button1.setBackgroundColor(Color.RED)
                1 -> button2.setBackgroundColor(Color.RED)
                2 -> button3.setBackgroundColor(Color.RED)
                3 -> button4.setBackgroundColor(Color.RED)
            }
        } else {
            txtState.text = "Right!"
            when (correctAnswerIndex) {
                0 -> button1.setBackgroundColor(Color.GREEN)
                1 -> button2.setBackgroundColor(Color.GREEN)
                2 -> button3.setBackgroundColor(Color.GREEN)
                3 -> button4.setBackgroundColor(Color.GREEN)
            }
        }
    }

    //Downloading Image Process

    inner class DownloadingImageTask: AsyncTask<String, Int, Bitmap?>() {
        override fun doInBackground(vararg pictureName: String?): Bitmap? {

            try {
                val downloadingObject = DownloadingObject()
                val plantBitmap: Bitmap? = downloadingObject.downloadPicture(pictureName[0])

                return plantBitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imgTaken.setImageBitmap(result)
        }
    }
}
