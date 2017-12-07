package cn.imrhj.cowlevel.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.imrhj.cowlevel.ui.fragment.BlankFragment
import cn.imrhj.cowlevel.ui.fragment.FeedFragment

/**
 * Created by rhj on 2017/11/28.
 */
class FragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
        private val mTabTitles = arrayOf("动态", "BLANK")

    init {
        mFragmentList.add(FeedFragment())
        mFragmentList.add(BlankFragment())
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}