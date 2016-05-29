package ch.shibastudio.volleybooks.network;

import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import ch.shibastudio.volleybooks.utils.LruBitmapCache;

/**
 * Created by didier on 5/27/16.
 */
public class VolleyRequestQueue {
	private final static int CACHE_SIZE_MB = 10 * 1024 * 1024;

	private static VolleyRequestQueue instance;
	private Context context;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;

	/**
	 * Gets the instance of the Volley request queue.
	 * @param context as the given context.
	 * @return the instance.
	 */
	public static synchronized VolleyRequestQueue getInstance(Context context){
		if(null == instance){
			instance = new VolleyRequestQueue(context);
		}
		return instance;
	}

	/**
	 * Gets the request queue.
	 * @return the request queue.
	 */
	public RequestQueue getRequestQueue(){
		if(null == this.requestQueue){
			Cache cache = new DiskBasedCache(this.context.getCacheDir(), CACHE_SIZE_MB);
			Network network = new BasicNetwork(new HurlStack());
			this.requestQueue = new RequestQueue(cache, network);
			this.requestQueue.start();
		}

		return this.requestQueue;
	}

	/**
	 * Gets the ImageLoader.
	 * @return the ImageLoader.
     */
	public ImageLoader getImageLoader(){ return this.imageLoader; }

	private VolleyRequestQueue(Context context){
		this.context = context;
		this.requestQueue = this.getRequestQueue();
		this.imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(context)));
	}
}