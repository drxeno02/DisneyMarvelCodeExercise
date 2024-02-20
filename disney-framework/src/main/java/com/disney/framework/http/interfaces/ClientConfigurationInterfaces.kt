package com.disney.framework.http.interfaces

import com.disney.framework.http.client.DisneyApiClient
import com.disney.framework.http.configuration.ClientConfiguration

interface ClientConfigurationInterfaces {
    /**
     * Returns [ClientConfiguration] information used by the [DisneyApiClient].
     *
     * @return [ClientConfiguration]
     */
    fun getClientConfiguration(): ClientConfiguration

    /**
     * Update client configuration.
     *
     * The updated changes only apply if [DisneyApiClient] is initialized.
     *
     * @param clientConfiguration REQUIRED: Configuration that collects information necessary to
     * perform request operations.
     */
    fun updateClientConfiguration(clientConfiguration: ClientConfiguration)
}
