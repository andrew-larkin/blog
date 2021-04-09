package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.NameWeightResponse;
import com.skillbox.AndrewBlog.api.response.TagsResponse;
import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.Tag;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.Tag2PostRepository;
import com.skillbox.AndrewBlog.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsService {

    private TagsRepository tagsRepository;
    private Tag2PostRepository tag2PostRepository;
    private PostRepository postRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository, Tag2PostRepository tag2PostRepository,
                       PostRepository postRepository) {
        this.tagsRepository = tagsRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getApiTag(String query) {
        return ResponseEntity.status(HttpStatus.OK).body(new TagsResponse(
                getNameWeightResponseList(query)
        ));

    }

    private List<NameWeightResponse> getNameWeightResponseList(String query) {
        List<NameWeightResponse> nameWeightResponseList = new ArrayList<>();
        List<String> tags;
        if (query == null || query.equals("")) {
            tags = tag2PostRepository.getUsableTags();
        } else {
            tags = new ArrayList<>();
            tags.add(query);
        }
        for (String tag : tags) {
            nameWeightResponseList.add(getNameWeightResponse(tagsRepository.findByName(tag).get()));
        }
        return nameWeightResponseList;
    }

    private NameWeightResponse getNameWeightResponse(Tag tag) {
        return new NameWeightResponse(
                tag.getName(),
                        1/((double)tag2PostRepository.getMaxTag()
                                /(double)postRepository.countByModerationStatus(ModerationStatus.ACCEPTED))
                        *((double)tag2PostRepository.getAmountOfTagByTagId(tag.getId())
                                /(double)postRepository.countByModerationStatus(ModerationStatus.ACCEPTED))
        );
    }

}
