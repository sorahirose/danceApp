package com.rufflez.swipeyandlist;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YoutubeListAdapter extends ArrayAdapter<YoutubeListData> {
	// LayoutInflaterはレイアウトxmlファイルからIDを指定して
		// Viewが使えちゃう仕組み
		private LayoutInflater mLayoutInflater;
		private Handler imgHandler;
		private Object mQueue;
		private ImageView thumbnail;
		private String thumbnailUrl;
		
		
		public YoutubeListAdapter(Context context, int resourceId, List<YoutubeListData> objects) {
			super(context, resourceId, objects);
			// getLayoutInflater()メソッドはActivityじゃないと使えない
			mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		// getView()メソッドは各行を表示しようとした時に呼ばれる
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 特定行(position)のデータを得る
			YoutubeListData item = (YoutubeListData)getItem(position);
			// convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
			if (null == convertView) convertView = mLayoutInflater.inflate(R.layout.lista, null);
			
			// YoutubeListDataのデータをViewの各Widgetにセットする
			TextView titleTextView = (TextView)convertView.findViewById(R.id.title);
			titleTextView.setText(item.getTextData());
			TextView channelTextView = (TextView)convertView.findViewById(R.id.channel);
			channelTextView.setText(item.getTextData());
			TextView detailTextView = (TextView)convertView.findViewById(R.id.detail);
			detailTextView.setText(item.getTextData());
			
			  thumbnail =(ImageView)convertView.findViewById(R.id.thumbnail);
			  thumbnailUrl = item.getThumbnailUrl();
			 
			//画像読込
		        try{
		        	thumbnail.setTag(thumbnailUrl);
		            // AsyncTaskは１回しか実行できない為、毎回インスタンスを生成
		            ImageGetTask task = new ImageGetTask(thumbnail);
		            task.execute(thumbnailUrl);
		        }
		        catch(Exception e){
		        	thumbnail.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_launcher));
//		            waitBar.setVisibility(View.GONE);
		            thumbnail.setVisibility(View.VISIBLE);
		        }

			  
			  
	        imgHandler = new Handler();
	          new Thread( new Runnable() {
	               private Drawable drawImg;

				@Override
	               public void run() {
	                    // TODO Auto-generated method stub
					 
	                    try {
	                         URL url = new URL(thumbnailUrl);
	                         InputStream is = (InputStream)url.openStream();
	                         drawImg = Drawable.createFromStream(is, "");
	                         is.close();
	                          
	                         imgHandler.post( new Runnable() {
	                              @Override
	                              public void run() {
	                                   // TODO Auto-generated method stub
	                                   thumbnail.setImageDrawable(drawImg);
	                                   thumbnail.invalidate();
	                              }
	                         });
	                    } catch (MalformedURLException e) {
	                         // TODO Auto-generated catch block
	                         e.printStackTrace();
	                    } catch (IOException e) {
	                         // TODO Auto-generated catch block
	                         e.printStackTrace();
	                    }
	               }
	          }).start();
	        
			return convertView;
		}
		class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
			private ImageView image;
		    private String tag;
		 
			public ImageGetTask(ImageView thumbnail) {
				//対象の項目を保持しておく
		        image = thumbnail;
		        tag = image.getTag().toString();
			}

			@Override
		    protected Bitmap doInBackground(String... params) {
		        // ここでHttp経由で画像を取得します。取得後Bitmapで返します。
		        synchronized (getContext()){
		            try {
		                //キャッシュより画像データを取得
		                Bitmap image = ImageCache.getImage(params[0]);
		                if (image == null) {
		                    //キャッシュにデータが存在しない場合はwebより画像データを取得
		                    URL imageUrl = new URL(params[0]);
		                    InputStream imageIs;
		                    imageIs = imageUrl.openStream();
		                    image = BitmapFactory.decodeStream(imageIs);
		                    //取得した画像データをキャッシュに保持
		                    ImageCache.setImage(params[0], image);
		                }
		                return image;
		            } catch (MalformedURLException e) {
		                return null;
		            } catch (IOException e) {
		                return null;
		            }
		        }
		    }
			@Override
		    protected void onPostExecute(Bitmap result) {
		        // Tagが同じものか確認して、同じであれば画像を設定する
		        // （Tagの設定をしないと別の行に画像が表示されてしまう）
		        if(tag.equals(image.getTag())){
		            if(result!=null){
		                //画像の設定
		                image.setImageBitmap(result);
		            }
		            else{
		                //エラーの場合は×印を表示
		                image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_launcher));
		            }
		            //プログレスバーを隠し、取得した画像を表示
		            image.setVisibility(View.VISIBLE);
		        }
		    }
	        //以下に記載
	    }
		public static class ImageCache {
		    private static HashMap<String,Bitmap> cache = new HashMap<String,Bitmap>();
		 
		    //キャッシュより画像データを取得
		    public static Bitmap getImage(String key) {
		        if (cache.containsKey(key)) {
		            return cache.get(key);
		        }
		        //存在しない場合はNULLを返す
		        return null;
		    }
		 
		    //キャッシュに画像データを設定
		    public static void setImage(String key, Bitmap image) {
		        cache.put(key, image);
		    }
		 
		    //キャッシュの初期化（リスト選択終了時に呼び出し、キャッシュで使用していたメモリを解放する）
		    public static void clearCache(){
		        cache = null;
		        cache = new HashMap<String,Bitmap>();
		    }
		}
		
}
