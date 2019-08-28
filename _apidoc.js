// ------------------------------------------------------------------------------------------
// General apiDoc documentation blocks and old history blocks.
// ------------------------------------------------------------------------------------------

// ------------------------------------------------------------------------------------------
// Current Success.
// ------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------
// Current Errors.
// ------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------
// Current Permissions.
// ------------------------------------------------------------------------------------------
/**
 * @apiDefine token 需要传入一个token作为权限验证
 * 需要header中传递Authorization
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine admin 需要传入一个token作为权限验证,且具有管理员角色
 * 需要权限为admin的用户
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine mer 需要传入一个token作为权限验证,且具有商家角色
 * 需要权限为merchant的用户
 * @apiVersion 0.0.1
 */

/**
 * @apiDefine none 无需登录授权
 * 无需登录授权
 */

/**
 * @apiDefine SUCCESS  成功
 * @apiSuccessExample {json} 成功返回:
 * {"code":0,"msg":"成功","data":null}
 */

/**
 * @api {post} /login 后端管理登陆
 * @apiDescription  后端管理登陆，输入任意账号密码登陆
 * @apiName login
 * @apiVersion 0.0.1
 * @apiParam {string} username 用户名
 * @apiParam {string} code 验证码
 * @apiSuccessExample {json} 成功返回:
 * {"code": 0,"data": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IumCk-e-jueQqjEiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNTY3Mzk0MjUwfQ.6u_YgQcwitbkCDG91j6ghq7jAwBgYbJS_poc_c_qwhA",
    "msg": "成功"}
 * @apiGroup user
 * @apiPermission none
 */

// ------------------------------------------------------------------------------------------
// History.
// ------------------------------------------------------------------------------------------
