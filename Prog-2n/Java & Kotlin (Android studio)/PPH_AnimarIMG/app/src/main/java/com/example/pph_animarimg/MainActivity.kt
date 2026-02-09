package com.example.pph_animarimg

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var loopAnimator: ObjectAnimator

    // Llista d'imatges
    private val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3   // pots afegir més
    )
    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#121212"))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        img = ImageView(this).apply {
            setImageResource(images[currentImageIndex])
            layoutParams = LinearLayout.LayoutParams(400, 400)
        }

        val buttonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }

        fun makeButton(text: String, action: () -> Unit): Button {
            return Button(this).apply {
                this.text = text
                setOnClickListener { action() }
            }
        }

        // Botons animació
        val btnX = makeButton("Moviment X") { moveX() }
        val btnY = makeButton("Moviment Y") { moveY() }
        val btnAlpha = makeButton("Transparència") { alphaAnim() }
        val btnRotate = makeButton("Rotació") { rotate() }
        val btnCombo = makeButton("X + Rotació + Alpha") { combo() }
        val btnScale = makeButton("Escalat") { scale() }
        val btnLoop = makeButton("Bucle X") { loopX() }
        val btnReset = makeButton("Reset") { reset() }

        // Botó canvi imatge
        val btnChangeImg = makeButton("Canviar imatge") { changeImage() }

        root.addView(img)
        buttonsLayout.addView(btnX)
        buttonsLayout.addView(btnY)
        buttonsLayout.addView(btnAlpha)
        buttonsLayout.addView(btnRotate)
        buttonsLayout.addView(btnCombo)
        buttonsLayout.addView(btnScale)
        buttonsLayout.addView(btnLoop)
        buttonsLayout.addView(btnChangeImg)
        buttonsLayout.addView(btnReset)
        root.addView(buttonsLayout)

        setContentView(root)
    }

    // ===== Animacions =====

    private fun moveX() {
        ObjectAnimator.ofFloat(img, "translationX", 0f, 500f).apply {
            duration = 1000
            start()
        }
    }

    private fun moveY() {
        ObjectAnimator.ofFloat(img, "translationY", 0f, 500f).apply {
            duration = 1000
            start()
        }
    }

    private fun alphaAnim() {
        ObjectAnimator.ofFloat(img, "alpha", 1f, 0f).apply {
            duration = 1000
            start()
        }
    }

    private fun rotate() {
        ObjectAnimator.ofFloat(img, "rotation", 0f, 360f).apply {
            duration = 1000
            start()
        }
    }

    private fun combo() {
        val moveX = ObjectAnimator.ofFloat(img, "translationX", 0f, 500f)
        val rotate = ObjectAnimator.ofFloat(img, "rotation", 0f, 360f)
        val alpha = ObjectAnimator.ofFloat(img, "alpha", 1f, 0f)

        AnimatorSet().apply {
            duration = 1500
            playTogether(moveX, rotate, alpha)
            start()
        }
    }

    private fun scale() {
        val scaleX = ObjectAnimator.ofFloat(img, "scaleX", 0.6f, 1.6f)
        val scaleY = ObjectAnimator.ofFloat(img, "scaleY", 0.6f, 1.6f)

        AnimatorSet().apply {
            duration = 1000
            playTogether(scaleX, scaleY)
            start()
        }
    }

    private fun loopX() {
        loopAnimator = ObjectAnimator.ofFloat(img, "translationX", 0f, 500f).apply {
            duration = 800
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun reset() {
        if (::loopAnimator.isInitialized) loopAnimator.cancel()

        img.animate().apply {
            translationX(0f)
            translationY(0f)
            rotation(0f)
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
            duration = 500
            start()
        }
    }

    // ===== Canvi d'imatge =====

    private fun changeImage() {
        currentImageIndex = (currentImageIndex + 1) % images.size
        img.setImageResource(images[currentImageIndex])
    }
}
