package com.faitmain.domain.web.service;

import com.faitmain.domain.web.domain.AttachImage;
import com.faitmain.domain.web.mapper.AttachMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AttachServiceImpl implements AttachService{


//    @Autowired
    private AttachMapper attachMapper;

    @Override
    public List<AttachImage> getAttachList( int productNumber ){

        log.info( "AttachImage = {} ", getClass() );
        return attachMapper.getAttachList( productNumber );
    }

}
