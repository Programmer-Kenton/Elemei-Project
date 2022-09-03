package com.kenton.elemei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kenton.elemei.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
