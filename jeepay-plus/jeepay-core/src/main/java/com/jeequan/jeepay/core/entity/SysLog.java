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
package com.jeequan.jeepay.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统操作日志表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_log")
public class SysLog implements Serializable {

    public static final LambdaQueryWrapper<SysLog> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "sys_log_id", type = IdType.AUTO)
    private Integer sysLogId;

    /**
     * 用户姓名
     */
    private String loginUsername;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 所属系统： MGR-运营平台, MCH-商户中心,AGENT-代理中心
     */
    private String sysType;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法描述
     */
    private String methodRemark;

    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 操作请求参数
     */
    private String optReqParam;

    /**
     * 操作响应结果
     */
    private String optResInfo;

    /**
     * 创建时间
     */
    private Date createdAt;


}
