package com.app.whisperly.view.signin

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.whisperly.model.Country
import com.app.whisperly.util.LogInFailedState
import com.app.whisperly.util.toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    private val verificationId: MutableLiveData<String> = MutableLiveData()

    private val credential: MutableLiveData<PhoneAuthCredential> = MutableLiveData()

    private val taskResult: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    private val failedState: MutableLiveData<LogInFailedState> = MutableLiveData()

    private val auth = FirebaseAuth.getInstance()

    fun setMobile(country: Country, mobile: String, actContext:Context) {
        val number = country.noCode + " " + mobile
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(actContext as Activity)
            .setCallbacks(this)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        this.credential.value = credential
        signInWithPhoneAuthCredential(credential)
    }

    override fun onVerificationFailed(exp: FirebaseException) {
        failedState.value = LogInFailedState.Verification
        when (exp) {
            is FirebaseAuthInvalidCredentialsException ->
                context.toast("Invalid Request")
            else -> context.toast(exp.message.toString())
        }
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        this.verificationId.value = verificationId
        context.toast("Verification code sent successfully")
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    taskResult.value = task
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                        context.toast("Invalid verification code!")
                    failedState.value = LogInFailedState.SignIn
                }
            }
    }
    fun setCredential(credential: PhoneAuthCredential) {
        signInWithPhoneAuthCredential(credential)
    }

    fun getVerificationCode(): MutableLiveData<String> {
        return verificationId
    }

    fun clearVerificationCode() {
        verificationId.value = null
    }

    fun clearOldAuth(){
        credential.value=null
        taskResult.value=null
    }

    fun getCredential(): LiveData<PhoneAuthCredential> {
        return credential
    }

    fun getTaskResult(): LiveData<Task<AuthResult>> {
        return taskResult
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return failedState
    }
}