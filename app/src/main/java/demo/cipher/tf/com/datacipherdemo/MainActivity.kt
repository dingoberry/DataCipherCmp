package demo.cipher.tf.com.datacipherdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

class MainActivity : FragmentActivity() {

    private var mPagerAdapter : FragmentPagerAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        mPagerAdapter = InnerAdapter(supportFragmentManager)
        val pager : ViewPager = findViewById(R.id.main_container)
        pager.adapter = mPagerAdapter
        CommunicationMgr.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        CommunicationMgr.destroy()
    }

    class InnerAdapter constructor(fm : FragmentManager): FragmentPagerAdapter(fm) {

        private val mFragments : Array<Fragment> = arrayOf(ClientFragment(), ServerFragment())

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }

}
