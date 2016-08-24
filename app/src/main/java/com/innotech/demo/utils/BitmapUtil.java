package com.innotech.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

	/**
	 * 圆形图片，有白色边框
	 */
	public static Bitmap getCircleBitmap(Context context, int width, Bitmap bitmap) {
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
				context.getResources().getDisplayMetrics());
		int x = width - 2 * margin;
		Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		
		// 画圆图
		paint.reset();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(width / 2, width / 2, x / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		Rect rcSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawBitmap(bitmap, rcSrc, new Rect(margin, margin, x + margin, x + margin), paint);

		// 画白色外圆
		paint.reset();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(margin);
		canvas.drawCircle(width / 2, width / 2, x / 2, paint);
		return output;
	}
	
	/***
	 * 将图片按比例压缩到接近指定大少
	 * 
	 * @param bitmap
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static Bitmap compressBmpByWid(Bitmap bitmap, int targetWidth, int targetHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;  
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, opts);
		int imgWidth = opts.outWidth;  
		int imgHeight = opts.outHeight;  
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 && widthRatio > 1) {  
			if (widthRatio > heightRatio) {  
				opts.inSampleSize = widthRatio;  
			} else {  
				opts.inSampleSize = heightRatio;  
			}  
		}  
		opts.inJustDecodeBounds = false;  
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, opts);
		return bitmap;  
	}  
	
	/**
	 * 得到 图片旋转 的角度
	 * @param filepath
	 * @return
	 */
	public static int readPictureDegree(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				default:
					break;
				}
			}
		}
		return degree;
	}
	
	/**
	 * 旋转图片
	 * @param bitmap
	 * @param angle
	 * @return
	 */
	public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
		final Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		final Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 保存图片到指定路径
	 * @param bitmap
	 * @param savePath
	 * @return
	 */
	public static boolean saveBitmapToPath(Bitmap bitmap, String savePath) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(savePath);
			bitmap.compress(CompressFormat.JPEG, 100, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	  * 从文件加载最适合的图片.
	  * @param fileName
	  * @param maxWidth
	  * @param maxHeight
	  * @return
	  */
	 public static Bitmap getBestBitmapFromFile(String fileName, int maxWidth, int maxHeight) {
		 final BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
		 decodeOptions.inJustDecodeBounds = true;
		 Bitmap bitmap = BitmapFactory.decodeFile(fileName, decodeOptions);
		 final int actualWidth = decodeOptions.outWidth;
		 final int actualHeight = decodeOptions.outHeight;

		 final int desiredWidth = getResizedDimension(maxWidth, maxHeight, actualWidth, actualHeight);
		 final int desiredHeight = getResizedDimension(maxHeight, maxWidth, actualHeight, actualWidth);

		 decodeOptions.inJustDecodeBounds = false;
		 decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
		 final Bitmap tempBitmap = BitmapFactory.decodeFile(fileName, decodeOptions);

		 if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
			 bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
			 tempBitmap.recycle();
		 } else {
			 bitmap = tempBitmap;
		 }
		 return bitmap;
	 }
	 
	 private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
		 if (maxPrimary == 0 && maxSecondary == 0) {
			 return actualPrimary;
		 }
		 if (maxPrimary == 0) {
			 final double ratio = (double) maxSecondary / (double) actualSecondary;
			 return (int) (actualPrimary * ratio);
		 }
		 if (maxSecondary == 0) {
			 return maxPrimary;
		 }
		 final double ratio = (double) actualSecondary / (double) actualPrimary;
		 int resized = maxPrimary;
		 if (resized * ratio > maxSecondary) {
			 resized = (int) (maxSecondary / ratio);
		 }
		 return resized;
	 }
	 
	 private static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
		 final double wr = (double) actualWidth / desiredWidth;
		 final double hr = (double) actualHeight / desiredHeight;
		 final double ratio = Math.min(wr, hr);
		 float n = 1.0f;
		 while ((n * 2) <= ratio) {
			 n *= 2;
		 }
		 return (int) n;
	 }
}
