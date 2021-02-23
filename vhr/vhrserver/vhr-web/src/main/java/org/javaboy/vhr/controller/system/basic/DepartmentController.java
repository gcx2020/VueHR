package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/17 15:43
 */

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep){
        //json传参数需要用@RequestBody
        departmentService.addDep(dep);
        if(dep.getResult()==1){
            return RespBean.ok("添加成功！",dep);
        }
        return RespBean.error("添加失败，请重试！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id){
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);//传一个对象进去
        if(dep.getResult()==-2){
            return RespBean.error("该部门下有子部门，删除失败!");
        }else if(dep.getResult()==-1){
            return RespBean.error("该部门下有员工，删除失败!");
        }else if(dep.getResult()==1){
            return RespBean.ok("删除成功!");
        }
        return  RespBean.error("未知原因，删除失败!");
    }

}
