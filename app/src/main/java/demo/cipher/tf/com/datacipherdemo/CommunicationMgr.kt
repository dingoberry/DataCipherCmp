package demo.cipher.tf.com.datacipherdemo

import android.net.LocalServerSocket
import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


object CommunicationMgr {

    private var mServerName: String? = null;
    private var mServerSocket: LocalServerSocket? = null
    private var mClientSocket: LocalSocket? = null

    private var mCommonHandler: Handler? = null;
    private var mServerHandler: Handler? = null;
    private var mClientHandler: Handler? = null;
    private var mServerLooper: HandlerThread? = null;
    private var mClientLooper: HandlerThread? = null;

    private var mCallbacks: CopyOnWriteArrayList<OnReceiveCallback> = CopyOnWriteArrayList();

    private fun processServerData(s: LocalSocket?) {
        if (null == s) {
            return;
        }

        val ins = s.inputStream;
        while (true) {
            val buffer = ByteArray(ins.read())
            if (-1 == ins.read(buffer)) {
                break
            }
            val ois = ObjectInputStream(ByteArrayInputStream(buffer))
            val tk: TalkObject = ois.readObject() as TalkObject;
            mCommonHandler?.obtainMessage(0, tk)?.sendToTarget();
        }
        s.close()
    }

    fun send(tk: TalkObject) {
        Logger.i("send=" + tk)
        mClientHandler?.obtainMessage(0, tk)?.sendToTarget()
    }

    private fun sendImpl(tk: TalkObject?): Boolean {
        if (null != tk) {
            if (null == mClientSocket) {
                mClientSocket = LocalSocket()
                mClientSocket?.connect(LocalSocketAddress(mServerName))
            }
            val bos = ByteArrayOutputStream()
            val ojs = ObjectOutputStream(bos)
            ojs.writeObject(tk)
            ojs.flush()

            val buffer = bos.toByteArray()
            val cos = mClientSocket?.outputStream;
            cos?.write(buffer.size)
            cos?.flush()
            cos?.write(buffer)
            cos?.flush()
        }
        Logger.i("sendImpl=" + tk)
        return true
    }

    private fun callbackImpl(tk: TalkObject?): Boolean {
        if (null != tk) {
            for (callback in mCallbacks) {
                callback.onReceive(tk)
            }
        }
        return false
    }

    fun init() {
        mServerName = UUID.randomUUID().toString()
        mServerSocket = LocalServerSocket(mServerName)

        mCommonHandler = Handler(Looper.getMainLooper(), Handler.Callback { msg: Message? -> callbackImpl(msg?.obj as TalkObject) })
        mServerLooper = HandlerThread(UUID.randomUUID().toString())
        mServerLooper?.start()
        mServerHandler = Handler(mServerLooper?.looper);
        mServerHandler?.post(object : Runnable {
            override fun run() {
                Logger.i("Looper to accept")
                processServerData(mServerSocket?.accept())
                mServerHandler?.post(this);
            }
        })

        mClientLooper = HandlerThread(UUID.randomUUID().toString())
        mClientLooper?.start()
        mClientHandler = Handler(mClientLooper?.looper, Handler.Callback { msg: Message? -> sendImpl(msg?.obj as TalkObject) })
    }

    fun destroy() {
        Logger.i("destroy")
        mServerLooper?.quit()
        mClientLooper?.quit()
        mServerSocket?.close()
        mClientSocket?.close()

        mServerName = null
        mServerSocket = null
        mClientSocket = null

        mCommonHandler = null
        mServerHandler = null
        mClientHandler = null
    }

    fun registerReceiveCallback(callback: OnReceiveCallback) {
        mCallbacks.add(callback)
    }

    fun unregisterReceiveCallback(callback: OnReceiveCallback) {
        mCallbacks.remove(callback)
    }

    interface OnReceiveCallback {
        fun onReceive(obj: TalkObject)
    }
}