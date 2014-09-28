package tw.com.newstars.crazy;

public class TimeFormat {
	private int secs;
	private int mins;
	private int hours;
	public TimeFormat(long millisecs){
		long remainsecs = (int) (millisecs / 1000);
		hours = (int)(remainsecs / 3600);
		remainsecs = remainsecs - hours*3600;
		mins = (int)(remainsecs/60);
		remainsecs = remainsecs - mins*60;
		secs = (int)remainsecs;
	}
	
	public String getLongFormat(){
		String str="";
		if(hours>0){
			str+=(hours+"小時");
		}
		if(mins>0){
			str+=(mins+"分");
		}
		str+=(secs+"秒");
		return str;
	}
	
	public String getShortFormat(){
		String str="";
		if(hours>0){
			str+=(hours+"小時");
		}
		str+=(mins+"分");
		return str;
	}
}
