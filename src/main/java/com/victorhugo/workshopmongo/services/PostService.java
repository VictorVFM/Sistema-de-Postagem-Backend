package com.victorhugo.workshopmongo.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.victorhugo.workshopmongo.domain.User;
import com.victorhugo.workshopmongo.dto.AuthorDTO;
import com.victorhugo.workshopmongo.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorhugo.workshopmongo.domain.Post;
import com.victorhugo.workshopmongo.repository.PostRepository;
import com.victorhugo.workshopmongo.services.exeception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	@Autowired
	private UserService userService;
	
	public List<Post> findAll(){
		return repo.findAll();
	}
	
	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Post não encontrado"));
	}

	public Post insert(Post obj){
		return  repo.insert(obj);
	}

	public Post addCommentToPost(String postId, CommentDTO comment){
		Post post = findById(postId);
		User user = userService.findById(comment.getAuthor().getId());
		AuthorDTO author = new AuthorDTO(user);
		comment.setAuthor(author);
		comment.setDate(getCurrentDate());
		post.getComments().add(comment);
		return repo.save(post);

	}

	public Post removeAllCommentsToPost(String postId){
		Post post = findById(postId);
		post.getComments().clear();
		return repo.save(post);
	}


	public List<Post> findByTitle(String text){
		return repo.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate){
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}

	public  Date getCurrentDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
