package my.tone.customtextview

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.toSpannable
import java.lang.Integer.min

/**
 * ellipsis 기능 필수 사용되어야 함 end 기준.
 * maxline 생성해주어야함.
 */
class EllipseTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attr, defStyleAttr) {

    var moreText: String? = null
    var originText = ""

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                originText = text.toString()

                text = moreText?.let {
                    "$text $moreText"
                } ?: text

                if (maxLines >= lineCount)
                    addCustomMore()

                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    /**
     * 라인수를 체크
     * 생략이 시작된 라인에서 축약된 글자 길이를 확인한다.
     * 확인된 글자 사이즈에서 추가할 텍스트 만큼을 자른다.
     * text : 추가할 글자를 붙여 생략되는지 유무를 확인한다.
     * originText : 원본 텍스트
     * text 기준으로 자름 유무를 체크 한 후
     * originText 기준으로 자른다.
     * 그 후 추가할 텍스트를 재 추가한다.
     * 생략되지 않을 경우 ... 넣지 않는다.
     * 생략될 경우 ... 넣는다.
     */
    private fun addCustomMore() {
        var start = 0
        var end = 0
        var showText = ""
        var lineLength: Int? = null

        for (i in 0 until maxLines) {
            if (i < maxLines - 1) {
                end = layout.getLineEnd(i)
                if (start < end && end <= text.length)
                    showText += originText.substring(start, Math.min(end, originText.length))

                if (lineLength == null) lineLength = end
                start = end
            } else {
                var isEllipsis = layout.getEllipsisCount(i)

                end = originText.length - isEllipsis

                if (start < end && end <= originText.length)
                    showText += originText.substring(start, Math.min(end, originText.length))

                text = showText
            }
        }


        if (originText.length > showText.length) {
            text = "$showText...${moreText?.let { " $moreText" } ?: ""}"
        } else text = "$showText${moreText?.let { " $moreText" } ?: ""}"


        moreText?.let {
            text = moreSpan(it)
        }
    }

    private fun moreSpan(moreText: String): Spannable {
        var span = text.toSpannable()

        span.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF8983")),
            text.length - moreText.length,
            text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return span
    }
}