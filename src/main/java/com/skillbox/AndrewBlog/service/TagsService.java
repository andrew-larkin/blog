package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.response.Tags;
import com.Skillbox.AndrewBlog.api.response.TagsResponse;
import com.Skillbox.AndrewBlog.model.Post;
import com.Skillbox.AndrewBlog.model.Tag;
import com.Skillbox.AndrewBlog.model.Tag2Post;
import com.Skillbox.AndrewBlog.repository.PostRepository;
import com.Skillbox.AndrewBlog.repository.Tag2PostRepository;
import com.Skillbox.AndrewBlog.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsService implements TagsServiceInt {

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private Tag2PostRepository tag2PostRepository;

    @Autowired
    private PostRepository postRepository;

    public TagsService(TagsRepository tagsRepository, Tag2PostRepository tag2PostRepository,
                       PostRepository postRepository) {
        this.tagsRepository = tagsRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.postRepository = postRepository;
    }

    @Override
    public TagsResponse getTags(String request) {

        TagsResponse tagsResponse = new TagsResponse();
        List<Tags> tagsList = new ArrayList<>();

        Iterable<Tag> tagIterable = tagsRepository.findAll();
        List<Tag> tagList = new ArrayList<>();
        tagIterable.forEach(tagList::add);

        Iterable<Tag2Post> tag2PostIterable = tag2PostRepository.findAll();
        List<Tag2Post> tag2PostList = new ArrayList<>();
        tag2PostIterable.forEach(tag2PostList::add);

        Iterable<Post> postIterable = postRepository.findAll();
        List<Post> postList = new ArrayList<>();
        postIterable.forEach(postList::add);

        int postAmount = postList.size();

        for (Tag tag : tagList) {
            Tags tags = new Tags();
            double count = 0.0;
            if (tag.getName().equals(request)) {
                for (Tag2Post tag2Post : tag2PostList) {
                    if (tag.getId() == tag2Post.getTagId()) {
                        count++;
                    }
                }
                tags.setName(tag.getName());
                tags.setWeight(count / postAmount);
                tagsList.add(tags);
            }
        }
        tagsResponse.setTags(tagsList);
        return tagsResponse;
    }

    @Override
    public TagsResponse getTags() {

        TagsResponse tagsResponse = new TagsResponse(); //объект, возвращаемый методом
        List<Tags> tagsList = new ArrayList<>(); //список

        Iterable<Tag> tagIterable = tagsRepository.findAll();
        List<Tag> tagList = new ArrayList<>();
        tagIterable.forEach(tagList::add);

        Iterable<Tag2Post> tag2PostIterable = tag2PostRepository.findAll();
        List<Tag2Post> tag2PostList = new ArrayList<>();
        tag2PostIterable.forEach(tag2PostList::add);

        Iterable<Post> postIterable = postRepository.findAll();
        List<Post> postList = new ArrayList<>();
        postIterable.forEach(postList::add);

        int postAmount = postList.size();

        for (Tag tag : tagList) {
            Tags tags = new Tags();
            double count = 0.0;
            for (Tag2Post tag2Post : tag2PostList) {
                if (tag.getId() == tag2Post.getTagId()) {
                    count++;
                }
            }
            tags.setName(tag.getName());
            tags.setWeight(count/postAmount);
            tagsList.add(tags);
        }
        tagsResponse.setTags(tagsList);
        return tagsResponse;
    }
}
