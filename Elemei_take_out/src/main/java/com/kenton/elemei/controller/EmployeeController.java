package com.kenton.elemei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kenton.elemei.common.R;
import com.kenton.elemei.entity.Employee;
import com.kenton.elemei.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author: Kenton
 * @description 员工控制类
 * @date: 2022/9/3 10:15
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */

    // 前端发送Post请求
    @PostMapping("/login")
    // 前端响应的JSON数据
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){

        // 1.将页面提交的密码password进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.根据用户名查询username查询数据库
        // 创建条件构造器 LambdaQueryWrapper
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 数据库对employee表的username字段进行unique唯一约束 可以检查是否重复
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        log.info("emp = {}",emp);
        // 3.如果没有查到则返回登录失败
        if (emp == null){
            return R.error("账号不存在,登录失败");
        }

        // 4.密码对比 如果不一致则返回登录失败
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误,登录失败");
        }

        // 5.查看员工状态是否可用
        if (emp.getStatus() == 0){
            return R.error("账号已禁用");
        }
        // 6.登录成功 将员工id存储session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        /*什么是 Cookie
        HTTP Cookie（也叫 Web Cookie或浏览器 Cookie）是服务器发送到用户浏览器并保存在本地的一小块数据，
        它会在浏览器下次向同一服务器再发起请求时被携带并发送到服务器上。通常，它用于告知服务端两个请求是否来自同一浏览器，
        如保持用户的登录状态。Cookie 使基于无状态的 HTTP 协议记录稳定的状态信息成为了可能。*/
        // 清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @RequestMapping
    // 这里的employee是前端返回的 即输入的员工信息
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工,员工信息:{}",employee.toString());

        // 设置初始密码 需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        // 获得当前登录用户的id
        // request.getAttribute()只能在一个request内有效，如果重定向回客户端，将取不到值。
        // request.getSession()可以帮你得到HttpSession类型的对象，通常称之为session对象，session对象的作用域为一次
        // Long empId = (Long) request.getSession().getAttribute("employee");
        // log.info("编号是:{}",empId);
        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工分页查询
     * @param page 查第几页
     * @param pageSize 每页显示条数
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
