package com.plantquiz.plantquiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Toast.makeText(this, "The onCreate method is called ", Toast.LENGTH_SHORT).show()
    }

    fun button1IsClicked(buttonView: View) {
        Toast.makeText(this, "Button number 1 is clicked ", Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
