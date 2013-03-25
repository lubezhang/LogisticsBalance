package com.lube.test.service;

import com.lube.test.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午10:06
 * To change this template use File | Settings | File Templates.
 */
public interface IStudentService {
    public List<StudentEntity> getStudentAll();
}
