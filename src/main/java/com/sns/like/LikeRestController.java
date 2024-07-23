package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	@Autowired
	private LikeBO likeBO;

	//  GET:	/like?postId=13	=> @RequestParam("postId")
	//● GET:	/like/13		=> @PathVariable
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "postId") int postId,
			HttpSession session) {
		
		// 로그인 여부 확인 json
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403); // 비로그인
			result.put("error_message", "로그인이 되지 않은 사용자 입니다.");
			return result;
		}
		
		// likeToggle BO 요청
		likeBO.likeToggle(postId, postId);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
}
