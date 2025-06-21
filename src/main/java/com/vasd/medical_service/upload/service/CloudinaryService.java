    package com.vasd.medical_service.upload.service;

    import com.cloudinary.Cloudinary;
    import com.cloudinary.utils.ObjectUtils;
    import com.vasd.medical_service.upload.config.CloudinaryConfig;
    import com.vasd.medical_service.upload.entities.TemporaryImage;
    import com.vasd.medical_service.upload.repository.TemporaryImageRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.Map;

    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class CloudinaryService {

        private final Cloudinary cloudinary;
        private final TemporaryImageRepository imageRepository;

        /**
         * Upload an image to Cloudinary into a specified folder.
         *
         * @param file MultipartFile image to upload
         * @param folder the folder name on Cloudinary (e.g., news, service)
         * @return the URL of the image after a successful upload
         * @throws IOException if an upload error occurs
         */

        public String uploadImage(MultipartFile file, String folder) throws IOException {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            String url = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");

            TemporaryImage img = new TemporaryImage();
            img.setPublicId(publicId);
            img.setUrl(url);
            imageRepository.save(img);

            return url;
        }
    }
