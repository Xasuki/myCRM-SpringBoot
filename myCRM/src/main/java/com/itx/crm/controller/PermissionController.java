package com.itx.crm.controller;

import com.itx.crm.base.BaseController;
import com.itx.crm.service.PermissionService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/15 11:27
 */
@Controller
public class PermissionController extends BaseController {
    @Resource
    private PermissionService permissionService;
}
