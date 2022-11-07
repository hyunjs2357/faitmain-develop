package com.faitmain.domain.web.mapper;

import java.util.List;

import com.faitmain.domain.web.domain.AttachImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachMapper {

    /* 이미지 데이터 반환 */
    public List<AttachImage> getAttachList( int bookId);

}