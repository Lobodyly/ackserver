/**
 * 包名：com.yqsapp.common.httpclient
 * 文件名：DeleteMethod.java
 * 版本信息：
 * 日期：2015年1月26日-上午11:28:08
 * 
 */
package com.manji.ackservice.common.utils;

import org.apache.commons.httpclient.methods.EntityEnclosingMethod;

/**
 * 
 * <p>:类名称<p>
 * <p>:描述本类实现的功能作用，若为接口应该声明调用地址</p>
 * @version 2015年1月26日 上午11:28:08
 * 
 */
public class PatchMethod extends EntityEnclosingMethod {

    // ----------------------------------------------------------- Constructors

    /**
     * No-arg constructor.
     *
     * @since 1.0
     */
    public PatchMethod() {
        super();
    }


    /**
     * Constructor specifying a URI.
     *
     * @param uri either an absolute or relative URI
     *
     * @since 1.0
     */
    public PatchMethod(String uri) {
        super(uri);
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Return <tt>"DELETE"</tt>.
     * @return <tt>"DELETE"</tt>
     *
     * @since 2.0
     */
    public String getName() {
        return "DELETE";
    }
}