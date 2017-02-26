package vn.com.phongnguyen93.readmee.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by phongnguyen on 8/31/16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
  private Fragment[] fragments;

  public FragmentAdapter(FragmentManager fm, Fragment[] fragments) {
    super(fm);
    if (fragments == null || fragments.length < 1) return;
    this.fragments = fragments;
  }

  @Override public Fragment getItem(int position) {
    return fragments[position];
  }

  @Override public int getCount() {
    return fragments != null ? fragments.length : 0;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "Tags";
      case 1:
        return "Begin date";
      case 2:
        return "More";
      default:
        return "";
    }
  }
}
