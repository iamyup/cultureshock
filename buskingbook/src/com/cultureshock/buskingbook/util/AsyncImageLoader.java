package com.cultureshock.buskingbook.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.ByteArrayBuffer;

import com.cultureshock.buskingbook.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;


/**
 * User: idccc
 * Date: 2010. 6. 10 4:23:42
 */
public class AsyncImageLoader
{
	private boolean m_blIsExistFile;
	private boolean m_blIsEnableCashe;
	
	private Bitmap  m_oTmpBitmap;
	
	private static final String TAG = "AsyncImageLoader";

	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	
	public interface ImageCallback
	{
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

	public interface getBitmapCallback
	{
		public void onBitmapLoaded(Bitmap bm, String url );
	}
	
	public AsyncImageLoader()
	{
		File fp;
		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Busking/cache";
		fp = new File(filePath);	
		if(fp.exists())
		{
			m_blIsEnableCashe = true;
		}
		else
		{
			m_blIsEnableCashe = false;
			fp.mkdirs();
		}
	}

	
	public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback)
	{
		if(imageUrl != null && imageUrl.length() > 0)
		{
			if(imageCache.containsKey(imageUrl))
			{
				SoftReference<Drawable> softReference = imageCache.get(imageUrl);
				Drawable drawable = softReference.get();

				if(drawable != null)
				{
					return drawable;
				}
			}

			final Handler handler = new Handler()
			{
				@Override
				public void handleMessage(Message message)
				{
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			new Thread()
			{
				@Override
				public void run()
				{
					Drawable drawable = loadImageFromUrl(imageUrl);
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();
		}

		return null;
	}

	public static Drawable loadImageFromUrl(String url)
	{
		InputStream inputStream = null;

		try
		{
			URL target = new URL(url);
			Log.d("============>", " Async .. url .. "+target);
			URLConnection urlConnection = target.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.connect();
			inputStream = urlConnection.getInputStream();
			if (inputStream == null)
				Log.d("----", " input stream is null ... ");
			Log.d("==========>", "---- get ok .. ");
		} catch(IOException e)
		{
			/*throw new RuntimeException(e);*/
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
		Drawable tmp = Drawable.createFromStream(inputStream, "src");
		if (tmp == null)
			tmp = Drawable.createFromStream(inputStream, "src");
		
		return tmp;
	}
	
	private static class ImageView_Item
	{
		public	String	url = null;
		public	WeakReference<ImageView> weak_iv = null;
		
		public 	ImageView_Item(ImageView iv, String url)
		{
			this.url = url;
			weak_iv = new WeakReference<ImageView>(iv);
		}
	}
	
	private static class ImageView_ItemList extends ArrayList<ImageView_Item>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		private void Remove(ImageView iv)
		{
			int index = IndexOf( iv );
			if( index >= 0 )
				this.remove(index);
		}

		public void Reset(ImageView iv, String url)
		{
			Remove(iv);
			if( url == null || url.length()==0 )
				return;
			
			this.add( new ImageView_Item(iv, url) );
		}
		
		private int IndexOf( ImageView iv )
		{
			for( int i=0; i<this.size(); i++)
			{
				ImageView_Item item = this.get(i);
				if( item.weak_iv != null && item.weak_iv.get() == iv )
					return i;
			}
			return -1;
		}
		
		public boolean Contains(ImageView iv, String url)
		{
			Cleaning();
			for( int i=0; i<this.size(); i++)
			{
				ImageView_Item item = this.get(i);
				if( item.weak_iv != null && item.weak_iv.get() == iv && item.url.equals(url) )
					return true;
			}
			return false;
		}

		private void Cleaning()
		{
			for( int i=0; i<this.size(); i++)
			{
				ImageView_Item item = this.get(i);
				if( item.weak_iv == null || item.weak_iv.get() == null )
				{
					this.remove(i--);
				}
			}
		}
	}
	
	private static HashMap<String, byte[]> url_rawdata = new HashMap<String, byte[]>();
	private ImageView_ItemList	iv_itemlist = new ImageView_ItemList();

	public void setImageDrawableAsync( ImageView v, final String url, Context context) 
	{
		setImageDrawableAsync( v, url, context.getResources().getDrawable(R.drawable.image_default), context.getResources().getDrawable(R.drawable.default_a) , context);
	}
	
	
	public void getImageDrawableAsync( final String url, final getBitmapCallback callback )
	{
		byte[] rawdata = url_rawdata.get(url);		
		if( rawdata != null )
		{
			Bitmap bm = null;
			if( rawdata.length > 512000 )
			{
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 10;	
				bm = BitmapFactory.decodeByteArray( rawdata, 0, rawdata.length, options);
			}else
			{
				bm = BitmapFactory.decodeByteArray( rawdata, 0, rawdata.length);
			}
			if( bm != null )
			{
				callback.onBitmapLoaded(bm, url);
				return;
			}

			// Log.i("AsyncImageLoader.getImageDrawableAsync", String.format("***FAILED url:%s, md5:%s, size:%d", url, BytesToMD5(rawdata), rawdata.length) );
			url_rawdata.remove(url);
		}
		
		SoftReference<byte[]>	ref_rawdata = static_url_rawdata.get(url);
		rawdata = ref_rawdata != null ? ref_rawdata.get() : null;
		if( rawdata != null )
		{
			Bitmap bm = BitmapFactory.decodeByteArray( rawdata, 0, rawdata.length );
			if( bm != null )
			{
				// Log.i("AsyncImageLoader.getImageDrawableAsync", String.format("***OK url:%s, md5:%s, size:%d", url, BytesToMD5(rawdata), rawdata.length) );
				url_rawdata.put(url, rawdata );
				callback.onBitmapLoaded(bm, url);
				return;
			}

			// Log.i("AsyncImageLoader.getImageDrawableAsync", String.format("***FAILED url:%s, md5:%s, size:%d", url, BytesToMD5(rawdata), rawdata.length) );
			static_url_rawdata.remove(url);
		}
		
		try {
			// thread
			new AsyncTask<String, Void, byte[]>() 
			{
				String	url;
				boolean isExistFile;
				@Override
				protected byte[] doInBackground(String... params) 
				{
					url = params[0];
					if( params[1] != null && params[1].equalsIgnoreCase("Y") )
						isExistFile = true;
					else
						isExistFile = false;
					return downloadBitmap(url);
				}
	
				@Override
				protected void onPostExecute(byte[] rawdata) {
					if( rawdata != null )
					{
						Bitmap bm = null;
						if( rawdata.length > 512000 )
						{
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 10;	
							bm = BitmapFactory.decodeByteArray( rawdata, 0, rawdata.length, options);
						}else
						{
							bm = BitmapFactory.decodeByteArray( rawdata, 0, rawdata.length);
						}
						
						if( bm != null )
						{
							url_rawdata.put(url, rawdata );
							static_url_rawdata.put(url, new SoftReference<byte[]>(rawdata));
							
							if(m_blIsEnableCashe)
							{
					        	if(!isExistFile)
					        	{
					        		String filename = url.substring(url.lastIndexOf("/") + 1);
					        		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Busking/cache" +"/" + filename;	
					        		File fp = new File(filePath);
					        		FileOutputStream fpOut = null;
					        		
					        		int nOffset = 0; 
					        		int nTotalLen = rawdata.length;
					        		byte[] pbTmpBuf = new byte[nTotalLen];
					        		int nToRead;
					        		pbTmpBuf = rawdata;
					    
					        		if(nTotalLen > 0)
					        		{
					        			try
					        			{
					        				fpOut = new FileOutputStream(fp);	
					        			}
					        			catch(IOException e)
					        			{
					        				e.printStackTrace();
					        			}		        			
					        		}
					        		        		
					        		while(nTotalLen > 0)
					        		{
					        			nToRead = nTotalLen > 4096?4096:nTotalLen;
					        			try
					        			{
					        				fpOut.write(pbTmpBuf, nOffset, nToRead);
					        			}
					        			catch(IOException e)
					        			{
					        				fp.delete();
					        			}
					        			nOffset += nToRead;
					        			nTotalLen -= nToRead;
					        		}
					        		if(fpOut != null)
					        		{
					        			try
					        			{
					        				fpOut.close();
					        				fpOut.flush();
					        			}
					        			catch(IOException e)
					        			{
					        				fp.delete();
					        			}	

					        		}	
					        	}
							}
						}
						else
						{
							// Log.i("AsyncImageLoader.getImageDrawableAsync", String.format("***FAILED url:%s, md5:%s, size:%d", url, BytesToMD5(rawdata), rawdata.length) );
						}
						callback.onBitmapLoaded(bm, url);
					}
				};
			}
			.execute( url , m_blIsExistFile ? "Y" : "N" );
		}catch( RejectedExecutionException  e) {
			e.printStackTrace();
		}catch( Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setImageDrawableAsync( final ImageView iv, final String url, final Drawable loading_drawable, final Drawable default_drawable , Context context) 
	{
		
		final WeakReference<ImageView> weak_iv = new WeakReference<ImageView>(iv);

		String mUrl = "";
		mUrl = url;
		
		iv_itemlist.Reset(iv, mUrl);
		String filename = "";
		String filePath = "";
		try{
			filename = mUrl.substring(mUrl.lastIndexOf("/") + 1);
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Busking/cache" +"/" + filename;	
		}
		catch( Exception e)
		{
			iv.setImageDrawable(default_drawable);
			return ;
		}
		
		if (existCacheImage(filePath))
		{
			m_blIsExistFile = true;
			Drawable drawable = null;
		
			File fp = new File(filePath);
			 if(fp.length() > 512000)
			{
			   BitmapFactory.Options options = new BitmapFactory.Options();
			   options.inSampleSize = 10;	
			   Bitmap temp = BitmapFactory.decodeFile(filePath, options);
			   if( temp != null )
				   drawable = new BitmapDrawable(temp);
			   else
				   drawable = context.getResources().getDrawable(R.drawable.image_default);
			}
			else
			{
				drawable = Drawable.createFromPath(filePath);
				if( drawable == null )
					drawable = context.getResources().getDrawable(R.drawable.image_default);
			}
			iv.setImageDrawable(drawable);
			return ;
		}
		else
		{
			m_blIsExistFile = false;
			iv.setImageDrawable(loading_drawable);

			getImageDrawableAsync( mUrl, new getBitmapCallback() {
				@Override
				public void onBitmapLoaded(Bitmap bm, String url) 
				{
					ImageView	iv = weak_iv != null ? weak_iv.get() : null;
					if( iv == null || iv_itemlist.Contains( iv, url) == false )
					{
						
						try
						{
							bm.recycle();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return;
					}

					if( bm != null ){
						Drawable drawable = new BitmapDrawable(bm);
						iv.setImageDrawable(drawable);
					}		
				}
			});
		}
		
	}
	
	public void setImageDrawableAsync( final RemoteViews rv, final int resid, final String url, final Drawable loading_drawable, final Drawable default_drawable ) 
	{	
		String filename = "";
		String filePath = "";
		String mUrl = "";
		mUrl = url;
		try{
			filename = mUrl.substring(mUrl.lastIndexOf("/") + 1);
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Busking/cache" +"/" + filename;	
		}
		catch( Exception e)
		{
			Drawable d = loading_drawable; 
			Bitmap loadingBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(loadingBitmap);
			d.setBounds(0, 0, 100, 100);
			d.draw(canvas);
			rv.setImageViewBitmap(resid, loadingBitmap);
			return ;
		}
		
		if (existCacheImage(filePath))
		{
			m_blIsExistFile = true;
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			rv.setImageViewBitmap(resid, bitmap);
			return ;
		}
		else
		{
			m_blIsExistFile = false;

			Drawable d = loading_drawable; 
			Bitmap loadingBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(loadingBitmap);
			d.setBounds(0, 0, 100, 100);
			d.draw(canvas);
			rv.setImageViewBitmap(resid, loadingBitmap);

			getImageDrawableAsync( mUrl, new getBitmapCallback() {
				@Override
				public void onBitmapLoaded(Bitmap bm, String url) {
					if( bm != null )
						rv.setImageViewBitmap(resid, bm);
					else {
						Drawable d = default_drawable; 
						Bitmap defaultBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
						Canvas canvas = new Canvas(defaultBitmap);
						d.setBounds(0, 0, 100, 100);
						d.draw(canvas);
						rv.setImageViewBitmap(resid, defaultBitmap);	
					}
				}
			});
		}
		
	}

	private static HashMap<String, SoftReference<byte[]>> static_url_rawdata = new HashMap<String, SoftReference<byte[]>>();
	private static ImageView_ItemList	static_iv_itemlist = new ImageView_ItemList();
	
	
	static class FlushedInputStream extends FilterInputStream {
	    public FlushedInputStream(InputStream inputStream) {
	        super(inputStream);
	    }

	    @Override
	    public long skip(long n) throws IOException {
	        long totalBytesSkipped = 0L;
	        while (totalBytesSkipped < n) {
	            long bytesSkipped = in.skip(n - totalBytesSkipped);
	            if (bytesSkipped == 0L) {
	                  int bytes = read();
	                  if (bytes < 0) {
	                      break;  // we reached EOF
	                  } else {
	                      bytesSkipped = 1; // we read one byte
	                  }
	           }
	            totalBytesSkipped += bytesSkipped;
	        }
	        return totalBytesSkipped;
	    }
	}
	
	private static byte[] downloadBitmap(String url) 
	{
		try
		{
			URL	conn = new URL(url);

			URLConnection urlConnection = conn.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setUseCaches(true);

			urlConnection.connect();

			InputStream inputStream = urlConnection.getInputStream();
			if( inputStream == null )
				return null;

			final int	content_length = urlConnection.getContentLength();
			if(content_length > 204800)
			{
				return null;
			}
			/**
			 * 용량 제한 일단 풀어놓음 !
			 */
//			if(content_length > 5120000)
//			{
//				return null;
//			}
			final ByteArrayBuffer	arraybuf = new ByteArrayBuffer( content_length > 0 ? content_length : 0 );
        	byte[] b = new byte[8096];
	        while(true)
	        {
	        	int readed = inputStream.read(b, 0, b.length );
	        	if( readed < 0)
	        		break;
	        	arraybuf.append(b, 0, readed );
	        }
			if( content_length > 0 && arraybuf.length() != content_length )
			{
				// Log.i("AsyncImageLoader.ImageDownloader", String.format("ContentLength failed : %d, %d", arraybuf.length(), content_length) );
				arraybuf.clear();	
			}

			// Log.i("AsyncImageLoader.ImageDownloader", String.format("ByteArray size : %d", arraybuf.length() ) );
	        return arraybuf.toByteArray();
		}
		catch( ConnectTimeoutException e )
        {
			
        }
        catch( SocketTimeoutException e )
        {
        
        }
		catch(Exception e)
		{
		}

	    return null;
	}
	
	private static boolean existCacheImage(String filename)
	{
		
		File fp;

		fp = new File(filename);
		if(fp.exists())
		{
			if( fp.isFile() )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}
	
}
