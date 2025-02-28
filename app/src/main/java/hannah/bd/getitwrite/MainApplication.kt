package hannah.bd.getitwrite

import android.app.Application
import hannah.bd.getitwrite.repository.FirebaseRepository

class MainApplication : Application() {

    lateinit var firebaseRepository: FirebaseRepository

    override fun onCreate() {
        super.onCreate()
        firebaseRepository = FirebaseRepository()
    }
}
