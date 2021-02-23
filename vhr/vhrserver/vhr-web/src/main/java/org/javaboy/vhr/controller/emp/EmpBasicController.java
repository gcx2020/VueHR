package org.javaboy.vhr.controller.emp;

import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/18 22:27
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;

    @Autowired
    PoliticsstatusService politicsstatusService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size, Employee employee, Date[] beginDataScope) {
//        System.out.println(employee);
//        System.out.println(Arrays.toString(beginDataScope));
        return employeeService.getEmployeeByPage(page, size, employee,beginDataScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        if (employeeService.addEmp(employee) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败，请重试！");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticstatus() {
        return politicsstatusService.getAllPoliticstatus();
    }

    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/postions")
    public List<Position> getAllPostions() {
        return positionService.getAllPostions();
    }

    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
//        return RespBean.ok("",String.format("%08d",employeeService.maxWorkID()+1));
        RespBean respBean = RespBean.build().setStatus(200)//bulid可以一项一项设置
                .setObj(String.format("%08d", employeeService.maxWorkID() + 1));
        return respBean;
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id) {
        if (employeeService.deleteEmpByEid(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败，请重试！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败，请重试！");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> list = (List<Employee>) employeeService.getEmployeeByPage(null, null,
                new Employee(),null).getData();
        return HrUtils.POIUtils.employee2Excel(list);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) {
//        try {
//            file.transferTo(new File("C:\\Users\\msi\\Desktop\\文件上传\\gcx.xls"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<Employee> list = HrUtils.POIUtils.Excel2employee(file, nationService.getAllNations(), politicsstatusService.getAllPoliticstatus()
                , departmentService.getAllDepartmentsWithoutChildren(), positionService.getAllPositions(), jobLevelService.getAllJobLevels());
//        for (Employee employee : list) {
//            System.out.println(employee);
//        }
        if (employeeService.addEmps(list) == list.size()) {
            return RespBean.ok("上传文件成功！");
        }
        return RespBean.error("上传文件失败，请重试！");
    }

}
