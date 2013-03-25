package com.lube.test.service;

import com.lube.test.dao.IStudentDao;
import com.lube.test.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午10:43
 * To change this template use File | Settings | File Templates.
 */
@Service("studentService")
public class StudentService implements IStudentService {
    @Autowired
    private IStudentDao iStudentDao;

    @Override
    public List<StudentEntity> getStudentAll() {
        System.out.println("StudentService");
        return iStudentDao.getStudentAll();
    }
}
