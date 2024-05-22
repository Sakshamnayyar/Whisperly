package com.app.whisperly.view.signin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.whisperly.model.Country
import com.app.whisperly.model.ModelMobile
import com.app.whisperly.model.UserProfile
import com.app.whisperly.repository.SignInRepository
import com.app.whisperly.util.Countries
import com.app.whisperly.util.LogInFailedState
import com.app.whisperly.util.MPreference
import com.app.whisperly.util.toast
import com.app.whisperly.view.navigation.RouteNavigator
import com.app.whisperly.view.profileScreen.ProfileScreenRoute
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInRepo: SignInRepository,
    private val preference: MPreference,
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator {

    val country = MutableLiveData(Countries.getDefaultCountry())

    val mobile = MutableLiveData<String>()

    val userProfileExists = MutableLiveData<Boolean>()

    val otpOne = MutableLiveData<String>()

    val otpTwo = MutableLiveData<String>()

    val otpThree = MutableLiveData<String>()

    val otpFour = MutableLiveData<String>()

    val otpFive = MutableLiveData<String>()

    val otpSix = MutableLiveData<String>()

    var editPosition = 0

    fun validateOtp(){
        val otp = getOtpValue()
        //TODO: check for no Internet
        if(otp.length!=6)context.toast("Enter valid otp")
        val credential = PhoneAuthProvider.getCredential(signInRepo.getVerificationCode().value!!,otp)
        signInRepo.setCredential(credential)

//        navigateToRoute(HomeScreenRoute.route)
    }

    private fun getOtpValue(): String {
        return otpOne.value+otpTwo.value+otpThree.value+otpFour.value+otpFive.value+otpSix.value
    }



    fun startVerification(){
//        context.toast("SignInViewModel(): Continue clicked.")
        navigateToRoute(VerifyRoute.route)
    }

    fun setCountry(country: Country) {
        this.country.value = country
    }

    fun setMobile(actContext: Context) {
        signInRepo.clearOldAuth()
        saveMobile()
        signInRepo.setMobile(country.value!!, mobile.value!!,actContext)
    }

    fun setEmptyText(){
        otpOne.value=""
        otpTwo.value=""
        otpThree.value=""
        otpFour.value=""
        otpFive.value=""
        otpSix.value=""
    }

    fun getCredential(): LiveData<PhoneAuthCredential> {
        return signInRepo.getCredential()
    }

    fun setCredential(credential: PhoneAuthCredential) {
        signInRepo.setCredential(credential)
    }

    fun getVerificationId(): MutableLiveData<String> {
        return signInRepo.getVerificationCode()
    }

    fun getTaskResult(): LiveData<Task<AuthResult>> {
        return signInRepo.getTaskResult()
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return signInRepo.getFailed()
    }

    private fun saveMobile() =
        preference.saveMobile(ModelMobile(country.value!!.noCode,mobile.value!!))

    fun fetchUser(taskId: Task<AuthResult>) {
        val db = FirebaseFirestore.getInstance()
        val user = taskId.result?.user
        val noteRef = db.document("Users/" + user?.uid)
        noteRef.get()
            .addOnSuccessListener { data ->
                preference.setUid(user?.uid.toString())
                preference.setLogin()
                preference.setLogInTime()
                if (data.exists()) {
                    val appUser = data.toObject(UserProfile::class.java)
                    preference.saveProfile(appUser!!)
                    //if device id is not same,send new_user_logged type notification to the token

                }
                navigateToRoute(ProfileScreenRoute.route)
            }.addOnFailureListener { e ->
                context.toast(e.message.toString())
            }
    }
    fun clearAll() {
        userProfileExists.value = false
        signInRepo.clearOldAuth()
    }
}