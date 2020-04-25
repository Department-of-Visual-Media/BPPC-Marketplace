package a.suman.bppcmarketplace.ProductList.View

import a.suman.bppcmarketplace.R
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_product_list.*


class ProductListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillCustomGradient(view.findViewById(R.id.appbar_product_list))
    }

    private fun fillCustomGradient(v: View) {
        val layers = arrayOfNulls<Drawable>(1)
        val sf: ShaderFactory = object : ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(
                    0f,
                    0f,
                    0f,
                    v.height.toFloat(), intArrayOf(
                        resources.getColor(R.color.colorPrimaryDark, null),  // please input your color from resource for color-4
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
        v.background =composite
    }

}
