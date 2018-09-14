package cn.imrhj.cowlevel.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment

/**
 * Created by rhj on 2017/11/28.
 */
class FragmentAdapter(fm: FragmentManager?, list: List<Fragment>, titleList: List<String>) : FragmentPagerAdapter(fm) {
    private var mFragmentList = list
    private var mTabTitles = titleList

    override fun getPageTitle(position: Int): CharSequence {
        return mTabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun scrollToTop(position: Int) {
        val fragment = mFragmentList[position]
        if (fragment is RecyclerFragment<*>) {
            fragment.scrollToTop()
        }
    }

    fun update(titleList: List<String>, fragmentList: List<Fragment>) {
        this.mTabTitles = titleList
        this.mFragmentList = fragmentList
        notifyDataSetChanged()
    }
}