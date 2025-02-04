package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//import com.sns.post.bo.PostBO;
//import com.sns.post.entity.PostEntity;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

import jakarta.servlet.http.HttpSession;

@Controller
public class TimelineController {

	/*
	 * @Autowired private PostBO postBO;
	 */
	@Autowired 
	private TimelineBO timelineBO;
	
	@GetMapping("/timeline/timeline-view")
	public String timelineView(Model model, HttpSession session) {
		/* (db 가공 전) 목록 전부 가져오기
		 * List<PostEntity> postList = postBO.getPostEntityList();
		 * model.addAttribute("postList", postList);
		 */
		
		Integer userId = (Integer)session.getAttribute("userId"); // UserRestController 이름과 동일하게
		// (가공 후) 댓글 뿌리기
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardViewList", cardViewList);
		
		return "timeline/timeline";
	}
}
