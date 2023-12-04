package com.teamseven.cafeplatform.file.service;

import com.teamseven.cafeplatform.file.common.Classify;
import com.teamseven.cafeplatform.file.common.FileInfoDTO;
import com.teamseven.cafeplatform.file.entity.Photo;
import com.teamseven.cafeplatform.file.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final PhotoRepository photoRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String filePath = rootPath + "/src/main/resources/static/images/product/";

    public String getFullPath(String filename) {
        return filePath + filename;
    }

    public String extractExt(String orgName){ return  orgName.substring(orgName.lastIndexOf("."));}

    public FileInfoDTO storeFile(MultipartFile multipartFile) throws IOException{
        if(multipartFile.isEmpty()) return null;
        String orgName = multipartFile.getOriginalFilename();
        if(orgName == null) return null;
        String saveName = UUID.randomUUID() + extractExt(orgName);
        try{
            multipartFile.transferTo(new File(getFullPath(saveName)));
        }catch (Exception e){
           log.error("파일 저장 오류 : " + e.getMessage());
        }
        return FileInfoDTO.builder()
                .orgName(orgName)
                .saveName(saveName)
                .build();
    }

    @Transactional
    public Optional<Photo> storePhoto(MultipartFile multipartFile, Classify classify) throws IOException{
        FileInfoDTO fileInfoDTO = storeFile(multipartFile);
        if(fileInfoDTO == null){
            return Optional.empty();
        }
        return Optional.of(photoRepository.save(Photo.builder()
                .orgName(fileInfoDTO.getOrgName())
                .saveName(fileInfoDTO.getSaveName())
                .classify(classify)
                .build()));
    }

    @Transactional
    public List<Photo> storePhotos(List<MultipartFile> multipartFiles, Classify classify) throws IOException{
        List<Photo> photoList = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                storePhoto(multipartFile, classify).map(photoList::add);
            }
        }
        return photoList;
    }
}
