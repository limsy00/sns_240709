package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comment")
public class CommentRestController {
	
	@Autowired
	private CommentBO commentBO;

	/**
	 * 댓글 쓰기 API
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create") //TODO: GetMapping -> 확인 후 PostMapping
	public Map<String, Object> create(
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			HttpSession session) {
		
		// 로그인 여부 : 댓글쓴이 session 꺼내기
		Integer userId = (Integer)session.getAttribute("userId"); // null:로그인 풀렸을 가능성 => Integer
		
		Map<String, Object> result = new HashMap<>();
		if (userId == null) {
			result.put("code", 403); // 비로그인 상태
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// db insert
		commentBO.addComment(postId, userId, content);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
		
	}
	
	/**
	 * 댓글 삭제 API
	 * @param commentId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete( // 댓글 번호만 있으면 삭제 가능!!
			@RequestParam("commentId") int commentId, 
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("error_message", "로그인이 되지 않은 사용자 입니다.");
			return result;
		}
		
		// 삭제
		commentBO.deleteCommentById(commentId);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
	
}
