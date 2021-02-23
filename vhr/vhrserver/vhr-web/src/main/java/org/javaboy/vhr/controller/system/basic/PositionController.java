package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Position;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/15 20:19
 * 职位的增删改查。后端已经测试成功
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    PositionService positionService;
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    //添加是post的请求
    public RespBean addPosition(@RequestBody Position position){
        if(positionService.addPosition(position)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败，请重新添加！");
    }

    @PutMapping("/")
    //put是更新
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updatePosition(position)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if (positionService.deletePositionById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，请重试！");
    }

    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids){
        System.out.println(Arrays.toString(ids));
        if(positionService.deletePositionsByIds(ids)==ids.length){
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败，请重试！");
    }

}
