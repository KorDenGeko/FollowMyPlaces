package com.example.followmyplaces

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase

object GoogleServices {
    private var signInOptions:GoogleSignInOptions? = null

    init {

        signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("640481620245-27607n31ft3mg64uvknc063eflkj6bah.apps.googleusercontent.com")
                .requestEmail()
                .build()
    }



    fun getAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    fun getSignInOptions() = signInOptions


}
