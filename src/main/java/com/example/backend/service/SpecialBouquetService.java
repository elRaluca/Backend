package com.example.backend.service;
import com.example.backend.dto.SpecialBouquetDTO;
import com.example.backend.entity.SpecialBouquet;
import com.example.backend.mapper.SpecialBouquetMapper;

import com.example.backend.repository.SpecialBouquetRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class SpecialBouquetService {
    private final SpecialBouquetRepo specialBouquetRepo;

    @Autowired
    public SpecialBouquetService(SpecialBouquetRepo specialBouquetRepo) {
        this.specialBouquetRepo = specialBouquetRepo;
    }

    public SpecialBouquetDTO createSpecialBouquet(SpecialBouquetDTO specialBouquetDTO) {
        SpecialBouquet specialBouquet = SpecialBouquetMapper.mapToSpecialBouquet(specialBouquetDTO);
        SpecialBouquet savedSpecialBouquet = specialBouquetRepo.save(specialBouquet);
        return SpecialBouquetMapper.mapToSpecialBouquetDTO(savedSpecialBouquet);
    }

    public String storeImage(String base64Image) throws IOException {
        String uploadDir = "src/main/resources/static/image";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Decodare Base64
        String[] parts = base64Image.split(",");
        String imageString = parts[1];
        byte[] data = Base64.decodeBase64(imageString);
        String extension = parts[0].split(";")[0].split("/")[1]; // extract content type and find extension

        String filename = "biteem_" + System.currentTimeMillis() + "." + extension;
        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, data);

        return "/image/" + filename;
    }

}
