package com.wuruoye.ichp.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

/**
 * Created by wuruoye on 2017/11/21.
 * this file is to
 */

class FragmentVPAdapter(
        private val mFM: FragmentManager,
        private val mTitleList: List<String>,
        private val mFragmentList: List<Fragment>)
    : FragmentStatePagerAdapter(mFM) {

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitleList.size == mFragmentList.size) {
            mTitleList[position]
        } else {
            super.getPageTitle(position)
        }
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        mFM.beginTransaction().show(fragment).commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = mFragmentList[position]
        mFM.beginTransaction().hide(fragment).commit()
    }
}
