package com.unsw.digitalid.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log

class StudentCredentialService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu == null) return NfcConstants.STATUS_FAILED

        Log.d("HCE", "Received APDU: ${commandApdu.toHex()}")

        return when {
            isSelectAid(commandApdu) -> {
                Log.d("HCE", "Select AID matched")
                NfcConstants.STATUS_SUCCESS
            }
            isGetData(commandApdu) -> {
                Log.d("HCE", "Get Data requested, sending token")
                NfcConstants.CREDENTIAL_TOKEN.toByteArray(Charsets.UTF_8) + NfcConstants.STATUS_SUCCESS
            }
            else -> {
                Log.d("HCE", "Unknown command")
                NfcConstants.STATUS_UNKNOWN_COMMAND
            }
        }
    }

    override fun onDeactivated(reason: Int) {
        Log.d("HCE", "Deactivated: $reason")
    }

    private fun isSelectAid(apdu: ByteArray): Boolean =
        apdu.size >= NfcConstants.SELECT_AID_COMMAND.size &&
            apdu.sliceArray(NfcConstants.SELECT_AID_COMMAND.indices)
                .contentEquals(NfcConstants.SELECT_AID_COMMAND)

    private fun isGetData(apdu: ByteArray): Boolean =
        apdu.size >= NfcConstants.GET_DATA_COMMAND.size &&
            apdu.sliceArray(NfcConstants.GET_DATA_COMMAND.indices)
                .contentEquals(NfcConstants.GET_DATA_COMMAND)

    private fun ByteArray.toHex(): String = joinToString("") { "%02X".format(it) }
}
