package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.ErrorDescriptionResponse;
import com.skillbox.AndrewBlog.api.response.NameWeightResponse;
import com.skillbox.AndrewBlog.api.response.TagsResponse;
import com.skillbox.AndrewBlog.model.Tag;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.Tag2PostRepository;
import com.skillbox.AndrewBlog.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

        if (query != null) {
            StringBuilder errors = new StringBuilder();
            getQueryCheck(query, errors);

            if (!errors.toString().equals("")) {
                return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new TagsResponse(
                getNameWeightResponseList(query)
        ));

    }

    private List<NameWeightResponse> getNameWeightResponseList(String query) {
        List<NameWeightResponse> nameWeightResponseList = new ArrayList<>();
        List<Tag> tags;
        if (query == null || query.equals("")) {
            tags = tagsRepository.getAllTags();
        } else {
            tags = tagsRepository.getTagByName(query);
        }
        for (Tag tag : tags) {
            nameWeightResponseList.add(getNameWeightResponse(tag));
        }
        return nameWeightResponseList;
    }

    private NameWeightResponse getNameWeightResponse(Tag tag) {
        return new NameWeightResponse(
                tag.getName(),
                new DecimalFormat("#0.000").format((double) tag2PostRepository
                        .getAmountOfTagByTagId(tag.getId())/postRepository.getAmountOfPosts()
        ));
    }

    private void getQueryCheck (String query, StringBuilder errors) {
        if (!query.equals("")) {
        List<Tag> tags = tagsRepository.getAllTags();
        int count = 0;
        for (Tag tag : tags) {
            if (query.equals(tag.getName())) {
                count++;
            }
        }
        if (count == 0) {
            errors.append("Tag ").append(query).append(" is not found.");
        } }
    }
}
