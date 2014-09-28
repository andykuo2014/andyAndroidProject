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
			str+=(hours+"�p��");
		}
		if(mins>0){
			str+=(mins+"��");
		}
		str+=(secs+"��");
		return str;
	}
	
	public String getShortFormat(){
		String str="";
		if(hours>0){
			str+=(hours+"�p��");
		}
		str+=(mins+"��");
		return str;
	}
}
