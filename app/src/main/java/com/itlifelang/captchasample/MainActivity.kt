package com.itlifelang.captchasample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            verifyWithCaptcha(it)
        }
    }

    private fun verifyWithCaptcha(view: View) {
        SafetyNet.getClient(this)
            .verifyWithRecaptcha("6Lf2qJ8aAAAAAEhxCvO7XK_dXoflljgl_yWCYD2D")
            .addOnSuccessListener {
                if (it.tokenResult.isNullOrEmpty().not()) {
                    // Indicate communication with reCAPTCHA service was successful.
                    Snackbar.make(
                        view,
                        "Communicate with reCAPTCHA service successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener(this) { e ->
                if (e is ApiException) {
                    // An error occurred when communicating with the
                    // reCAPTCHA service. Refer to the status code to
                    // handle the error appropriately
                    Snackbar.make(
                        view,
                        CommonStatusCodes.getStatusCodeString(e.statusCode),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(view, e.message ?: "", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
}
