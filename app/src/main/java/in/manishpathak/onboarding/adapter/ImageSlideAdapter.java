package in.manishpathak.onboarding.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import in.manishpathak.onboarding.R;
import in.manishpathak.onboarding.bean.Product;
import in.manishpathak.onboarding.fragment.HomeFragment;

public class ImageSlideAdapter extends PagerAdapter {
//	ImageLoader imageLoader = ImageLoader.getInstance();
//	DisplayImageOptions options;
//	private ImageLoadingListener imageListener;
	FragmentActivity activity;
	List<Product> products;
	HomeFragment homeFragment;

	public ImageSlideAdapter(FragmentActivity activity, List<Product> products,
							 HomeFragment homeFragment) {
		this.activity = activity;
		this.homeFragment = homeFragment;
		this.products = products;
//		options = new DisplayImageOptions.Builder()
//				.showImageOnFail(R.drawable.ic_error)
//				.showStubImage(R.drawable.first)
//				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory()
//				.cacheOnDisc().build();

//		imageListener = new ImageDisplayListener();
	}

	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.vp_image, container, false);

		ImageView mImageView = (ImageView) view
				.findViewById(R.id.image_display);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Bundle arguments = new Bundle();
//				Fragment fragment = null;
//				Log.d("position adapter", "" + position);
//				Product product = (Product) products.get(position);
//				arguments.putParcelable("singleProduct", product);
//
//				// Start a new fragment
//				fragment = new ProductDetailFragment();
//				fragment.setArguments(arguments);
//
//				FragmentTransaction transaction = activity
//						.getSupportFragmentManager().beginTransaction();
//				transaction.replace(R.id.content_frame, fragment,
//						ProductDetailFragment.ARG_ITEM_ID);
//				transaction.addToBackStack(ProductDetailFragment.ARG_ITEM_ID);
//				transaction.commit();
			}
		});
//		imageLoader.displayImage(
//				((Product) products.get(position)).getImageUrl(), mImageView,
//				options, imageListener);

//		imageLoader.displayImage("drawable://" + R.drawable.ic_launcher, mImageView,
//				options, imageListener);
		mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
		mImageView.setImageResource(((Product) products.get(position)).getImgresID());

		container.addView(view, 0);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

//	private static class ImageDisplayListener extends
//			SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
}