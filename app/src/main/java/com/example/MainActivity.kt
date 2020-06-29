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

        btnGuide.setOnClickListener { v ->
            val intent = Intent(v.context, GuideActivity::class.java)
            startActivity(intent)
        }

        btnRecyclerView.setOnClickListener { v ->
            val intent = Intent(v.context, ListActivity::class.java)
            startActivity(intent)
        }

        btnImageManager.setOnClickListener { v ->
            val intent = Intent(v.context, DisplayActivity::class.java)
            startActivity(intent)
        }

        btnTransformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            startActivity(intent)
        }

        btnIssues10.setOnClickListener { v ->
            val intent = Intent(v.context, Issues10Activity::class.java)
            startActivity(intent)
        }

        btnIssues12.setOnClickListener { v ->
            val intent = Intent(v.context, Issues12Activity::class.java)
            startActivity(intent)
        }

        btnIssues13.setOnClickListener { v ->
            val intent = Intent(v.context, Issues13Activity::class.java)
            startActivity(intent)
        }
    }

}
