package firebase_info.com.realmfirestore.dagger.modules

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by konstantinkochetov on 11.03.18.
 */
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun providesFirestore(context: Context): FirebaseFirestore {
        // Set Up Firebase
        return FirebaseFirestore.getInstance()
    }

}