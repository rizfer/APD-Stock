package com.example.apdstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class daftar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        btn_rg_regist.setOnClickListener{
            registeruser()
        }
        btn_back.setOnClickListener{
            startActivity(Intent(this,login::class.java))
            finish()
        }
    }

    private fun registeruser(){
        if(rg_username.text.toString().isEmpty()){
            rg_username.error = "Mohon isi nama pengguna"
            rg_username.requestFocus()
            return
        }
        if(rg_email.text.toString().isEmpty()){
            rg_email.error = "Mohon isi email"
            rg_email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(rg_email.text.toString()).matches()){
            rg_email.error = "Mohon isi email dengan benar"
            rg_email.requestFocus()
            return
        }
        if(rg_pass.text.toString().isEmpty()){
            rg_pass.error = "Mohon isi kata sandi"
            rg_pass.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(rg_email.text.toString(), rg_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this,login::class.java))
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(baseContext, "Daftar Gagal. Silahkan coba lagi beberapa waktu",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
