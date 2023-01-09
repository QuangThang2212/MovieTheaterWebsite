package com.training.service.impl;

import com.training.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private final Path folder = Paths.get("./image");

    @Override
    public String save(MultipartFile image) throws Exception{
        saveToFolder(image);
        return createImageLink(image.getOriginalFilename());
    }

    private String createImageLink(String imageName){
        return "http://localhost:8080" + "/api/v1/image" + "/" + imageName;
    }


    private void saveToFolder(MultipartFile image) throws Exception{

        try {
            if(Files.notExists(folder)){
                Files.createDirectories(folder);
            }

            String uploadPath = folder + "/" + image.getOriginalFilename();
            byte[] bytes = image.getBytes();
            Path path = Paths.get(uploadPath);
            Files.write(path,bytes);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Image not found");
        }

    }

    @Override
    public byte[] get(String name) throws Exception {

        Path loadPath = Paths.get(String.valueOf(folder)).resolve(name).normalize();
        Resource resource = new UrlResource(loadPath.toUri());
        return IOUtils.toByteArray(resource.getInputStream());
    }
}

