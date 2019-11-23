package com.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banner.DisplayActivity
import com.example.list.ListActivity
import com.example.banner.GuideActivity
import com.example.banner.TransformerActivity
import com.example.issues.Issues10Activity
import com.example.issues.Issues12Activity
import com.example.issues.Issues13Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_guide.setOnClickListener { v ->
            val intent = Intent(v.context, GuideActivity::class.java)
            startActivity(intent)
        }

        btn_recycler_view.setOnClickListener { v ->
            val intent = Intent(v.context, ListActivity::class.java)
            startActivity(intent)
        }

        btn_image_manager.setOnClickListener { v ->
            val intent = Intent(v.context, DisplayActivity::class.java)
            startActivity(intent)
        }

        btn_transformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
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
    }

}
