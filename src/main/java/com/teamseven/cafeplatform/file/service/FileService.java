package com.teamseven.cafeplatform.file.service;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.file.common.Classify;
import com.teamseven.cafeplatform.file.common.FileInfoDTO;
import com.teamseven.cafeplatform.file.entity.CafePhoto;
import com.teamseven.cafeplatform.file.entity.Photo;
import com.teamseven.cafeplatform.file.entity.ReviewPhoto;
import com.teamseven.cafeplatform.file.repository.CafePhotoRepository;
import com.teamseven.cafeplatform.file.repository.PhotoRepository;
import com.teamseven.cafeplatform.file.repository.ReviewPhotoRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final PhotoRepository photoRepository;
    private final CafePhotoRepository cafePhotoRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String filePath = rootPath + "/src/main/resources/static/images/product/";

    public String getFullPath(String filename) {
        return filePath + filename;
    }

    public String extractExt(String orgName){ return  orgName.substring(orgName.lastIndexOf("."));}

    /**
     * 설정된 path에 파일을 저장하는 메서드
     * @param multipartFile 파일
     * @return 저장된 파일정보 DTO 반환
     * @throws IOException
     */
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

    /**
     * 사진파일을 저장 후 저장정보를 DB에 저장하는 메서드
     * @param multipartFile 사진 파일
     * @param classify 파일 구분
     * @return DB에 저장된 Photo 엔티티 반환
     * @throws IOException
     */
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

    /**
     * 사진파일 리스트를 저장하는 메서드
     * @param multipartFiles 사진 파일 리스트
     * @param classify 파일 구분
     * @return DB에 저장된 Photo 엔티티 리스트 반환
     * @throws IOException
     */
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

    /**
     * 카페에 등록된 사진들을 static폴더에 저장해주고 저장 정보를 DB에 저장하는 메서드
     * @param multipartFiles 사진 파일 리스트
     * @param cafe 카페
     * @return CafePhoto 엔티티 리스트 반환
     * @throws IOException
     */
    @Transactional
    public List<CafePhoto> storeCafePhotos(List<MultipartFile> multipartFiles, Cafe cafe) throws IOException{
        return storePhotos(multipartFiles, Classify.CAFE).stream()
                .map(photo -> CafePhoto.builder().photo(photo).cafe(cafe).build())
                .map(cafePhotoRepository::save).collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewPhoto> storeReviewPhotos(List<MultipartFile> multipartFiles, Review review) throws IOException{
        return storePhotos(multipartFiles, Classify.REVIEW).stream()
                .map(photo -> ReviewPhoto.builder().photo(photo).review(review).build())
                .map(reviewPhotoRepository::save).collect(Collectors.toList());
    }

}
