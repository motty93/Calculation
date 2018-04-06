package net.minpro.calculation

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test.*
import net.minpro.calculation.R.id.textViewAnswer
import java.util.*

class TestActivity : AppCompatActivity(), View.OnClickListener {

    //問題数
    var numberOfReamining: Int = 0
    //正解数
    var numberOfCorrect: Int = 0
    //soundPool
    lateinit var soundPool : SoundPool
    //サウンドの宣言
    var soundIdCorrect : Int = 0
    var soundIdIncorrect : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //テスト画面が開いたら
        val bundle : Bundle = intent.extras // intent.extrasの戻り値はbundle型
        val numberOfQuestion = bundle.getInt("number")
        textViewRemaining.text = numberOfQuestion.toString()


        //todo 答え合わせボタンが押されたら
        buttonAnswerCheck.setOnClickListener {
            answerCheck()
        }

        //todo 戻るボタンを押されたら
        buttonBack.setOnClickListener {  }

        //todo 電卓ボタンが押されたら
        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        buttonminus.setOnClickListener(this)
        buttonc.setOnClickListener(this)

        //問題をだす
        question()

    }

    override fun onResume() {
        super.onResume()

        //soundpoolの準備
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                                        .setUsage(AudioAttributes.USAGE_MEDIA) // 用途に応じて変えてくれる
                                        .build())
                                        .setMaxStreams(1) // 同時に流す音楽の数
                                        .build()
                    } else {
                        SoundPool(1, AudioManager.STREAM_MUSIC, 0)
                    }

        soundIdCorrect = soundPool.load(this, R.raw.correct1, 1)
        soundIdIncorrect = soundPool.load(this, R.raw.incorrect1, 1)
    }

    override fun onPause() {
        super.onPause()

        soundPool.release()
    }

    //問題を出すメソッド
    private fun question() {
        //戻るボタンの非活性化
        buttonBack.isEnabled = false
        //それ以外のボタンを活性化
        buttonAnswerCheck.isEnabled = true
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
        buttonminus.isEnabled = true
        buttonc.isEnabled = true

        //右と左の数字をランダムに表示する
        val random = Random()
        val intQuestionLeft = random.nextInt(100) + 1
        val intQuestionRight = random.nextInt(100) + 1
        textViewLeft.text = intQuestionLeft.toString()
        textViewRight.text = intQuestionRight.toString()

        //マイナスとプラスをランダムに表示
        when(random.nextInt(2) + 1) {
            1 -> textViewOperator.text = "+"
            2 -> textViewOperator.text = "-"
        }

        //終わったら答えを消して、丸画像orバツ画像を消す
        textViewAnswer.text = ""
        imageView.visibility = View.VISIBLE
    }

    //答え合わせをするメソッド
    private fun answerCheck() {
        buttonBack.isEnabled = false
        buttonAnswerCheck.isEnabled = false
        button0.isEnabled = false
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
        buttonminus.isEnabled = false
        buttonc.isEnabled = false

        //残り問題数を減らす
        numberOfReamining --
        textViewRemaining.text = numberOfReamining.toString()

        //まるばつ画像を見えるようにする
        imageView.visibility = View.VISIBLE

        //自分の答え
        val intMyAnswer: Int = textViewAnswer.text.toString().toInt()

        //本当の答え
        val intRealAnswer: Int =
                if (textViewOperator.text == "+") {
                    textViewRight.text.toString().toInt() + textViewLeft.text.toString().toInt()
                } else {
                    textViewRight.text.toString().toInt() - textViewLeft.text.toString().toInt()
                }

        //比較
        if (intMyAnswer == intRealAnswer) {
            numberOfCorrect ++
            textViewCorrect.text = numberOfCorrect.toString()
            imageView.setImageResource(R.drawable.o699403)
            soundPool.play(soundIdCorrect, 1.0f, 1.0f, 0, 0, 1.0f)
        } else {
            imageView.setImageResource(R.drawable.x699403)
            soundPool.play(soundIdIncorrect, 1.0f, 1.0f, 0, 0, 1.0f)
        }
    }

    override fun onClick(v: View?) {
        val button: Button = v as Button

        when(v?.id) {
            //クリアボタンだったら消す
            R.id.buttonc
                -> textViewAnswer.text = ""

            //マイナスボタン
            R.id.buttonminus
                    -> if (textViewAnswer.text.toString() == "")
                        textViewAnswer.text = "-"

            //0のとき
            R.id.button0
                    -> if (textViewAnswer.text.toString() != "0" && textViewAnswer.text.toString() != "-")
                        textViewAnswer.append(button.text)

            else
                -> if (textViewAnswer.text.toString() == "0")
                    textViewAnswer.text = button.text
                   else textViewAnswer.append(button.text)
        }
    }
}
