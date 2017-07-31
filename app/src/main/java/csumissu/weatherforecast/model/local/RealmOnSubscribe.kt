package csumissu.weatherforecast.model.local

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.annotations.NonNull
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * @author yxsun
 * @since 31/07/2017
 */
abstract class RealmOnSubscribe<T> : FlowableOnSubscribe<T> {

    @Throws(Exception::class)
    override fun subscribe(@NonNull emitter: FlowableEmitter<T>) {
        val realm = Realm.getDefaultInstance()
        val obj: T?

        try {
            realm.beginTransaction()
            obj = get(realm)
            realm.commitTransaction()

            if (obj != null && !(obj is Unit || obj is Realm)) {
                emitter.onNext(copyFromRealm(realm, obj))
            }

            emitter.onComplete()
        } catch (error: Throwable) {
            realm.cancelTransaction()
            emitter.onError(error)
        } finally {
            realm.close()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun copyFromRealm(realm: Realm, obj: T): T {
        return when (obj) {
            is RealmObject -> realm.copyFromRealm(obj) as T
            is RealmList<*> -> realm.copyFromRealm(obj) as T
            is RealmResults<*> -> realm.copyFromRealm(obj) as T
            else -> obj
        }
    }

    abstract operator fun get(realm: Realm): T?
}
