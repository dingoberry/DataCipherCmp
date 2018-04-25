package demo.cipher.tf.com.datacipherdemo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import demo.cipher.tf.com.datacipherdemo.algorithm.Algorithm
import demo.cipher.tf.com.datacipherdemo.algorithm.Interpreter
import demo.cipher.tf.com.datacipherdemo.utils.Base64Utils
import demo.cipher.tf.com.datacipherdemo.utils.Logger
import java.lang.StringBuilder

class ServerFragment : Fragment(), CommunicationMgr.OnReceiveCallback {

    private var mContent: TextView? = null

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
        val view = inflater!!.inflate(R.layout.fragment_server, container, false)
        mContent = view.findViewById(R.id.server_content)
        return view
    }

    override fun onReceive(obj: TalkObject) {
        val it = Interpreter.Factory.loadRefInterpreter(obj.tag!!)!!
        it.algorithm = Algorithm(null, Base64Utils.toByte(obj.deco!!))
        val sb = StringBuilder()
        sb.append("Algorithm:").append(obj.tag).append("\n")
                .append("Encode:").append(obj.msg).append("\n")
                .append("Content:").append(it.translateToLocal(obj.msg!!)).append("\n\n")
                .append("Key:").append(obj.deco!!).append("\n")
        mContent!!.text = sb.toString()
    }
}
