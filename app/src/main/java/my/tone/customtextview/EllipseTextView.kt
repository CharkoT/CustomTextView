package my.tone.customtextview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import java.lang.Integer.min

class EllipseTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attr, defStyleAttr) {

    var moreText = "(10)"
    var originText = ""

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                originText = text.toString()

                text = "${text} ${moreText}"

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
        Log.e(">>>>>>>>", "settext22 : ${text}")
        Log.e(">>>>>>>>", "settext length : ${text.length}")
        Log.e(">>>>>>>>", "lineCount22 : ${lineCount}")
        Log.e(">>>>>>>>", "viewLines : ${maxLines}")

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
                end += if(isEllipsis > 0) lineLength ?: 0 else text.length

                if (start < end && end <= text.length)
                    showText += originText.substring(start, Math.min(end, originText.length))
            }
        }

        if (originText.length > showText.length) {
            // ...
            showText = showText.substring(0, showText.length - (moreText.length + 4))

            text = "${showText}... ${moreText}"
        }

        Log.e(">>>>>>>>", "show Text : ${showText}")
        Log.e(">>>>>>>>", "originText : ${originText}")
        Log.e(">>>>>>>>", "originText.length : ${originText.length}")
        Log.e(">>>>>>>>", "showText.length : ${showText.length}")

    }
}