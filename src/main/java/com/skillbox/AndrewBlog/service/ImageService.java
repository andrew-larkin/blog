package com.skillbox.AndrewBlog.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.skillbox.AndrewBlog.api.response.ResultErrorsResponse;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.UserRepository;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    private static final int IMG_WIDTH = 36;
    private static final int IMG_HEIGHT = 36;
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

    public ResponseEntity<?> getUploadImageForPost(MultipartFile image) throws Exception {
        //Map<String, String> errors = new HashMap<>();
                validateFile(image);
                Cloudinary cloudinary = new Cloudinary(makeConfig());
                Map res = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String imageAddress = res.get("secure_url").toString();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(imageAddress);
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

    public void upload(MultipartFile file) throws Exception {

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int imageType = originalImage.getType() == 0
                ? BufferedImage.TYPE_INT_ARGB
                : originalImage.getType();
        validateFile(file);
        Cloudinary cloudinary = new Cloudinary(makeConfig());
        BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, imageType);
        File outputFile = new File("image.png");
        ImageIO.write(resizeImageHintJpg, "PNG", outputFile);
        InputStream stream =  new FileInputStream(outputFile);
        MultipartFile resizedImage = new MockMultipartFile("resizedImage", outputFile.getName(),
                String.valueOf(MediaType.IMAGE_PNG), stream);

        Map res = cloudinary.uploader().upload(resizedImage.getBytes(), ObjectUtils.emptyMap());

        String image = res.get("secure_url").toString();
        User person = personDetailsService.getCurrentUser();
        person.setPhoto(image);
        personRepository.save(person);

    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
