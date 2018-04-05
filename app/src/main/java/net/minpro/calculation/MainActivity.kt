package net.minpro.calculation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import net.minpro.calculation.R
import net.minpro.calculation.TestActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val arrayAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item)
//        arrayAdapter.add(10)
//        arrayAdapter.add(20)
//        arrayAdapter.add(30)

        // 上のやつをsting.xmlで設定すれば一行で済む
        val arrayAdapter = ArrayAdapter.createFromResource(this, R.array.number_of_question, android.R.layout.simple_spinner_item)

        // spinnerとアダプターをつなぐ
        spinner.adapter = arrayAdapter

        button.setOnClickListener {
            val numberOfQuestion: Int = spinner.selectedItem.toString().toInt()

            val intent = Intent(this@MainActivity, TestActivity::class.java)
            intent.putExtra("number", numberOfQuestion)
            startActivity(intent)
        }
    }
}
