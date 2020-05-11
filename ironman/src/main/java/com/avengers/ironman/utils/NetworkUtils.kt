package com.avengers.ironman.utils

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object NetworkUtils {

    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            val adds = LinkedList<InetAddress>()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                if (!ni.isUp || ni.isLoopback) continue
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement())
                }
            }
            adds.forEach {
                if (!it.isLoopbackAddress) {
                    val hostAddress = it.hostAddress
                    val isIpv4 = hostAddress.indexOf(':') < 0
                    if (useIPv4) {
                        if (isIpv4) return hostAddress
                    } else {
                        if (!isIpv4) {
                            val index = hostAddress.indexOf('%')
                            return if (index < 0)
                                hostAddress.toUpperCase(Locale.ROOT)
                            else
                                hostAddress.substring(9, index).toUpperCase(Locale.ROOT)
                        }
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return ""
    }
}