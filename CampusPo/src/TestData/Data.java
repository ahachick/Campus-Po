package TestData;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.campuspo.domain.Poster;
import com.campuspo.domain.Timeline;
import com.campuspo.domain.User;

public class Data {
	
	public static Timeline getTimeline() {

		Timeline timeline = new Timeline();
		ArrayList<Poster> posters = new ArrayList<Poster>();
		
		Poster p1 = new Poster();
		p1.setPosterId(2);
		p1.setPosterTitle("打球");
		p1.setProfileIconUrl("https://lh4.googleusercontent.com/-EIBGfnuLtII/URquqVHwaRI/AAAAAAAAAbs/FA4McV2u8VE/s160-c/Grand%252520Teton.jpg");
		p1.setPosterDescription("YOOOOOOOOOOO!");
		p1.setUserId(12);
		p1.setUserScreenName("Eric");
		p1.setReleasedTime(new Date());
		p1.setWantedNum(9);
		p1.setParticipantNum(8);
		p1.setFavorited(true);
		p1.setJoined(false);
		p1.setSponsor(false);
		
		Poster p2 = new Poster();
		p2.setPosterId(1);
		p2.setPosterTitle("play game");
		p2.setProfileIconUrl("https://lh6.googleusercontent.com/-H4SrUg615rA/URquuL27fXI/AAAAAAAAAbs/4aEqJfiMsOU/s1024/Horseshoe%252520Bend%252520Sunset.jpg");
		p2.setPosterDescription("haaaaaaaaaaaaaaa!");
		p2.setUserId(12);
		p2.setUserScreenName("Erika");
		p2.setReleasedTime(new Date());
		p2.setWantedNum(4);
		p2.setParticipantNum(1);
		
		Poster p3 = new Poster();
		p3.setPosterId(2);
		p3.setPosterTitle("打球");
		p3.setProfileIconUrl("https://lh4.googleusercontent.com/-WoMxZvmN9nY/URquq1v2AoI/AAAAAAAAAbs/grj5uMhL6NA/s1024/Grass%252520Closeup.jpg");
		p3.setPosterDescription("~~~~~~~~~~~~!");
		p3.setUserId(12);
		p3.setUserScreenName("Eric");
		p3.setReleasedTime(new Date());
		p3.setWantedNum(9);
		p3.setParticipantNum(8);
		
		Poster p4 = new Poster();
		p4.setPosterId(2);
		p4.setPosterTitle("打球");
		p4.setProfileIconUrl( "https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s1024/Green%252520Grass.jpg");
		p4.setUserId(12);
		p4.setUserScreenName("Eric");
		p4.setReleasedTime(new Date());
		p4.setWantedNum(9);
		p4.setParticipantNum(8);
		
		Poster p5 = new Poster();
		p5.setPosterId(2);
		p5.setPosterTitle("打球");
		p5.setProfileIconUrl("https://lh5.googleusercontent.com/-6LVb9OXtQ60/URquteBFuKI/AAAAAAAAAbs/4F4kRgecwFs/s1024/Hanging%252520Leaf.jpg");
		p5.setUserId(12);
		p5.setUserScreenName("Eric");
		p5.setReleasedTime(new Date());
		p5.setWantedNum(9);
		p5.setParticipantNum(8);
		
		Poster p6 = new Poster();
		p6.setPosterId(2);
		p6.setPosterTitle("打球");
		p6.setProfileIconUrl("https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s1024/Green%252520Grass.jpg");
		p6.setUserId(12);
		p6.setUserScreenName("Eric");
		p6.setReleasedTime(new Date());
		p6.setWantedNum(9);
		p6.setParticipantNum(8);
		
		Poster p7 = new Poster();
		p7.setPosterId(2);
		p7.setPosterTitle("打球");
		p7.setProfileIconUrl("https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg");
		p7.setUserId(12);
		p7.setUserScreenName("Eric");
		p7.setReleasedTime(new Date());
		p7.setWantedNum(9);
		p7.setParticipantNum(8);
		
		posters.add(p1);
		posters.add(p2);
		posters.add(p3);
		posters.add(p4);
		posters.add(p5);
		posters.add(p6);
		posters.add(p7);
		
		Log.d("Data", "" + posters.size());
		timeline.setPosters(posters);
		return timeline;
	}
	
	public static User getUserProfile(){
		
		JSONObject user = new JSONObject();
		try {
			user.put("user_id", 123);
			user.put("user_email", "1233");
			user.put("user_screen_name", "渣渣");
			user.put("user_description", "day day up");
			user.put("profile_icon_url", "https://lh6.googleusercontent.com/-h-ALJt7kSus/URqvIThqYfI/AAAAAAAAAbs/ejiv35olWS8/s160-c/Tokyo%252520Heights.jpg");
			user.put("gender", 1);
			user.put("created_at", "2013-12-12 12:00:11");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return new User(user);
		
	}

}
