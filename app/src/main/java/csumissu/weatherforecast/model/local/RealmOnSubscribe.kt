package csumissu.weatherforecast.model.local

import io.reactivex.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * @author yxsun
 * @since 16/10/2017
 */
abstract class RealmOnSubscribe<T> : FlowableOnSubscribe<T>, MaybeOnSubscribe<T>,
        ObservableOnSubscribe<T>, SingleOnSubscribe<T> {

    override fun subscribe(e: ObservableEmitter<T>) {
        subscribeInternal(e::onNext, e::onError, e::onComplete)
    }

    override fun subscribe(e: SingleEmitter<T>) {
        subscribeInternal(e::onSuccess, e::onError, {})
    }

    override fun subscribe(e: FlowableEmitter<T>) {
        subscribeInternal(e::onNext, e::onError, e::onComplete)
    }

    override fun subscribe(e: MaybeEmitter<T>) {
        subscribeInternal(e::onSuccess, e::onError, e::onComplete)
    }

    abstract operator fun get(realm: Realm): T?

    private inline fun subscribeInternal(body: (T) -> Unit,
                                         error: (Throwable) -> Unit,
                                         complete: () -> Unit) {
        val realm = Realm.getDefaultInstance()
        val obj: T?

        try {
            realm.beginTransaction()
            obj = get(realm)
            realm.commitTransaction()

            if (obj != null && !(obj is Unit || obj is Realm)) {
                body(copyFromRealm(realm, obj))
            }

            complete()
        } catch (t: Throwable) {
            realm.cancelTransaction()
            error(t)
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

}