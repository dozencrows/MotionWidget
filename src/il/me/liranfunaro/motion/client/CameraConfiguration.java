package il.me.liranfunaro.motion.client;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class CameraConfiguration {
	public static final String PATTERN_REGEX = "<li\\s*>\\s*<a\\s*href=([^><]+)>([^><]+)</a\\s*>\\s*=\\s*([^><]+)</li\\s*>";
	public static final int GROUP_ADDRESS 	= 1;
	public static final int GROUP_NAME 		= 2;
	public static final int GROUP_VALUE 	= 3;
	
	public static final Pattern PATTERN = Pattern.compile(PATTERN_REGEX);
	
	private final String name;
	private final String address;
	private String value;
	
	public CameraConfiguration(String name, String address, String value) {
		this.name = name;
		this.address = address;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getSetAddress() {
		return address + "=" + value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static List<CameraConfiguration> getCameraConfiguration(InputStream inputStream) {
		LinkedList<CameraConfiguration> confList = new LinkedList<CameraConfiguration>();
		
		Scanner streamScanner = null;
		
		try {
			streamScanner = new Scanner(inputStream);
			
			while (streamScanner.findWithinHorizon(PATTERN, 0) != null) {
			  MatchResult m = streamScanner.match();
			  confList.add(new CameraConfiguration(m.group(GROUP_NAME), m.group(GROUP_ADDRESS), m.group(GROUP_VALUE)));
			}
		} finally {
			if(streamScanner != null) {
				streamScanner.close();
			}
		}
		
		return confList;
	}
}
