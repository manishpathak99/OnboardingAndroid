package in.manishpathak.onboarding.json;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.manishpathak.onboarding.R;
import in.manishpathak.onboarding.bean.Product;
import in.manishpathak.onboarding.utils.TagName;

public class JsonReader {

	public static List<Product> getHome(JSONObject jsonObject)
			throws JSONException {
		List<Product> products = new ArrayList<Product>();

		JSONArray jsonArray = jsonObject.getJSONArray(TagName.TAG_PRODUCTS);
		Product product;
		for (int i = 0; i < jsonArray.length(); i++) {
			product = new Product();
			JSONObject productObj = jsonArray.getJSONObject(i);
			product.setId(productObj.getInt(TagName.KEY_ID));
			product.setName(productObj.getString(TagName.KEY_NAME));
			product.setImageUrl(productObj.getString(TagName.KEY_IMAGE_URL));

			products.add(product);
		}
		return products;
	}

	public static List<Product> getAllImages() {
		List<Product> products = new ArrayList<Product>();
		int[] imgArr = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
//		JSONArray jsonArray = jsonObject.getJSONArray(TagName.TAG_PRODUCTS);
		Product product;
		for (int i = 0; i < 4; i++) {
			product = new Product();

			product.setId(i);
			product.setName(i+"name");
//			product.setImageUrl(imgArr[i]);
			product.setImgresID(imgArr[i]);

			products.add(product);
		}
		return products;
	}

}
