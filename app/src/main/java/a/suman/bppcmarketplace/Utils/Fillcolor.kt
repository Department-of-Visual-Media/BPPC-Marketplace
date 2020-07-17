package a.suman.bppcmarketplace.Utils

import a.suman.bppcmarketplace.R
import android.content.res.Resources
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View


fun fillCustomGradient(v: View, resources: Resources) {
    val layers = arrayOfNulls<Drawable>(1)
    val sf: ShapeDrawable.ShaderFactory = object : ShapeDrawable.ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return LinearGradient(
                0f,
                0f,
                0f,
                v.height.toFloat(), intArrayOf(
                    resources.getColor(
                        R.color.colorPrimaryDark,
                        null
                    ),  // please input your color from resource for color-4
                    resources.getColor(R.color.colorPrimaryDark, null),
                    resources.getColor(R.color.colorPrimary, null),
                    resources.getColor(R.color.design_default_color_background, null)
                ), floatArrayOf(0f, 0.6f, 0.8f, 1f),
                Shader.TileMode.CLAMP
            )
        }
    }
    val p = PaintDrawable()
    p.shape = RectShape()
    p.shaderFactory = sf
    p.setCornerRadii(floatArrayOf(5f, 5f, 5f, 5f, 0f, 0f, 0f, 0f))
    layers[0] = p as Drawable
    val composite = LayerDrawable(layers)
    v.background = composite
}