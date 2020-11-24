package Utils;
import Manager.Const;
import Manager.Control;
import Utils.Secure.FileCrypto;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class Util {

	public static int getLineAmt(String path) throws IOException {
		BufferedReader reader = new BufferedReader(Gdx.files.internal(path).reader());
		int lines = 0;
		while (reader.readLine() != null) lines++;
		reader.close();
		return lines;
	}

	public static void writeToText(String filepath, String data) {
		FileHandle file = Gdx.files.local(filepath);
		if(file.exists()) file.delete();
		file.writeString(data, true);
	}
	public static String getfromText(String filepath){
		return Gdx.files.local(filepath).readString();
	}

	public static JsonNode readFromJson(String path){
		try {
			return new ObjectMapper().readTree(Gdx.files.internal(path).reader());
		} catch (IOException e2) {
			StringWriter sw = new StringWriter();
			e2.printStackTrace(new PrintWriter(sw));
			Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, sw.toString());
		}
		return null;
	}

	public static String readCryptoFile(FileHandle fileHandle, boolean force){
		try {
			if(Control.getClientSettings().clientVersion() || force)
			return new FileCrypto().decrypt(fileHandle);
			else return fileHandle.readString();
		}catch (Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, sw.toString());
		}
		return null;
	}

	public static String readCryptoFile(String contents){
		try {
			if(Control.getClientSettings().clientVersion())
			return new FileCrypto().decrypt(contents);
			else return contents;
		}catch (Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, sw.toString());
		}
		return null;
	}

	public static JsonNode readJson(String string){
		try {
			return new ObjectMapper().readTree(string);
		}catch (Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, sw.toString());
		}
		return null;
	}

	public static String MD5(byte[] bytes) {
		try {
			byte[] array = java.security.MessageDigest.getInstance("MD5").digest(bytes);
			StringBuilder sb = new StringBuilder();
			for (byte b : array) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static Pixmap readEncryptedImage(FileHandle fileHandle){
		try{
			byte[] array = new FileCrypto().decryptInput(fileHandle.readBytes());
			return new Pixmap(array, 0, array.length);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


	public static int convertToHash2(int val1, int val2){
			return (999 + val1) * 999 + val2;
	}

	public static int convertToHash3(int val1, int val2, int val3){
		return 37 * ((37 + val1) * 37 + val2) + val3;
	}

	public static boolean floatArrayIntersection(float[] array1, float[] array2){
		return rectangleIntersection(array1[0], array1[1], array1[2], array1[3], array2[0], array2[1], array2[2], array2[3]);
	}

	public static float getWidth(short graphicType){
		switch (graphicType){
			case Const.Graphic_NONSQUARE:
				return Control.nonSquareScaleX;
			case Const.Graphic_SQUAREMIN:
			case Const.Graphic_DISPLAY:
				return Control.SquareScaleMin;
			case Const.Graphic_SQUAREMAX:
				return Control.SquareScaleMax;
		}
		return 0;
	}

	public static float getHeight(short graphicType){
		switch (graphicType){
			case Const.Graphic_NONSQUARE:
				return Control.nonSquareScaleY;
			case Const.Graphic_SQUAREMIN:
			case Const.Graphic_DISPLAY:
				return Control.SquareScaleMin;
			case Const.Graphic_SQUAREMAX:
				return Control.SquareScaleMax;
		}
		return 0;
	}

	public static float getX(float x, short graphicType){
		switch (graphicType){
			case Const.Graphic_NONSQUARE:
				return x * Control.nonSquareScaleX;
			case Const.Graphic_DISPLAY:
				return UNIJMath.getGraphicX(x);
			case Const.Graphic_SQUAREMIN:
				return x * Control.SquareScaleMin;
			case Const.Graphic_SQUAREMAX:
				return x * Control.SquareScaleMax;
		}
		return 0;
	}

	public static float getY(float y, short graphicType){
		switch (graphicType){
			case Const.Graphic_NONSQUARE:
				return y * Control.nonSquareScaleY;
			case Const.Graphic_DISPLAY:
				return UNIJMath.getGraphicY(y);
			case Const.Graphic_SQUAREMIN:
				return y * Control.SquareScaleMin;
			case Const.Graphic_SQUAREMAX:
				return y * Control.SquareScaleMax;
		}
		return 0;
	}

	public static String[] tokenize(String string, char delimiter)
	{
		String[] temp = new String[(string.length() / 2) + 1];
		int wordCount = 0;
		int i = 0;
		int j = string.indexOf(delimiter);

		while( j >= 0) {
			temp[wordCount++] = string.substring(i, j);
			i = j + 1;
			j = string.indexOf(delimiter, i);
		}

		temp[wordCount++] = string.substring(i);
		String[] result = new String[wordCount];
		System.arraycopy(temp, 0, result, 0, wordCount);
		return result;
	}


	public static boolean rectangleIntersection(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
		return Math.max(x1, x2) < Math.min(x1 + w1, x2 + w2) && Math.max(y1, y2) < Math.min(y1 + h1,  y2 + h2);
	}

	public static boolean floatArrayIntersection(float[] list, float x, float y, float w, float h){
		return rectangleIntersection(list[0], list[1], list[2], list[3], x, y, w, h);
	}

	public static JsonNode readFromJsonLocal(String path){
		try {
			return new ObjectMapper().readTree(Gdx.files.local(path).reader());
		} catch (IOException e2) {
			StringWriter sw = new StringWriter();
			e2.printStackTrace(new PrintWriter(sw));
			Control.getDeviceManager().sendAnalytic(Const.Analytics_ERROR,sw.toString());
		}
		return null;
	}

	public static boolean fileExists(String filepath){
		return Gdx.files.local(filepath).exists();
	}

	public static float[] readMatrix(String string){
		float[] array = new float[20];
		String[] str = string.split(",");
		for(int i = 0; i < str.length; ++i){
			array[i] = Float.parseFloat(str[i]);
		}
		return array;
	}

}
