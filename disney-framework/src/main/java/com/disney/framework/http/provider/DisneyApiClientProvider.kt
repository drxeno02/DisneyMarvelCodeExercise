package com.disney.framework.http.provider

import android.content.Context
import com.disney.framework.http.client.DisneyApiClient
import com.disney.framework.http.client.interfaces.DisneyApiClientInterfaces
import com.disney.framework.http.configuration.ClientConfiguration

private const val DESTROY_METHOD_JVM_NAME = "destroy"
internal const val ERROR_INSTANCE_ALREADY_INITIALIZED =
    "Start using the #getInstance() method since DisneyApiClient has already been initialized."
internal const val ERROR_INSTANCE_NOT_INITIALIZED =
    "Initialize the [DisneyApiClientProvider] first."

/**
 * A `singleton` instance provider for [DisneyApiClientInterfaces]. The client should call
 * {@linkplain #initialize()} to initialize the provider first before using {@linkplain #getInstance()}.
 *
 * @see [DisneyApiClientInterfaces]
 * @see [DisneyApiClient]
 */
object DisneyApiClientProvider {

    @Volatile
    private var INSTANCE: DisneyApiClientInterfaces? = null

    /**
     * Initialize [DisneyApiClient].
     *
     * @property context Interface to global information about an application environment.
     * @property clientConfiguration Contains necessary configuration data for making requests.
     */
    @JvmStatic
    @Throws(IllegalStateException::class)
    fun initialize(
        context: Context,
        clientConfiguration: ClientConfiguration
    ): DisneyApiClientInterfaces {
        if (INSTANCE == null) {
            synchronized(this) {
                // Assign the instance to local variable to check if it was initialized
                // by some other thread.
                // While current thread was blocked to enter the locked zone. If it was
                // initialized then we can return.
                val localInstance = INSTANCE
                if (localInstance == null) {
                    INSTANCE = DisneyApiClient(
                        context = context.applicationContext,
                        clientConfiguration = clientConfiguration
                    )
                }
            }
            return INSTANCE ?: throw IllegalStateException(ERROR_INSTANCE_NOT_INITIALIZED)
        } else {
            throw IllegalStateException(ERROR_INSTANCE_ALREADY_INITIALIZED)
        }
    }

    /**
     * Return a `singleton` instance of [DisneyApiClientInterfaces].
     */
    @JvmStatic
    fun getInstance(): DisneyApiClientInterfaces {
        return INSTANCE ?: throw IllegalStateException(ERROR_INSTANCE_NOT_INITIALIZED)
    }

    /**
     * ONLY FOR TESTING PURPOSES. Reset [INSTANCE] as null to help with unit tests.
     */
    @Synchronized
    @JvmStatic
    @JvmName(DESTROY_METHOD_JVM_NAME)
    internal fun destroy() {
        INSTANCE = null
    }
}
