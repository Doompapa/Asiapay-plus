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
package com.jeequan.jeepay.core.model;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.utils.JeepayKit;
import lombok.Data;

import java.io.Serializable;

/*
* 系统应用配置项定义Bean
*
* @author terrfly
* @site https://www.jeequan.com
* @date 2021/6/8 16:35
*/
@Data
public class DBApplicationConfig implements Serializable {

    /**
     * 平台名称
     */
    private String platName;

    /**
     * 运营系统地址
     */
    private String mgrSiteUrl;

    /**
     * 商户系统地址
     */
    private String mchSiteUrl;

    /**
     * 支付网关地址
     */
    private String paySiteUrl;

    /**
     * oss公共读文件地址
     */
    private String ossPublicSiteUrl;

    /**
     * 代理网关地址
     */
    private String agentSiteUrl;

    /**
     * 数据清理设置
     */
    private String dataOffset;

    /**
     * 登录白名单
     */
    private String loginWhiteList;

}
