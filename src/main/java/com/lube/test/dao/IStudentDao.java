package com.lube.test.dao;

import com.lube.replenish.entity.TBalance;
import com.lube.test.entity.StudentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 上午1:13
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public interface IStudentDao {
    public StudentEntity getStudent(String studentID);

    public StudentEntity getStudentAndClass(String studentID);

    public List<StudentEntity> getStudentAll();

    public void insertStudent(StudentEntity entity);

    public void deleteStudent(StudentEntity entity);

    public void updateStudent(StudentEntity entity);
    public void insertBalance (TBalance balance);
}
