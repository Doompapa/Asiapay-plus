/*
 * Copyright (c) 2021-2031
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeequan.jeepay.core.constants;

/*
* 接口返回码
*
* @author terrfly
* @site https://www.jeequan.com
* @date 2021/5/24 17:07
*/
public enum ApiCodeEnum{

    SUCCESS(0, "SUCCESS"), //请求成功

    CUSTOM_FAIL(9999, "自定义业务异常"),  //自定义业务异常

    SYSTEM_ERROR(10, "系统异常[%s]"),
    PARAMS_ERROR(11, "参数有误[%s]"),
    DB_ERROR(12, "数据库服务异常"),
    DELETE_BALANCE_NOT_ZERO(4000, "删除失败，余额或冻结金额不为0元，请先清空余额"),
    DELETE_BALANCE_NOT_ZERO_PASSAGE(4001, "删除失败，通道余额不为0元，请先清空余额"),

    SYS_OPERATION_FAIL_CREATE(5000, "新增失败"),
    SYS_OPERATION_FAIL_DELETE(5001, "删除失败"),
    SYS_OPERATION_FAIL_UPDATE(5002, "修改失败"),
    SYS_OPERATION_FAIL_SELETE(5003, "记录不存在"),
    SYS_PERMISSION_ERROR(5004, "权限错误，当前用户不支持此操作"),

    SYS_OPERATION_FAIL_REPEAT(5005, "新增失败,通道名重复"),
    ORDER_STATE_ERROR(5006, "订单状态错误"),
    QUERY_ERROR(5008, "查询参数异常"),
    NO_GOOGLE_ERROR(5007, "为了您的资金安全，该操作需要先绑定谷歌验证码");


    private int code;

    private String msg;

    ApiCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
