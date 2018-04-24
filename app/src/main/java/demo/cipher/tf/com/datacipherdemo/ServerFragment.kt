package demo.cipher.tf.com.datacipherdemo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ServerFragment : Fragment(), CommunicationMgr.OnReceiveCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommunicationMgr.registerReceiveCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        CommunicationMgr.unregisterReceiveCallback(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.i("ServerFragment")
        return inflater!!.inflate(R.layout.fragment_server, container, false)
    }

    override fun onReceive(obj: TalkObject) {
        Logger.i("ServerFragment:${obj.msg}")
    }
}
