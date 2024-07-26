package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;

	// input: X
	// output: List<PostEntity>
	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	}

	// input: 파라미터들 output:PostEntity
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {

		// 업로드 후 imagePath를 받아옴
		String imagePath = fileManagerService.uploadFile(file, userLoginId);

		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	@Transactional
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 이미지 존재 시 삭제를 위해 -> 기존글 가져오기 
		PostEntity post = postRepository.findById(postId).orElse(null);
		if (post == null) {
			log.info("[글 삭제] post is null. userId:{}, postId:{}", userId, postId);
			return;
		}
		
		// 조회건수가 있으면 글 삭제
		postRepository.delete(post); // post table 글 1 행 지우기
		
		// 이미지 삭제
		fileManagerService.deleteFile(post.getImagePath());
		
		// postId에 대한좋아요/댓글들 전부 삭제
		commentBO.deleteCommentById(postId); // CommentBO 메소드 재사용
		likeBO.deleteLikeByPostId(postId); // LikeBO에 메소드 만들기
		
	}
		
}