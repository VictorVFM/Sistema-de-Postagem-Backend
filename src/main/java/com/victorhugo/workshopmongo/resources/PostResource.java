package com.victorhugo.workshopmongo.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.victorhugo.workshopmongo.domain.Post;
import com.victorhugo.workshopmongo.resources.util.URL;
import com.victorhugo.workshopmongo.services.PostService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	
	@Autowired
	private PostService service;
	

	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<Post>> findAll(){
		List<Post> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Post objPost){
		service.insert(objPost);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objPost.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Post>  findById(@PathVariable String id){
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/titlesearch",method = RequestMethod.GET)
	public ResponseEntity<List<Post>>  findByTitle(@RequestParam(value = "text",defaultValue = "") String text){
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/fullsearch",method = RequestMethod.GET)
	public ResponseEntity<List<Post>>  fullSearch(
			@RequestParam(value = "text",defaultValue = "") String text,
			@RequestParam(value = "minDate",defaultValue = "") String minDate,
			@RequestParam(value = "maxDate",defaultValue = "") String maxDate
			){
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> list = service.fullSearch(text,min,max);
		return ResponseEntity.ok().body(list);
	}
}
