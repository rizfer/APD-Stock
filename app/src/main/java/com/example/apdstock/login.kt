package com.example.apdstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btn_regist.setOnClickListener{
            startActivity(Intent(this, daftar::class.java))
        }

        btn_lgn.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin() {
        if(tv_email.text.toString().isEmpty()){
            tv_email.error = "Mohon isi email"
            tv_email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(tv_email.text.toString()).matches()){
            tv_email.error = "Mohon isi email dengan benar"
            tv_email.requestFocus()
            return
        }
        if(tv_pass.text.toString().isEmpty()){
            tv_pass.error = "Mohon isi kta sandi"
            tv_pass.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(tv_email.text.toString(), tv_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser : FirebaseUser?) {
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "mohon verifikasi alamat email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}