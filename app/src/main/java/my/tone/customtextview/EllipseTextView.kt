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

    // 연달아 작성될 때 기대값
    // 1줄이거나, 두줄인데 축약이 되지 않았을 때 ... 처리
    // 축약시 ... 처리를 어떻게 진행?
    // 영어 한글 혼용할때
    // 연속된 글자 작성시

    private fun addCustomMore() {
        var start = 0
        var end = 0
        var showText = ""
        var lineLength: Int? = null

        for (i in 0 until maxLines) {
            if (i < maxLines - 1) {
                end = layout.getLineEnd(i)
                if (start < end && end <= text.length)
                    showText += text.substring(start, end)

                if (lineLength == null) lineLength = end
                start = end
            } else {
                var isEllipsis = layout.getEllipsisCount(i)
                end += if (isEllipsis > 0) lineLength ?: 0 else {
                    showText = text.toString()
                    break
                }

                if (start < end && end <= text.length)
                    showText += text.substring(start, Math.min(end, originText.length))
            }
        }

        if (originText.length >= showText.length) {
            showText = showText.substring(0, showText.length - (moreText?.length ?: -1 + 4))

            text = "$showText...${moreText?.let { " $moreText" } ?: ""}"
        }


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