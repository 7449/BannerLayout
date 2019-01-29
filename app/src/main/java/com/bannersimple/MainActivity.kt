package com.bannersimple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bannersimple.simple.*
import com.bannersimple.simple.issues.Issues10Activity
import com.bannersimple.simple.issues.Issues12Activity
import com.bannersimple.simple.issues.Issues13Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_refresh_simple.setOnClickListener { v ->
            val intent = Intent(v.context, RefreshActivity::class.java)
            startActivity(intent)
        }

        btn_simple.setOnClickListener { v ->
            val intent = Intent(v.context, SimpleActivity::class.java)
            startActivity(intent)
        }

        btn_guide.setOnClickListener { v ->
            val intent = Intent(v.context, SimpleGuideActivity::class.java)
            startActivity(intent)
        }

        btn_image_manager.setOnClickListener { v ->
            val intent = Intent(v.context, ImageManagerActivity::class.java)
            startActivity(intent)
        }

        btn_transformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            startActivity(intent)
        }

        btn_method_test.setOnClickListener { v ->
            val intent = Intent(v.context, MethodTestActivity::class.java)
            startActivity(intent)
        }

        btn_issues_10.setOnClickListener { v ->
            val intent = Intent(v.context, Issues10Activity::class.java)
            startActivity(intent)
        }

        btn_issues_12.setOnClickListener { v ->
            val intent = Intent(v.context, Issues12Activity::class.java)
            startActivity(intent)
        }

        btn_issues_13.setOnClickListener { v ->
            val intent = Intent(v.context, Issues13Activity::class.java)
            startActivity(intent)
        }

        btn_java.setOnClickListener { v ->
            val intent = Intent(v.context, SimpleJavaActivity::class.java)
            startActivity(intent)
        }
    }

}
