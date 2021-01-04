package com.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banner.DisplayActivity
import com.example.banner.GuideActivity
import com.example.banner.TransformerActivity
import com.example.banner.VideoActivity
import com.example.databinding.ActivityMainBinding
import com.example.issues.Issues10Activity
import com.example.issues.Issues12Activity
import com.example.issues.Issues13Activity
import com.example.list.ListActivity

class MainActivity : AppCompatActivity() {

    private val viewBind: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind.btnGuide.setOnClickListener { v ->
            val intent = Intent(v.context, GuideActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnRecyclerView.setOnClickListener { v ->
            val intent = Intent(v.context, ListActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnImageLoader.setOnClickListener { v ->
            val intent = Intent(v.context, DisplayActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnTransformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnVideo.setOnClickListener { v ->
            val intent = Intent(v.context, VideoActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnIssues10.setOnClickListener { v ->
            val intent = Intent(v.context, Issues10Activity::class.java)
            startActivity(intent)
        }

        viewBind.btnIssues12.setOnClickListener { v ->
            val intent = Intent(v.context, Issues12Activity::class.java)
            startActivity(intent)
        }

        viewBind.btnIssues13.setOnClickListener { v ->
            val intent = Intent(v.context, Issues13Activity::class.java)
            startActivity(intent)
        }
    }

}
