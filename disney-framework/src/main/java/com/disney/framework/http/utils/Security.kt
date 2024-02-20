package com.disney.framework.http.utils

import java.math.BigInteger
import java.security.MessageDigest

private const val MD5 = "MD5"

/**
 * The MD5 (message-digest algorithm) hashing algorithm is a one-way cryptographic function that
 * accepts a message of any length as input and returns as output a fixed-length digest value to
 * be used for authenticating the original message.
 *
 * <p>Note: MD5 has been deprecated for uses other than as a non-cryptographic checksum to verify
 * data integrity and detect unintentional data corruption.</p>
 *
 * @param input Message to hash.
 * @return MD5 hash of the input message.
 */
fun md5(input: String): String {
    val md = MessageDigest.getInstance(MD5)
    return BigInteger(1, md.digest(input.toByteArray()))
        .toString(16)
        .padStart(32, '0')
}
