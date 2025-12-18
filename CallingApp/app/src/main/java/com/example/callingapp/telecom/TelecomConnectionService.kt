package com.example.callingapp.telecom

import android.os.Build
import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle
import androidx.annotation.RequiresApi

/**
 * This service is the bridge between your app and the Android Telecom framework.
 * It's responsible for creating and managing `Connection` objects, which represent
 * active calls. This is an advanced and critical component for any app that wants
 * to be a default phone handler.
 *
 * NOTE: This is a skeleton implementation. A full implementation requires handling
 * call states (active, hold, disconnected), audio routing, and connecting to a
 * persistent in-call UI.
 */
class MyConnectionService : ConnectionService() {

    override fun onCreateOutgoingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        // Called when your app initiates an outgoing call.
        // Here you would:
        // 1. Create your custom `MyConnection` class.
        // 2. Set the connection state to 'Dialing'.
        // 3. Connect to your in-call UI.
        // 4. Interface with your call backend (e.g., SIP, WebRTC, or cellular).
        return super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request)
    }

    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        // Called by the system when there is an incoming call for your app's PhoneAccount.
        // Here you would:
        // 1. Create your custom `MyConnection` class.
        // 2. Set the connection state to 'Ringing'.
        // 3. Post a notification for the incoming call or show a full-screen UI.
        return super.onCreateIncomingConnection(connectionManagerPhoneAccount, request)
    }

    override fun onCreateIncomingConnectionFailed(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ) {
        // Called when an incoming call fails before it can be connected.
        super.onCreateIncomingConnectionFailed(connectionManagerPhoneAccount, request)
    }

    override fun onCreateOutgoingConnectionFailed(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ) {
        // Called when an outgoing call fails before it can be connected.
        super.onCreateOutgoingConnectionFailed(connectionManagerPhoneAccount, request)
    }
}

// You would create a custom Connection class to manage call state.
class MyConnection() : Connection() {
    // Implement onShowIncomingCallUi, onHold, onAnswer, onDisconnect, etc.
}
