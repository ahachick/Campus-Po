package TestData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.campuspo.domain.Delegation;
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
		p1.setProfileIconUrl("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s160-c/A%252520Photographer.jpg");
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
		p2.setProfileIconUrl("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s160-c/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg");
		p2.setPosterDescription("haaaaaaaaaaaaaaa!");
		p2.setUserId(12);
		p2.setUserScreenName("Erika");
		p2.setReleasedTime(new Date());
		p2.setWantedNum(4);
		p2.setParticipantNum(1);
		
		Poster p3 = new Poster();
		p3.setPosterId(2);
		p3.setPosterTitle("打球");
		p3.setProfileIconUrl("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s160-c/Another%252520Rockaway%252520Sunset.jpg");
		p3.setPosterDescription("~~~~~~~~~~~~!");
		p3.setUserId(12);
		p3.setUserScreenName("Eric");
		p3.setReleasedTime(new Date());
		p3.setWantedNum(9);
		p3.setParticipantNum(8);
		
		Poster p4 = new Poster();
		p4.setPosterId(2);
		p4.setPosterTitle("打球");
		p4.setProfileIconUrl( "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s160-c/Antelope%252520Butte.jpg");
		p4.setUserId(12);
		p4.setUserScreenName("Eric");
		p4.setReleasedTime(new Date());
		p4.setWantedNum(9);
		p4.setParticipantNum(8);
		
		Poster p5 = new Poster();
		p5.setPosterId(2);
		p5.setPosterTitle("打球");
		p5.setProfileIconUrl("https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s160-c/Antelope%252520Hallway.jpg");
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
		p7.setProfileIconUrl("https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s160-c/Antelope%252520Walls.jpg");
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
	
	public static List<Delegation> getDelegations() {
		
		List<Delegation> list = new ArrayList<Delegation>();
		
		Delegation d = new Delegation();
		d.setProfilIconUrl( "https://lh5.googleusercontent.com/-PyY_yiyjPTo/URqunUOhHFI/AAAAAAAAAbs/azZoULNuJXc/s160-c/False%252520Kiva.jpg");
		d.setUserScreenName("纪嘉祥");
		d.setDelegationTitle("求Android项目组队，2-5人");
		d.setReward("无");
		
		Delegation d1 = new Delegation();
		d1.setProfilIconUrl(  "https://lh6.googleusercontent.com/-PYvLVdvXywk/URqunwd8hfI/AAAAAAAAAbs/qiMwgkFvf6I/s160-c/Fitzgerald%252520Streaks.jpg");
		d1.setUserScreenName("渣酒");
		d1.setDelegationTitle("求帮忙完成毕设");
		d1.setReward("无");
		
		Delegation d2 = new Delegation();
		d2.setProfilIconUrl("https://lh4.googleusercontent.com/-KIR_UobIIqY/URquoCZ9SlI/AAAAAAAAAbs/Y4d4q8sXu4c/s160-c/Foggy%252520Sunset.jpg");
		d2.setUserScreenName("林绵程");
		d2.setDelegationTitle("求香港代购IPAD，急急急啊~~~~~~~~~~~!");
		d2.setReward("报酬200元");
		
		Delegation d3 = new Delegation();
		d3.setProfilIconUrl("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s160-c/Another%252520Rockaway%252520Sunset.jpg");
		d3.setUserScreenName("王二");
		d3.setDelegationTitle("求PS高手帮忙P个图,十万火急!!!!!!!!!");
		d3.setReward("报酬50元");
		
		Delegation d4 = new Delegation();
		d4.setProfilIconUrl("https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s1024/Green%252520Grass.jpg");
		d4.setUserScreenName("李四");
		d4.setDelegationTitle("----------------------------------------------");
		d4.setReward("无");
		
		Delegation d5= new Delegation();
		d5.setProfilIconUrl("https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s160-c/Antelope%252520Walls.jpg");
		d5.setUserScreenName("纪嘉祥");
		d5.setDelegationTitle("................................");
		d5.setReward("无");
		
		list.add(d);
		list.add(d1);
		list.add(d2);
		list.add(d3);
		list.add(d4);
		list.add(d5);
		
		
		return list;
	}

}
