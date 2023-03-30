package kz.kuz.twothreads

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.Future

// в этом упражнении два параллельных потока работают одновременно
class MainActivity : AppCompatActivity() {
    var k = "pooh"
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button1).setOnClickListener {
            (findViewById<View>(R.id.textView1) as TextView).text = "Another text"
            handler.post(SomeClass())
            val finalF = intArrayOf(1)
            handler.post {
                object : CountDownTimer(6000, 1000) {
                    // определяем длительность счётчика и цикла
                    override fun onFinish() {
                        // данный метод исполняется после завершения счётчика
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        // данный метод исполняется после каждого цикла
                        val g = finalF[0].toString()
                        (findViewById<View>(R.id.textView2) as TextView).text = g
                        finalF[0]++
                        if (k !== "pooh") {
                            (findViewById<View>(R.id.textView1) as TextView).text = k
                        }
                    }
                }.start()
            }
        }
        findViewById<View>(R.id.button2).setOnClickListener {
            var d = (findViewById<View>(R.id.textView2) as TextView).text
                    .toString().toInt()
            d++
            val e = d.toString()
            (findViewById<View>(R.id.textView2) as TextView).text = e
        }
    }

    inner class SomeClass : Runnable {
        override fun run() {
            val executorService = Executors.newFixedThreadPool(1)
            val future: Future<*> = executorService.submit<String> {
                var a: Int
                for (b in 0..999999999) {
                    a = b
                }
                k = "Yet another text"
                "Yet another text"
            }
            executorService.shutdown()
        }
    }
}