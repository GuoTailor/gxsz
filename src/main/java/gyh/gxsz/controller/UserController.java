package gyh.gxsz.controller;

import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * Created by gyh on 2019/8/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @api {get} /user 获取所有用户信息
     * @apiDescription  获取所有用户信息
     * @apiName getAllUser
     * @apiVersion 0.0.1
     * @apiUse PageQuery
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"data": {"pageNum": 1,"pageSize": 1,"total": 1204,"pages": 41,"list": [{"id": 1,"userName":
     * "宁巧玲","houseId": "4303","phone": "18723960932","pactTime": "2018-09-01T00:00:00.000+0000","landingTime":
     * null}]},"msg": "成功"}
     * @apiGroup user
     * @apiPermission admin
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public RespBody getAllUser(@Valid PageQuery pageQuery) {
        return new RespBody<>(0, userService.getAll(pageQuery), "成功");
    }

    /**
     * @api {put} /user 修改用户的信息
     * @apiDescription  修改用户的信息
     * @apiName updateUser
     * @apiVersion 0.0.1
     * @apiParam {Integer} id   用户id
     * @apiParam {String} [userName] 用户名
     * @apiParam {String} [houseId] 寝室号
     * @apiParam {String} [phone] 手机号
     * @apiParam {Date} [pactTime] 合同时间格式：yyyy-MM-dd HH:mm:ss 如：2019-08-26 00:07:28
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":1}
     * @apiGroup user
     * @apiPermission admin
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public RespBody updateUser(User user) {
        return new RespBody<>(0, userService.updateByPrimaryKeySelective(user), "成功");
    }

    /**
     * @api {post} /user 添加用户
     * @apiDescription  添加用户
     * @apiName addUser
     * @apiVersion 0.0.1
     * @apiParam {String} [userName] 用户名
     * @apiParam {String} [houseId] 寝室号
     * @apiParam {String} [phone] 手机号
     * @apiParam {Date} [pactTime] 合同时间格式：yyyy-MM-dd HH:mm:ss 如：2019-08-26 00:07:28
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":1}
     * @apiGroup user
     * @apiPermission admin
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RespBody addUser(User user) {
        if (userService.selectUserByPhone(user.getPhone()) != null) {
            return new RespBody(1,"存在相同手机号");
        }
        return new RespBody<>(0, userService.insertSelective(user), "成功");
    }

    /**
     * @api {delete} /user/{id} 删除用户的信息
     * @apiDescription  删除用户的信息
     * @apiName deleteUser
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 用户的id
     * @apiSuccessExample {json} 成功返回:
     * {"code":0,"msg":"成功","data":1}
     * @apiGroup user
     * @apiPermission admin
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public RespBody deleteUser(@PathVariable Integer id) {
        return new RespBody<>(0, userService.deleteByPrimaryKey(id), "成功");
    }

}
