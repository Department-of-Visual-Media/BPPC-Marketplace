package a.suman.bppcmarketplace.UsersList.View

import a.suman.bppcmarketplace.R
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_users_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UsersListFragment : Fragment() {

    var animation:Job= Job()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillCustomGradient(view.findViewById(R.id.appbar_users_list))
        animation= MainScope().launch {
            while(true){noInternetUser.animate().alpha(0f).duration = 500
                delay(500)
                noInternetUser.animate().alpha(1f).duration = 500
                delay(600)
            }
        }
    }

    private fun fillCustomGradient(v: View) {
        val layers = arrayOfNulls<Drawable>(1)
        val sf: ShapeDrawable.ShaderFactory = object : ShapeDrawable.ShaderFactory() {
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
        v.background= composite
    }

    override fun onStop() {
        super.onStop()
        animation.cancel()
    }

}
