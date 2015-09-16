package in.manishpathak.onboarding.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import in.manishpathak.onboarding.R;
import in.manishpathak.onboarding.adapter.ImageSlideAdapter;
import in.manishpathak.onboarding.bean.Product;
import in.manishpathak.onboarding.json.GetJSONObject;
import in.manishpathak.onboarding.utils.CirclePageIndicator;
import in.manishpathak.onboarding.utils.CustomViewPager;
import in.manishpathak.onboarding.utils.PageIndicator;
import in.manishpathak.onboarding.utils.TagName;

public class HomeFragment extends Fragment {

	public static final String ARG_ITEM_ID = "home_fragment";

	private static final long ANIM_VIEWPAGER_DELAY = 5000;
	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

	// UI References
	private CustomViewPager mViewPager;
	private TextView imgNameTxt;
	private PageIndicator mIndicator;
	private int mIndicatorPosition;

	private AlertDialog alertDialog;

	private List<Product> products;
	private RequestImgTask task;
	private String message;

	private Button skipBtn;

	String url = "https://api.myjson.com/bins/558qt";
	FragmentActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		findViewById(view);

		mIndicator.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnSwipeOutListener(new CustomViewPager.OnSwipeOutListener() {
			@Override
			public void onSwipeOutAtEnd() {
				getActivity().finish();
			}
		});
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction()) {

					case MotionEvent.ACTION_CANCEL:
						break;

					case MotionEvent.ACTION_UP:
						// calls when touch release on ViewPager
						if (products != null && products.size() != 0) {
//						stopSliding = false;false
//						runnable(products.size());
//						handler.postDelayed(animateViewPager,
//								ANIM_VIEWPAGER_DELAY_USER_VIEW);
						}
						break;

					case MotionEvent.ACTION_MOVE:
						// calls when ViewPager touch
//					if (handler != null && stopSliding == false) {
//						stopSliding = true;
//						handler.removeCallbacks(animateViewPager);
//					}
						break;
				}
				return false;
			}
		});

		skipBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		return view;
	}

	private void findViewById(View view) {
		mViewPager = (CustomViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		imgNameTxt = (TextView) view.findViewById(R.id.img_name);
		skipBtn = (Button) view.findViewById(R.id.btn_skip);
	}

	@Override
	public void onResume() {
		if (products == null) {
			sendRequest();
		} else {
			mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
					HomeFragment.this));

			mIndicator.setViewPager(mViewPager);

//			runnable(products.size());
			//Re-run callback
//			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
		super.onResume();
	}

	
	@Override
	public void onPause() {
		if (task != null)
			task.cancel(true);
//		if (handler != null) {
//			//Remove callback
//			handler.removeCallbacks(animateViewPager);
//		}
		super.onPause();
	}

	private void sendRequest() {
		products = in.manishpathak.onboarding.json.JsonReader.getAllImages();

		mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
				HomeFragment.this));
		mIndicator.setViewPager(mViewPager);
//		imgNameTxt.setText(""
//				+ ((Product) products.get(mViewPager.getCurrentItem()))
//				.getName());
//		runnable(products.size());
		//Re-run callback
//		handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

//		if (CheckNetworkConnection.isConnectionAvailable(activity)) {
//			task = new RequestImgTask(activity);
//			task.execute(url);
//		} else {
//			message = getResources().getString(R.string.no_internet_connection);
//			showAlertDialog(message, true);
//		}
	}

	public void showAlertDialog(String message, final boolean finish) {
		alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (finish)
							activity.finish();
					}
				});
		alertDialog.show();
	}

	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == CustomViewPager.SCROLL_STATE_IDLE) {
				if (products != null) {
					Log.e("Switch", mIndicatorPosition + "");
					if(mViewPager.getCurrentItem() == products.size()-1){
						skipBtn.setText("CONTINUE");
						skipBtn.setBackgroundResource(R.drawable.btn_selector_continue);
						skipBtn.setTextColor(getResources().getColor(android.R.color.white));
					} else {
						skipBtn.setText("SKIP");
						skipBtn.setBackgroundResource(android.R.color.transparent);
						skipBtn.setTextColor(getResources().getColor(R.color.green_bullet));
					}
				}
			}

			if (state == CustomViewPager.SCROLL_STATE_DRAGGING) {
				Log.e("Switch", mIndicatorPosition + "");
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			Log.e("Onboarding onPageSelected", String.valueOf(position));
			mIndicatorPosition = position;
			if(mViewPager.getCurrentItem() == products.size()-1){
				skipBtn.setText("CONTINUE");
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//						LinearLayout.LayoutParams.MATCH_PARENT);
//				skipBtn.setLayoutParams(params);
				skipBtn.setBackgroundResource(R.drawable.btn_selector_continue);
				skipBtn.setTextColor(getResources().getColor(android.R.color.white));
			} else {
				skipBtn.setText("SKIP");
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//						LinearLayout.LayoutParams.WRAP_CONTENT);
//				skipBtn.setLayoutParams(params);
				skipBtn.setBackgroundResource(android.R.color.transparent);
				skipBtn.setTextColor(getResources().getColor(R.color.green_bullet));
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private class RequestImgTask extends AsyncTask<String, Void, List<Product>> {
		private final WeakReference<Activity> activityWeakRef;
		Throwable error;

		public RequestImgTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Product> doInBackground(String... urls) {
			try {
				JSONObject jsonObject = getJsonObject(urls[0]);
				if (jsonObject != null) {
					boolean status = jsonObject.getBoolean(TagName.TAG_STATUS);

					if (status) {
						JSONObject jsonData = jsonObject
								.getJSONObject(TagName.TAG_DATA);
						if (jsonData != null) {
							products = in.manishpathak.onboarding.json.JsonReader.getHome(jsonData);

						} else {
							message = jsonObject.getString(TagName.TAG_DATA);
						}
					} else {
						message = jsonObject.getString(TagName.TAG_DATA);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return products;
		}

		/**
		 * It returns jsonObject for the specified url.
		 * 
		 * @param url
		 * @return JSONObject
		 */
		public JSONObject getJsonObject(String url) {
			JSONObject jsonObject = null;
			try {
				jsonObject = GetJSONObject.getJSONObject(url);
			} catch (Exception e) {
			}
			return jsonObject;
		}

		@Override
		protected void onPostExecute(List<Product> result) {
			super.onPostExecute(result);

			if (activityWeakRef != null && !activityWeakRef.get().isFinishing()) {
				if (error != null && error instanceof IOException) {
					message = getResources().getString(R.string.time_out);
					showAlertDialog(message, true);
				} else if (error != null) {
					message = getResources().getString(R.string.error_occured);
					showAlertDialog(message, true);
				} else {
					products = result;
					if (result != null) {
						if (products != null && products.size() != 0) {

							mViewPager.setAdapter(new ImageSlideAdapter(
									activity, products, HomeFragment.this));

							mIndicator.setViewPager(mViewPager);
//							imgNameTxt.setText(""
//									+ ((Product) products.get(mViewPager
//											.getCurrentItem())).getName());
//							runnable(products.size());
//							handler.postDelayed(animateViewPager,
//									ANIM_VIEWPAGER_DELAY);
						} else {
							imgNameTxt.setText("No Products");
						}
					} else {
					}
				}
			}
		}
	}
}
