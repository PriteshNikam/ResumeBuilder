package com.example.buildresume.repository
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.buildresume.UserProfileDetails
import com.example.buildresume.data.Profile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// repository
class ProfileEditDetailsRepository @Inject constructor
    (@ApplicationContext private val context: Context){
    private val Context.datastore : DataStore<UserProfileDetails> by dataStore(
        fileName = "UserProfile",
        serializer = DataStoreProfile
    )

    suspend fun writeToLocal(userName:String,userMobile:String,userEmail:String,userAddress:String) = context.datastore.updateData{
        profile-> profile.toBuilder()
        .setUserName(userName)
        .setUserMobile(userMobile)
        .setUserEmail(userEmail)
        .setUserAddress(userAddress)
        .build()
    }

    val readToLocal: Flow<Profile> = context.datastore.data
        .catch {
            if(this is Exception){
                Log.d("profile","${this.message}")
            }
        }.map {
            val profile = Profile(it.userName,it.userMobile,it.userAddress,it.userEmail)
            profile
        }
}
