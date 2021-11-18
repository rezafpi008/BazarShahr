package com.bazar.bane.bazarshahr.loginFragments

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bazar.bane.bazarshahr.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper


class EnterAppActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_app)
    }
}