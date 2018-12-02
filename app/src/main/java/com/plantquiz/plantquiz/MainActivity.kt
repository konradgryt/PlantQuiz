package com.plantquiz.plantquiz

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Toast.makeText(this, "The onCreate method is called ", Toast.LENGTH_SHORT).show()
        val myPlant: Plant = Plant("","","","","","",0)
       // Plant("test","test","test","","","",0)
        myPlant.plantName = "Some example name"
        var nameOfPlant = myPlant.plantName
    }

    fun button1IsClicked(buttonView: View)  {
        Toast.makeText(this, "Button number 1 is clicked ", Toast.LENGTH_SHORT).show()

        var myNumber = 20 //Implied data type
        val myName: String = "Konrad" //Specified data type
        var numberOfLetters = myName.length

        //myName = "another name" val is equivalent of a constant - so cannot change it

        var animalName: String? = null
        var testNullVar = animalName?.length ?: 100 // If animalname is null, assign 100 to testNullVar

    }

    fun button2IsClicked(buttonView: View) {
        Toast.makeText(this, "Button number 2 is clicked ", Toast.LENGTH_SHORT).show()
    }

    fun button3IsClicked(buttonView: View) {
        Toast.makeText(this, "Button number 3 is clicked ", Toast.LENGTH_SHORT).show()
    }

    fun button4IsClicked(buttonView: View) {
        Toast.makeText(this, "Button number 4 is clicked ", Toast.LENGTH_SHORT).show()
    }

    // <Parameters that wil be passed to the task , Integer - on progress update method, Datatype of returned value of async task>
    inner class DownloadingPlantTask: AsyncTask<String, Int, List<Plant>>() {

        //overwriting function from asynctask class
        // vararg is passing multiple parameters?
        // can access background thread. not the user interface thread
        override fun doInBackground(vararg params: String?): List<Plant>? {

            return null
        }

        // after doinbackground is going to be executed completely,
        // /ts going to pass its result as parameter to onpostexecute automatically
        // can access user interface thread. not background thread
        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
        }
    }

    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "The onStart method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

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
}
