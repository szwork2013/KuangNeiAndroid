package me.kuangneipro.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.qiniu.auth.JSONObjectRet;
import com.qiniu.io.IO;
import com.qiniu.io.PutExtra;

public class ImageUtil {
	
	public static final String IMAGE_TMP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/kuangnei/files/tmp/images/";
	
	public static final String QINIUDN_SERVER = "http://kuangnei.qiniudn.com/";
	
	public static final int GET_IMAGE_UPLOAD_TOKEN = -10000;
	
	public static void gettingImageUploadToken(HttpHelper httpRequest){
		httpRequest.setUrl(HostUtil.GET_UP_TOKEN_URL).put("userid", PushUtil.getToken()).put("secretkey","xxxx***").asyncGet();
	}
	
	public static String getImageUploadToken(JSONObject jsonObj){
		String token = jsonObj.optString("uptoken");
		return token;
	}
	
	public static void checkImageTmpFolder(){
		File file = new File(IMAGE_TMP_PATH);
		if(!file.exists())
			file.mkdirs();
	}
	
	public static File compressBmpToTmpFile(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;//ֻ����,��������  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;//  
        float ww = 480f;//  
        int be = 1;  
        if (w > h && w > ww) {  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//���ò�����  
          
        newOpts.inPreferredConfig = Config.ARGB_8888;//��ģʽ��Ĭ�ϵ�,�ɲ���  
        newOpts.inPurgeable = true;// ͬʱ���òŻ���Ч  
        newOpts.inInputShareable = true;//����ϵͳ�ڴ治��ʱ��ͼƬ�Զ�������  
          
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//      return compressBmpFromBmp(bitmap);//ԭ���ķ������������������ͼ���ж���ѹ��  
                                    //��ʵ����Ч��,��Ҿ��ܳ���  
        checkImageTmpFolder();
		
		File file = new File(IMAGE_TMP_PATH+UUID.randomUUID().toString()+".jpg");
		
        try {  
        	file.createNewFile();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int options = 80;//����ϲ����80��ʼ,  
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);  
            while (baos.toByteArray().length > 102400) {   
                baos.reset();  
                options -= 10;  
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);  
            }  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        return file;
    }  
	
	public static File compressBmpToTmpFile(Bitmap bmp){  
		
		checkImageTmpFolder();
		
		File file = new File(IMAGE_TMP_PATH+UUID.randomUUID().toString()+".jpg");
		
        try {  
        	file.createNewFile();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int options = 80;//����ϲ����80��ʼ,  
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
            while (baos.toByteArray().length > 102400) {   
                baos.reset();  
                options -= 10;  
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
            }  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        return file;
    }
	

	public static int calculateInSampleSize(BitmapFactory.Options options, int thumbnailSize) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > thumbnailSize || width > thumbnailSize) {
	        if (width > height) {
	            inSampleSize = Math.round((float)height / (float)thumbnailSize);
	        } else {
	            inSampleSize = Math.round((float)width / (float)thumbnailSize);
	        }
	    }
	    return inSampleSize;
	}
	
	public static Bitmap decodeSampledBitmap(String imgFile, int thumbnailSize) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imgFile, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, thumbnailSize);
	    
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(imgFile, options);
	}
	
	public static void uploadImg(String token,File file,PutExtra extra,JSONObjectRet jsonObjectRet){
		IO.putFile( token, IO.UNDEFINED_KEY, file, extra, jsonObjectRet);
	}
	
	public static void uploadImg(String token,File file,JSONObjectRet jsonObjectRet){
		PutExtra extra = new PutExtra();
		extra.params = new HashMap<String, String>();
		IO.putFile( token, IO.UNDEFINED_KEY, file, extra, jsonObjectRet);
	}
	
	
}