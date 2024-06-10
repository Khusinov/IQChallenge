package uz.khusinovs.iqchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.khusinovs.iqchallenge.utills.Pref

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Pref.init(applicationContext)
    }
}