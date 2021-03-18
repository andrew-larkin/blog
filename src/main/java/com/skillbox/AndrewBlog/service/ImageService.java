package com.skillbox.AndrewBlog.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.skillbox.AndrewBlog.api.response.ResultUserResponse;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.UserRepository;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    @Value("${cloudinary.cloud.name}")
    private String cloudName;

    @Value("${cloudinary.api.key}")
    private String cloudApiKey;

    @Value("${cloudinary.api.secret}")
    private String cloudApiSecret;

    @Value("#{'${upload.file.types}'.split(',')}")
    private List<String> uploadFileTypes;

    private final PersonDetailsService personDetailsService;
    private final UserRepository personRepository;

    @Autowired
    public ImageService(PersonDetailsService personDetailsService, UserRepository personRepository) {
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
    }


    public ResponseEntity<?> getUpload(String type, MultipartFile file) {
        if (file != null) {
            try {
                validateFile(file);
                Cloudinary cloudinary = new Cloudinary(makeConfig());
                Map res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                String image = res.get("secure_url").toString();
                User person = personDetailsService.getCurrentUser();
                person.setPhoto(image);
                personRepository.save(person);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(image);

            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResultUserResponse(
                                false,
                                "same errors"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResultUserResponse(
                            false,
                            "same errors"));
        }
    }


    private void validateFile(MultipartFile file) throws Exception{

        String contType = file.getContentType();

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File can not be empty");
        }
        if (contType == null) {
            throw new IllegalArgumentException("Content type is null");
        }

        if (uploadFileTypes.stream().noneMatch(contType::contains)) {
            throw new IllegalArgumentException("Unknown file type");
        }
    }

    private Map<String, String> makeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", cloudApiKey);
        config.put("api_secret", cloudApiSecret);
        return config;
    }
}
