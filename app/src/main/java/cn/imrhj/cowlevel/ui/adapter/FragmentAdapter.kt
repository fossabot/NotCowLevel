package cn.imrhj.cowlevel.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import cn.imrhj.cowlevel.ui.fragment.FeedFragment
import cn.imrhj.cowlevel.ui.fragment.HotFeedFragment

/**
 * Created by rhj on 2017/11/28.
 */
class FragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mTabTitles = arrayOf("动态", "推荐", "BLANK")

    init {
        mFragmentList.add(FeedFragment())
        mFragmentList.add(HotFeedFragment())
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

    fun scrollToTop(position: Int) {
        val fragment = mFragmentList[position]
        if (fragment is RecyclerFragment<*>) {
            fragment.scrollToTop()
        }
    }

}