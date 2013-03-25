package com.lube.test;

import com.lube.test.dao.IStudentDao;
import com.lube.test.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 上午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @RequestMapping(value="index.do")
    public void index_jsp(Model model){
        System.out.println("StudentController");
        studentService.getStudentAll();
//        studentMapper.getStudent("123456");
        model.addAttribute("liming", "studentMapper.getStudent(\"123456\")");
    }
}
