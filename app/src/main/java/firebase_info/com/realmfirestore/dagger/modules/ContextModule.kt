package firebase_info.com.realmfirestore.dagger.modules

import android.content.Context
import android.view.Display
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import firebase_info.com.realmfirestore.RealmFirestoreApp
import javax.inject.Singleton

@Module
class ContextModule(private val application: RealmFirestoreApp) {

    /**
     * Allow the application context to be injected but require that it be annotated with [ ] to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}
