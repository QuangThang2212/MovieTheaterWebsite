package com.training.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String save(MultipartFile image) throws Exception;

    byte[] get(String name) throws Exception;
}
