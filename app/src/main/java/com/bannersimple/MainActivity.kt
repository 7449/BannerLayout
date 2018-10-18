package com.bannersimple

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bannersimple.simple.*
import com.bannersimple.simple.issues.Issues10Activity
import com.bannersimple.simple.issues.Issues12Activity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_refresh_simple).setOnClickListener { v ->
            val intent = Intent(v.context, RefreshActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_simple).setOnClickListener { v ->
            val intent = Intent(v.context, SimpleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_guide).setOnClickListener { v ->
            val intent = Intent(v.context, SimpleGuideActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_image_manager).setOnClickListener { v ->
            val intent = Intent(v.context, ImageManagerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_transformer).setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_method_test).setOnClickListener { v ->
            val intent = Intent(v.context, MethodTestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_issues_10).setOnClickListener { v ->
            val intent = Intent(v.context, Issues10Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        findViewById<View>(R.id.btn_issues_12).setOnClickListener { v ->
            val intent = Intent(v.context, Issues12Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        findViewById<View>(R.id.btn_java).setOnClickListener { v ->
            val intent = Intent(v.context, SimpleJavaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}
