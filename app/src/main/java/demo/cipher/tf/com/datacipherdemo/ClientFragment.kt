package demo.cipher.tf.com.datacipherdemo

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

class ClientFragment : Fragment(), View.OnClickListener {

    private var mSelector: TextView? = null
    private var mData: TextView? = null
    private var mPair: KeyPair? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.i("ClientFragment")
        val v = inflater!!.inflate(R.layout.fragment_client, container, false)
        val selector = v.findViewById<TextView>(R.id.client_selecter)
        v.findViewById<Button>(R.id.client_send).setOnClickListener(this)
        selector.setOnClickListener(this)
        mSelector = selector
        mData = v.findViewById(R.id.client_data)
        v.findViewById<EditText>(R.id.client_edit).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                processMessage(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })
        return v;
    }

    private fun processMessage(s: CharSequence?) {
        val value = mSelector?.text.toString();
        if (TextUtils.isEmpty(value)) {
            return
        }

        val c = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        c.init(Cipher.ENCRYPT_MODE, mPair?.private)
        val b = c.doFinal(s.toString().toByteArray())
        mData?.text = Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun process(dialog: DialogInterface, which: Int) {
        dialog.dismiss();
        mSelector!!.text = Config.CIPHER_LIST[which];
        mPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.client_selecter -> {
                val builder = AlertDialog.Builder(context);
                builder.setItems(Config.CIPHER_LIST, DialogInterface.OnClickListener { dialog, which -> process(dialog, which) })
                builder.create().show();
            }
            R.id.client_send -> {
                CommunicationMgr.send(TalkObject("123", "23"))
            }
        }
    }
}
