package my.tone.customtextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_tv.text =
//            "123456789!123456789@123456789#123456789$123456789%12341341" //어중간한 숫자 값일 때
//            "가나다라마바사아자차카타하일이삼사오육칠팔구십하나두울세엣네엣다섯여섯일곱여덟아홉열열하나열둘열셋" //어중간한 한글 값일 때
//        "가나다라마바사아자차카타하일이삼사오육칠팔구십하나두울세엣네엣다섯여섯일곱여덟아홉여얼기억니은디긋리을미음비읍시옷이응지읏치읏키억티긋히읏" // 한글 길때
//        "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz" // 영어 길때
//        "abcdefghijklmnopqrstuvwxy가나다라마바사아자차카타하일이삼사오육칠팔구십하나두울세엣네엣다섯여섯일" // 영어 + 한글
            "///////////////////////////////////abcdefghijklmnopqrstuvwxyabcdefghijklmnopqrstuvwxy" // 한글 + 영어
        text_tv.moreText = "(10)"

        // 해당 한글을 찾아 pure length를 찾는다.
        // pure length 는(영어 or 숫자, asc 값 범위)기준 값을 말한다.
        // 영어 숫자 대비 한글의 경우 1.5 수준을 가진다.

    }
}