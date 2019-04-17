package com.bupin.test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class MakeXML {
	private final static String rootPath = "C:/Users/w1138/Desktop/values/values-{0}x{1}";
	private final static int DW = 800;
	private final static int DH = 1280;
	private final static float dw = DW;
	private final static float dh = DH;
//	private final static float dw = 320f;
//	private final static float dh = 480f;
	private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
	private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

	public static void main(String[] args) {
		makeString(320, 480);
		makeString(480, 800);
		makeString(480, 845);
		makeString(540, 960);
		makeString(600, 1024);
		makeString(768, 1024);
		makeString(720, 1184);
		makeString(720, 1196);
		makeString(720, 1280);
		makeString(768, 1280);
		makeString(800, 1280);
		makeString(1536, 2048);
		makeString(1080, 1812);
		makeString(1080, 1920);
		makeString(1200, 1920);
		makeString(1440, 2560);
		makeString(1600, 2560);
		makeString(1800, 2560);
	}

	private static void makeString(int w, int h) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<resources>");
		float cellw = w / dw;
		for (int i = 1; i < DW; i++) {
			sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
					change(cellw * i) + ""));
		}
		sb.append(WTemplate.replace("{0}", ""+DW).replace("{1}", w + ""));
		sb.append("</resources>");

		StringBuffer sb2 = new StringBuffer();
		sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb2.append("<resources>");
		float cellh = h / dh;
		for (int i = 1; i < DH; i++) {
			sb2.append(HTemplate.replace("{0}", i + "").replace("{1}",
					change(cellh * i) + ""));
		}
		sb2.append(HTemplate.replace("{0}", ""+DH).replace("{1}", h + ""));
		sb2.append("</resources>");

		String path = rootPath.replace("{0}", h + "").replace("{1}", w + "");
		File rootFile = new File(path);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}

		File layxFile = new File(path + "/lay_x.xml");
		File layyFile = new File(path + "/lay_y.xml");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(layxFile));
			pw.print(sb.toString());
			pw.close();
			pw = new PrintWriter(new FileOutputStream(layyFile));
			pw.print(sb2.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String change(float f) {
		float b = (float) (Math.round(f * 100))/100;// 100;(杩欓噷鐨�00灏辨槸2浣嶅皬鏁扮偣,濡傛灉瑕佸叾瀹冧綅,濡�浣�杩欓噷涓や釜100鏀规垚10000)
		return b+"";
	}
}
