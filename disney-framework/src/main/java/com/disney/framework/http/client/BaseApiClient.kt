package com.disney.framework.http.client

import android.os.Handler
import android.os.Looper
import com.disney.framework.constants.Constants
import com.disney.framework.http.configuration.base.BaseClientConfiguration
import com.disney.framework.http.interfaces.HttpRequestExecutor
import com.disney.framework.http.interfaces.HttpResponseCallback
import com.disney.framework.http.okhttp.OkHttpRequestExecutor
import com.disney.framework.http.responses.internal.EmptyStateInfo
import com.disney.framework.http.responses.internal.ErrorItem
import com.disney.framework.http.responses.internal.HttpStatusCode
import com.disney.framework.http.responses.internal.Response
import com.disney.framework.http.responses.internal.ResponseCallback
import com.disney.framework.http.responses.internal.ResponseItem
import com.disney.framework.logger.Logger
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * This abstract class encapsulates HTTP logic.
 *
 * @param T : BaseClientConfiguration Used to abstract the attributes used in HTTP request in.
 * @property configuration This property contains necessary info to make the HTTP requests.
 * @property okHttpRequestExecutor This interface establishes a common contract for hiding HTTP
 * library dependencies.
 * @property handler Class used to run a message loop for a thread
 * @property gson This is the main class for using Gson.
 */
abstract class BaseApiClient<T : BaseClientConfiguration>(
    protected var configuration: T,
    protected val okHttpRequestExecutor: HttpRequestExecutor = OkHttpRequestExecutor(),
    private val handler: Handler = Handler(Looper.getMainLooper()),
    protected var gson: Gson = Gson()
) {
    /**
     * FOR TESTING ONLY
     * Causes the Runnable to be added to the message queue. The runnable will be run on
     * the thread to which a handler is attached.
     */
    protected var postRunnableHook: () -> Unit = {}

    /**
     * Listen for the [HttpResponseCallback] and update [ResponseCallback] accordingly.
     *
     * @param T Generic type parameter.
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param identifier Description text or label for the HTTP request.
     */
    protected inline fun <reified T : EmptyStateInfo> getHttpResponseCallback(
        emptyResponse: T,
        responseCallback: ResponseCallback<T>?,
        identifier: String? = null
    ) = object : HttpResponseCallback {
        override fun onSuccess(responseItem: ResponseItem) {
            handleValidHttpResponse(
                responseItem = responseItem,
                emptyResponse = emptyResponse,
                tClass = T::class.java,
                identifier = identifier,
                responseCallback = responseCallback,
            )
        }

        override fun onFailure(errorItem: ErrorItem) {
            handleHttpResponseFailure(
                errorItem = errorItem,
                responseCallback = responseCallback,
                identifier = identifier
            )
        }

        override fun onCancelled() {
            // no-op
        }
    }

    /**
     * Handle callbacks for [ResponseCallback] when a HTTP request concludes successfully.
     *
     * @param responseItem HTTP response item.
     * @param emptyResponse Empty object for [T].
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param tClass Generic request class [T::class].
     * @param identifier description text or label for the HTTP request.
     */
    protected fun <T : EmptyStateInfo> handleValidHttpResponse(
        responseItem: ResponseItem,
        emptyResponse: T,
        responseCallback: ResponseCallback<T>?,
        tClass: Class<T>,
        identifier: String? = null
    ) {
        when (responseItem) {
            is ResponseItem.StringResponseItem -> {
                try {
                    val responseData = gson.fromJson(responseItem.response, tClass)
                    // handle valid HTTP response
                    handleResponseSuccess(
                        httpStatusCode = responseItem.statusCode,
                        responseData,
                        responseCallback = responseCallback,
                        identifier = identifier
                    )
                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                    Logger.e(Constants.TAG, e.message, e)
                    // handle non-HTTP failure
                    handleNonHttpFailure(
                        exception = e,
                        responseCallback = responseCallback,
                        identifier = identifier
                    )
                }
            }

            is ResponseItem.EmptyResponseItem -> {
                // handle valid empty HTTP response
                handleResponseSuccess(
                    httpStatusCode = responseItem.statusCode,
                    responseData = emptyResponse,
                    responseCallback = responseCallback,
                    identifier = identifier
                )
            }
        }
    }

    /**
     * Handle callbacks for [ResponseCallback] when a response succeeds.
     *
     * @param httpStatusCode Represents an HTTP status with code and message.
     * @param responseData Response data.
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param identifier description text or label for the HTTP request.
     */
    private fun <T : EmptyStateInfo> handleResponseSuccess(
        httpStatusCode: HttpStatusCode,
        responseData: T,
        responseCallback: ResponseCallback<T>?,
        identifier: String? = null
    ) = notifyWithHandler {
        responseCallback?.onSuccess(
            Response.Success(
                httpStatusCode = httpStatusCode,
                response = responseData,
                identifier = identifier
            )
        )
    }

    /**
     * Handle callbacks for [ResponseCallback] when a response fails.
     *
     * @param errorItem Distinguishes between a runtime error and a failed HTTP response.
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param identifier description text or label for the HTTP request.
     */
    private fun <T : EmptyStateInfo> handleResponseFailure(
        errorItem: ErrorItem,
        responseCallback: ResponseCallback<T>?,
        identifier: String? = null
    ) = notifyWithHandler {
        responseCallback?.onFailure(
            Response.Failure(
                errorItem = errorItem,
                identifier = identifier
            )
        )
    }

    /**
     * Handle non-Http failures and callbacks for failures.
     *
     * @param exception An object that wraps an error event that occurred and contains information
     * about the error including its type.
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param identifier description text or label for the HTTP request.
     */
    private fun <T : EmptyStateInfo> handleNonHttpFailure(
        exception: Exception,
        responseCallback: ResponseCallback<T>?,
        identifier: String? = null
    ) {
        handleResponseFailure(
            errorItem = ErrorItem.GenericErrorItem(exception),
            responseCallback = responseCallback,
            identifier = identifier
        )
    }

    /**
     * Handle HTTP failures and callbacks for failures.
     *
     * @param errorItem Distinguishes between a runtime error and a failed HTTP response.
     * @param responseCallback Callback to notify call-site of `onSuccess` and `onFailure` events.
     * @param identifier description text or label for the HTTP request.
     */
    protected fun <T : EmptyStateInfo> handleHttpResponseFailure(
        errorItem: ErrorItem,
        responseCallback: ResponseCallback<T>?,
        identifier: String? = null
    ) {
        handleResponseFailure(
            errorItem = errorItem,
            responseCallback = responseCallback,
            identifier = identifier
        )
    }

    /**
     * Wrap [action] around [Handler]'s post call.
     */
    protected fun notifyWithHandler(action: () -> Unit) = handler.post { action() }
        .also { postRunnableHook() }
}
