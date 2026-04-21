package com.unsw.digitalid.nfc

object NfcConstants {
    const val CREDENTIAL_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdHVkZW50TnVtYmVyIjoiejk5OTk5OTEiLCJ6SWQiOiJ6OTk5OTk5MSIsImZhY3VsdHkiOiJFbmdpbmVlcmluZyIsInByb2dyYW0iOiJDb21wdXRlciBTY2llbmNlIiwiaXNzdWVEYXRlIjoiMjAyNi0wNC0xNlQwODo1Njo1NC40Mjg0NDIrMDA6MDAiLCJleHBpcnlEYXRlIjoiMjAyNy0wNC0xNlQwODo1Njo1NC40Mjg0NDIrMDA6MDAiLCJjcmVkZW50aWFsSWQiOiJmZTQyYTk0ZC1kZjYyLTQxY2MtOGU5My01ZDMwNTlmNDA0YzciLCJpYXQiOjE3NzYzMjk4MTQsImV4cCI6MTgwNzg2NTgxNH0.cK0sBOm8S5U6P2P0o_C0M5RAGj5mQ3DVNdCIJ9_VSYo"

    val SELECT_AID_COMMAND = byteArrayOf(
        0x00.toByte(),
        0xA4.toByte(),
        0x04.toByte(),
        0x00.toByte(),
    )

    val GET_DATA_COMMAND = byteArrayOf(
        0x00.toByte(),
        0xCA.toByte(),
        0x00.toByte(),
        0x00.toByte(),
    )

    val STATUS_SUCCESS         = byteArrayOf(0x90.toByte(), 0x00.toByte())
    val STATUS_FAILED          = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    val STATUS_UNKNOWN_COMMAND = byteArrayOf(0x6D.toByte(), 0x00.toByte())
}
